package com.yybf.chenojbackendjudgeservice.judge.codesandbox;

import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * @author yangyibufeng
 * @Description 创建一个代码沙箱接口
 * @date 2024/2/6-14:28
 */
public interface CodeSandbox {
    /**
     * @param executeCodeRequest:
     * @return com.yybf.chenoj.judge.codesandbox.model.ExecuteCodeResponse:
     * @author yangyibufeng
     * @description 执行代码
     * @date 2024/2/6 14:43
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
