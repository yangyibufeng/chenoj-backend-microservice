package com.yybf.chenojbackendserviceclient.service;

import com.yybf.chenojbackendmodel.entity.Question;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lenovo
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2024-01-28 22:02:31
 */
@FeignClient(name = "chenoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId")long questionId);

    @GetMapping("/question_submit/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

}
