package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.user.UserInfoDto;
import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
public interface UserService {
    //    登录
    String login(UserInfoEntity userInfoEntity);
    //    注册
    Integer register(UserInfoEntity userInfoEntity);
    //    发送验证码
    void sendVerifyCode(String email, String code);
    //    用户中心_基础信息查询
    UserInfoDto getUserInfo(Integer userId);
    //根据用户名查询用户id
    Integer getUserId(String username);
}
