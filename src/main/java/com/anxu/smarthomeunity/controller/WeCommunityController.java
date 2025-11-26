package com.anxu.smarthomeunity.controller;

import com.anxu.smarthomeunity.model.Result.Result;
import com.anxu.smarthomeunity.model.dto.wecommunity.CommunityInfoDto;
import com.anxu.smarthomeunity.service.WeCommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WeCommunity-相关接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 13:27
 */
@RestController
@Slf4j
public class WeCommunityController {

    @Autowired
    private WeCommunityService weCommunityService;

    @GetMapping("/permission/isLogin")
    public Result isLogin() {
        log.info("验证登录状态-已经登陆-拦截验证通过");
        return Result.success();
    }

    //获取社区列表
    @GetMapping("/permission/wecommunity/getCommunityList")
    public Result getWeCommunity() {
        log.info("获取所有社区列表");
        List<CommunityInfoDto> allCommunityList = weCommunityService.getAllCommunityList();
        return Result.success(allCommunityList);
    }
    //用户加入社区
    @PostMapping("/permission/wecommunity/joinCommunity")
    public Result joinCommunity(@RequestParam("communityId") Integer communityId,
                                @RequestParam("userId") Integer userId) {
        log.info("用户加入社区");
        weCommunityService.joinCommunity(communityId, userId);
        return Result.success();
    }

    //用户退出社区
    @DeleteMapping("/permission/wecommunity/exitCommunity")
    public Result exitCommunity(@RequestParam("communityId") Integer communityId,
                                @RequestParam("userId") Integer userId) {
        log.info("用户退出社区");
        weCommunityService.exitCommunity(communityId, userId);
        return Result.success();
    }
}
