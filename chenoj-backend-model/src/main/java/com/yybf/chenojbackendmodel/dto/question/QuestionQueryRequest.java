package com.yybf.chenojbackendmodel.dto.question;

import com.yybf.chenojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 * @author 杨毅不逢
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;


    /**
     * 判题用例（json数组）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json对象）
     */
    private JudgeConfig judgeConfig;


    /**
     * 创建用户 id
     */
    private Long userId;


    private static final long serialVersionUID = 1L;
}