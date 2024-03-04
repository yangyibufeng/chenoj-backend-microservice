package com.yybf.chenojbackendjudgeservice.judge.codesandbox;

import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yybf.chenojbackendmodel.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱的代理类，用来给原本的代码沙箱接口添加功能
 *
 * @author yangyibufeng
 * @date 2024/2/6
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    // 给原本的就扣增加功能，所以作为一个代理类原本接口的功能要实现，并且原本接口所需要的参数也都要获取
    private final CodeSandbox codeSandbox;

    // 也可以使用Lombok的 @AllArgsConstructor
    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse);
        return executeCodeResponse;
    }
}