package com.yybf.chenojbackendserviceclient.service;

import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yangyibufeng
 * @Description 判题服务抽象的类
 * @date 2024/2/6-21:29
 */
@FeignClient(name = "chenoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
//judgeService.doJudge(questionSubmitId)

    /**
     * @param questionSubmitId: 题目提交编号
     * @return com.yybf.chenoj.model.vo.QuestionSubmitVO:
     * @author yangyibufeng
     * @description 判题
     * @date 2024/2/15 23:31
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
