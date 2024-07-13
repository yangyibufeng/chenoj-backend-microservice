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

    /**
     * 通过题目id获取对应的题目数据
     *
     * @author yangyibufeng
     * @date 2024/7/12 14:46
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 通过题目提交id获取对应的题目提交记录
     *
     * @author yangyibufeng
     * @date 2024/7/12 14:47
     */
    @GetMapping("/question_submit/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionId);

    /**
     * 更新题目提交数据
     *
     * @author yangyibufeng
     * @date 2024/7/12 14:49
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmit(@RequestBody QuestionSubmit questionSubmit);

    /**
     * 更新题目信息
     *
     * @author yangyibufeng
     * @date 2024/7/12 14:49
     */
    @PostMapping("/question/update")
    boolean updateQuestionById(@RequestBody Question question);
}
