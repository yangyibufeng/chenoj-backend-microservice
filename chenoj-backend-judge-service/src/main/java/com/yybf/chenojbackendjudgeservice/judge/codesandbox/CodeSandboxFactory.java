package com.yybf.chenojbackendjudgeservice.judge.codesandbox;


import com.yybf.chenojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yybf.chenojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yybf.chenojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 一个创建代码沙箱的工厂类，可以根据传入的字符串自动构造相对应的代码沙箱实例
 *
 * @author yangyibufeng
 * @date 2024/2/6
 */
public class CodeSandboxFactory {

    /**
     * @param type: 一个代表要创建的代码沙箱类型的字符串
     * @return com.yybf.chenoj.judge.codesandbox.CodeSandbox: 这里定义的是返回的抽象接口类
     * @author yangyibufeng
     * @description 创建代码沙箱实例
     * @date 2024/2/6 19:54
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }

}