package com.yybf.chenojbackenduserservice.controller.inner;

import com.yybf.chenojbackendmodel.entity.User;
import com.yybf.chenojbackendserviceclient.service.UserFeignClient;
import com.yybf.chenojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 该服务仅针对内部调用，不是给前端的
 * @author yangyibufeng
 * @date 2024/3/5
 */
@RestController()
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * @param userId:
     * @return com.yybf.chenojbackendmodel.entity.User:
     * @description 根据id获取用户信息
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId){
        return userService.getById(userId);
    };

    /**
     * @param idList:
     * @return java.util.List<com.yybf.chenojbackendmodel.entity.User>:
     * @description 根据id获取当前列表
     */
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList){
        return userService.listByIds(idList);
    };
}