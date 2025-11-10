package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.pojo.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //    用户注册
    Integer insert(UserInfo userInfo);
    //    根据用户名查询用户信息
    UserInfo queryByUsername(String username);
}
