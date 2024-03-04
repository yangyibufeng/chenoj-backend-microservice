package com.yybf.chenojbackendmodel.dto.question;

import lombok.Data;

/**
 * 题目配置
 *
 * @author yangyibufeng
 * @date 2024/1/28
 */
@Data
public class JudgeConfig {
    /**
     * @author yangyibufeng
     * @description 时间限制（ms）
     * @date 2024/1/28 23:23
     */
    private Long timeLimit;
    /**
     * @author yangyibufeng
     * @description 内存限制（KB）
     * @date 2024/1/28 23:23
     */
    private Long memoryLimit;
    /**
     * @author yangyibufeng
     * @description 堆栈限制（KB）
     * @date 2024/1/28 23:23
     */
    private Long stackLimit;

}