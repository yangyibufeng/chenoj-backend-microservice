package com.yybf.chenojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yybf.chenojbackendmodel.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yybf.chenojbackendmodel.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import com.yybf.chenojbackendmodel.entity.User;
import com.yybf.chenojbackendmodel.vo.QuestionSubmitVO;

/**
 * @author Lenovo
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2024-01-28 22:19:52
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * * @param request
     * @param loginUser:      直接传入登录用户信息，减少http请求用户信息
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmit
     * *@param request
     * @param loginUser:      直接传入登录用户信息，减少http请求用户信息
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmit, User loginUser);
}

