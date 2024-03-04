package com.yybf.chenojbackendjudgeservice.judge;

import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @author yangyibufeng
 * @Description 判题服务抽象的类
 * @date 2024/2/6-21:29
 */
@Service
public interface JudgeService {
    /**
     * @param questionSubmitId: 题目提交编号
     * @return com.yybf.chenoj.model.vo.QuestionSubmitVO:
     * @author yangyibufeng
     * @description 判题
     * @date 2024/2/15 23:31
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
