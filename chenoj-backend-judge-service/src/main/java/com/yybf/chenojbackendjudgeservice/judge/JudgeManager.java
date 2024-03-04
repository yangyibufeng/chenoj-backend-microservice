package com.yybf.chenojbackendjudgeservice.judge;

import com.yybf.chenojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yybf.chenojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.yybf.chenojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yybf.chenojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.yybf.chenojbackendmodel.codesandbox.JudgeInfo;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（通过判断不同语言，调用相对应的判题策略）
 * 相当于是在判题逻辑外面再封装一层
 *
 * @author yangyibufeng
 * @date 2024/2/16
 */
@Service
public class JudgeManager {
    /**
     * @param judgeContext:
     * @return com.yybf.chenoj.judge.codesandbox.model.JudgeInfo:
     * @author yangyibufeng
     * @description 执行判题
     * @date 2024/2/16 13:42
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        // 下面是根据需要，创建不同的判题逻辑
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        return judgeInfo;
    }
}