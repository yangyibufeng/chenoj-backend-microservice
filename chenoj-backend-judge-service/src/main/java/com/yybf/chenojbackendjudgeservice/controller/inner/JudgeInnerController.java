package com.yybf.chenojbackendjudgeservice.controller.inner;


import com.yybf.chenojbackendjudgeservice.judge.JudgeService;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import com.yybf.chenojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该服务仅针对内部调用，不是给前端的
 *
 * @author yangyibufeng
 * @date 2024/3/5
 */
@RestController()
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * @param questionSubmitId: 题目提交编号
     * @return com.yybf.chenoj.model.vo.QuestionSubmitVO:
     * @author yangyibufeng
     * @description 判题
     * @date 2024/2/15 23:31
     */
    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    };
}