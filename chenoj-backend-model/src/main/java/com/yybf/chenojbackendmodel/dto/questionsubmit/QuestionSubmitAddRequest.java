package com.yybf.chenojbackendmodel.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 * 用来接收前端传回来的提交题目的数据
 * @author 杨毅不逢
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目id
     */
    private Long questionId;


    private static final long serialVersionUID = 1L;
}