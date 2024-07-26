package com.yybf.chenojbackendjudgeservice;

import cn.hutool.http.HttpUtil;
import com.alibaba.excel.util.StringUtils;
import com.yybf.chenojbackendcommon.common.ErrorCode;
import com.yybf.chenojbackendcommon.exception.BusinessException;
import org.junit.jupiter.api.Test;

/**
 * $END$
 * 测试远程调用代码沙箱
 *
 * @author yangyibufeng
 * @date 2024/7/22
 */
public class RemoteTest {

    @Test
    public void testCodeSandbox() {
        String url = "http://192.168.40.133:8080/executeCode";
        String responseStr = HttpUtil.createPost(url)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "接口调用失败，message：" + responseStr);
        }
        System.out.println(responseStr);
    }
}
