package com.yybf.chenojbackendquestionservice.controller.inner;


import com.yybf.chenojbackendmodel.entity.Question;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import com.yybf.chenojbackendquestionservice.service.QuestionService;
import com.yybf.chenojbackendquestionservice.service.QuestionSubmitService;
import com.yybf.chenojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该服务仅针对内部调用，不是给前端的
 * @author yangyibufeng
 * @date 2024/3/5
 */
@RestController()
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {


    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId")long questionId){
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionId){
        return questionSubmitService.getById(questionId);
    }

    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmit(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }

    @Override
    @PostMapping("/question/update")
    public boolean updateQuestionById(Question question) {
        return questionService.updateById(question);
    }


}