package com.yybf.chenojbackendjudgeservice.judge.codesandbox.impl;

import com.yybf.chenojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.yybf.chenojbackendmodel.codesandbox.JudgeInfo;
import com.yybf.chenojbackendmodel.enums.JudgeInfoMessageEnum;
import com.yybf.chenojbackendmodel.enums.QuestionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author yangyibufeng
 * @Description 实例代码沙箱（仅为了跑通业务流程）
 * @date 2024/2/6
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        // todo 这里的输出需要修改
        executeCodeResponse.setOutputList(Collections.singletonList(code));
        executeCodeResponse.setMessage("代码沙箱测试成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getText());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}