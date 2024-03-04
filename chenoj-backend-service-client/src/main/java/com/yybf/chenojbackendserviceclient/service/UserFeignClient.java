package com.yybf.chenojbackendserviceclient.service;

import com.yybf.chenojbackendcommon.common.ErrorCode;
import com.yybf.chenojbackendcommon.exception.BusinessException;
import com.yybf.chenojbackendmodel.entity.User;
import com.yybf.chenojbackendmodel.enums.UserRoleEnum;
import com.yybf.chenojbackendmodel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.yybf.chenojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 * 用于提供远程调用的接口
 *
 * @author 杨毅不逢
 */
@FeignClient(name = "chenoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * @param userId:
     * @return com.yybf.chenojbackendmodel.entity.User:
     * @description 根据id获取用户信息
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * @param idList:
     * @return java.util.List<com.yybf.chenojbackendmodel.entity.User>:
     * @description 根据id获取当前列表
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取当前登录用户
     * 直接用默认方法，不走远程调用，节约性能
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    ;

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    ;

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    ;

}
