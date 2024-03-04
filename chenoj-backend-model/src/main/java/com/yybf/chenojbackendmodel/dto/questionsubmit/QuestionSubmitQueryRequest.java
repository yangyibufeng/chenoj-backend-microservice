package com.yybf.chenojbackendmodel.dto.questionsubmit;


import com.yybf.chenojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 * 用来接收前端传回来的提交题目的数据
 * @author 杨毅不逢
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     * 用包装类的好处是可以存储null
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 用户id
     */
    private Long userId;


    private static final long serialVersionUID = 1L;
}