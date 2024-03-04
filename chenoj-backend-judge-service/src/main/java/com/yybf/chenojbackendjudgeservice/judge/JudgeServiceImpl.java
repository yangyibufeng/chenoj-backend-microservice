package com.yybf.chenojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.yybf.chenojbackendcommon.common.ErrorCode;
import com.yybf.chenojbackendcommon.exception.BusinessException;
import com.yybf.chenojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yybf.chenojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.yybf.chenojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.yybf.chenojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.yybf.chenojbackendmodel.codesandbox.JudgeInfo;
import com.yybf.chenojbackendmodel.dto.question.JudgeCase;
import com.yybf.chenojbackendmodel.entity.Question;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import com.yybf.chenojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.yybf.chenojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yangyibufeng
 * @date 2024/2/15
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    // 用于查询题目信息
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    // $ 表示这个值动态传入 : 表示设置一个默认值
    // 这个值就是在配置文件中编写的用来给用户自定义的变量的值
    @Value("${codesandbox.type:example}")
    private String configType;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 根据传入的题目提交id，获取到对应的题目、提交信息（包括代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        // 获取题目id，判断题目信息存在状态
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        // 判断题目判题状态
        Integer status = questionSubmit.getStatus();
        if (!Objects.equals(status, QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }

        // 更改判题（题目提交状态 ，改为“判题中”，防止重复执行 -- 加锁）
        QuestionSubmit questionSubmitUpdateStatus = new QuestionSubmit();
        questionSubmitUpdateStatus.setId(questionSubmitId);
        questionSubmitUpdateStatus.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdateStatus);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        // 调用代码沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(configType);
        // 创建一个代理类，将原本的实例传入，来实现功能的增强
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取到判题用例，然后将获取到的string字符串转换成list数组
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        // 然后通过lambda表达式，只获取到其中的input，然后收集起来转换为一个新的数组
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 使用builder建造者模式，不用get，set方法，直接通过.的方式将属性传入构造器中
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // 根据沙箱的执行结果，设置题目的判题状态和信息

        // 设置判题策略所需的上下文信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        // 通过judgeManager调用doJudge方法，来实现不同判题策略的切换
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // 根据判题信息修改数据库中的数据
        questionSubmitUpdateStatus = new QuestionSubmit();
        questionSubmitUpdateStatus.setId(questionSubmitId);
        // 仅表示判题已完成，与题目是否通过无关
        questionSubmitUpdateStatus.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdateStatus.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdateStatus);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 查询到对应的执行结果并输出
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;

    }
}