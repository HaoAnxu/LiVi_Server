package com.anxu.smarthomeunity.service.impl;

import com.anxu.smarthomeunity.mapper.UserMapper;
import com.anxu.smarthomeunity.pojo.user.UserInfo;
import com.anxu.smarthomeunity.service.UserService;
import com.anxu.smarthomeunity.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MailSender mailSender;

    //    用户登录
    @Override
    public String login(UserInfo userInfo) {
        //后端也应有校验
        Assert.notNull(userInfo.getUsername(), "用户名不能为空");
        Assert.notNull(userInfo.getPassword(), "密码不能为空");

        //根据用户名查询用户
        UserInfo user = userMapper.queryByUsername(userInfo.getUsername());
        Assert.notNull(user, "用户不存在");
        //校验密码
        boolean matches = passwordEncoder.matches(userInfo.getPassword(), user.getPassword());
        Assert.isTrue(matches, "密码错误");

        //登录成功，生产JWT令牌然后返回
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",user.getId());
        dataMap.put("username",user.getUsername());
        return jwtUtils.generateJwt(dataMap);
    }
    //    用户注册
    @Override
    public Integer register(UserInfo userInfo) {
        //后端也应有校验
        Assert.notNull(userInfo.getUsername(), "用户名不能为空");
        Assert.notNull(userInfo.getPassword(), "密码不能为空");
        Assert.notNull(userInfo.getEmail(), "邮箱不能为空");
        Assert.isTrue(userInfo.getGender() == 0 || userInfo.getGender() == 1, "性别只能为0或1");
        Assert.isTrue(userInfo.getSignature().length() <= 100, "签名长度不能超过100位");
        Assert.isTrue(userInfo.getEmail().matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$"), "邮箱格式错误");
        Assert.isTrue(userInfo.getPassword().length() >= 6, "密码长度不能小于6位");
        userInfo.setCreateTime(LocalDateTime.now());

        UserInfo user = userMapper.queryByUsername(userInfo.getUsername());
        Assert.isTrue(user == null, "用户名已存在");

        //加密
        String encrypt = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encrypt);

        //插入数据库
        Integer rows = userMapper.insert(userInfo);
        return rows;
    }

    //    发送邮箱验证码
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendVerifyCode(String email, String code) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("haomarch7@qq.com");
            message.setTo(email);
            message.setSubject("验证码");
            message.setText("您的验证码为：" + code);
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
