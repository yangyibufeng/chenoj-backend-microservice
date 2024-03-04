package com.yybf.chenojbackendjudgeservice.judge.strategy;

import com.yybf.chenojbackendmodel.codesandbox.JudgeInfo;
import com.yybf.chenojbackendmodel.dto.question.JudgeCase;
import com.yybf.chenojbackendmodel.entity.Question;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author yangyibufeng
 * @description 定义上下文，用于定义在策略中传递的参数
 * 如果想要.allget生效，就要给实体类添加@Data注解，为其生成get/set方法
 * @date 2024/2/16 13:39
 * @return null:
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}