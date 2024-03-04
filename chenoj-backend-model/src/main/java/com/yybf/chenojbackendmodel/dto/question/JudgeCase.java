package com.yybf.chenojbackendmodel.dto.question;

import lombok.Data;

/**
 * 题目用例
 *
 * @author yangyibufeng
 * @date 2024/1/28
 */
@Data
public class JudgeCase {
    /**
     * @author yangyibufeng
     * @description 输入用例
     * @date 2024/1/28 23:23
     */
    private String input;
    /**
     * @author yangyibufeng
     * @description 输出用例
     * @date 2024/1/28 23:24
     */
    private String output;

}