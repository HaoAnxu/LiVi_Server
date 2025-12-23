package com.anxu.livi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.livi.mapper.user.UserMapper;
import com.anxu.livi.mapper.wePost.*;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.dto.wePost.PostCommentDTO;
import com.anxu.livi.model.dto.wePost.PostInfoDTO;
import com.anxu.livi.model.entity.user.UserInfoEntity;
import com.anxu.livi.model.entity.wePost.*;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.vo.user.UserInfoVO;
import com.anxu.livi.model.vo.wePost.*;
import com.anxu.livi.service.WePostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 【WePost相关服务实现类】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:45
 */
@Slf4j
@Service
public class WePostServiceImpl implements WePostService {
    @Autowired
    private PostInfoMapper postInfoMapper;
    @Autowired
    private PostCircleMapper postCircleMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostCarouselMapper postCarouselMapper;
    @Autowired
    private PostCircleUserMapper postCircleUserMapper;


    // 分页查询所有帖子信息列表，包括对应的圈子信息，用户信息
    @Override
    public List<PostInfoVO> listWePost(PageDTO pageDTO) {
        if (pageDTO.getPage() == null || pageDTO.getPageSize() == null) {
            pageDTO.setPage(1);
            pageDTO.setPageSize(10);
        }
        Page<PostInfoEntity> pagePostInfoEntity = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        // 分页查询帖子列表
        QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
        if (!(pageDTO.getId() == null)) {
            queryWrapper.eq("circle_id", pageDTO.getId());
        }
        queryWrapper.orderByDesc("create_time");
        // 分页查询帖子列表
        postInfoMapper.selectPage(pagePostInfoEntity, queryWrapper);

        List<PostInfoEntity> records = pagePostInfoEntity.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        //提取所有circleId
        Set<Integer> circleIds = records.stream()
                .map(PostInfoEntity::getCircleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        //提取所有userId
        Set<Integer> userIds = records.stream()
                .map(PostInfoEntity::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        //批量查询圈子信息
        Map<Integer, String> circleNameMap = new HashMap<>();
        if (!circleIds.isEmpty()) {
            List<PostCircleEntity> circleEntities = postCircleMapper.selectBatchIds(circleIds);
            circleNameMap = circleEntities.stream()
                    .collect(Collectors.toMap(PostCircleEntity::getCircleId, PostCircleEntity::getCircleName));
        }

        //批量查询用户信息
        Map<Integer, UserInfoVO> userInfoMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<UserInfoEntity> userEntities = userMapper.selectBatchIds(userIds);
            userInfoMap = userEntities.stream()
                    .collect(Collectors.toMap(UserInfoEntity::getId, userEntity -> {
                        UserInfoVO vo = new UserInfoVO();
                        vo.setUsername(userEntity.getUsername());
                        vo.setAvatar(userEntity.getAvatar());
                        return vo;
                    }));
        }

        //组装VO
        List<PostInfoVO> postInfoVO = BeanUtil.copyToList(records, PostInfoVO.class);
        for (PostInfoVO infoVO : postInfoVO) {
            infoVO.setCircleName(circleNameMap.getOrDefault(infoVO.getCircleId(), ""));
            UserInfoVO userVO = userInfoMap.get(infoVO.getUserId());
            if (userVO != null) {
                infoVO.setUserName(userVO.getUsername());
                infoVO.setUserAvatar(userVO.getAvatar());
            }
        }
        return postInfoVO;
    }

    // 根据userId分页查询用户发布的帖子信息列表，包括对应的圈子信息，用户信息
    @Override
    public List<PostInfoVO> listWePostByUserId(PageDTO pageDTO) {
        if (pageDTO.getPage() == null || pageDTO.getPageSize() == null) {
            pageDTO.setPage(1);
            pageDTO.setPageSize(10);
        }
        Page<PostInfoEntity> pagePostInfoEntity = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        //主查询
        QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", pageDTO.getId());
        queryWrapper.orderByDesc("create_time");
        postInfoMapper.selectPage(pagePostInfoEntity, queryWrapper);

        List<PostInfoEntity> records = pagePostInfoEntity.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        //提取所有circleId
        Set<Integer> circleIds = records.stream()
                .map(PostInfoEntity::getCircleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        //提取所有userId
        Set<Integer> userIds = records.stream()
                .map(PostInfoEntity::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        //批量查询圈子信息
        Map<Integer, String> circleNameMap = new HashMap<>();
        if (!circleIds.isEmpty()) {
            List<PostCircleEntity> circleEntities = postCircleMapper.selectBatchIds(circleIds);
            circleNameMap = circleEntities.stream()
                    .collect(Collectors.toMap(PostCircleEntity::getCircleId, PostCircleEntity::getCircleName));
        }

        //批量查询用户信息
        Map<Integer, UserInfoVO> userInfoMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<UserInfoEntity> userEntities = userMapper.selectBatchIds(userIds);
            userInfoMap = userEntities.stream()
                    .collect(Collectors.toMap(UserInfoEntity::getId, userEntity -> {
                        UserInfoVO vo = new UserInfoVO();
                        vo.setUsername(userEntity.getUsername());
                        vo.setAvatar(userEntity.getAvatar());
                        return vo;
                    }));
        }

        //组装VO
        List<PostInfoVO> postInfoVO = BeanUtil.copyToList(records, PostInfoVO.class);
        for (PostInfoVO infoVO : postInfoVO) {
            infoVO.setCircleName(circleNameMap.getOrDefault(infoVO.getCircleId(), ""));
            UserInfoVO userVO = userInfoMap.get(infoVO.getUserId());
            if (userVO != null) {
                infoVO.setUserName(userVO.getUsername());
                infoVO.setUserAvatar(userVO.getAvatar());
            }
        }
        return postInfoVO;
    }

    //根据circleId分页查询社区包含的帖子信息列表，包括对应的圈子信息，用户信息
    @Override
    public List<PostInfoVO> listWePostByCircleId(PageDTO pageDTO) {
        if (pageDTO.getPage() == null || pageDTO.getPageSize() == null) {
            pageDTO.setPage(1);
            pageDTO.setPageSize(10);
        }
        Page<PostInfoEntity> page = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("circle_id", pageDTO.getId());
        queryWrapper.orderByDesc("create_time");
        postInfoMapper.selectPage(page, queryWrapper);
        List<PostInfoEntity> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        //提取所有circleId
        Set<Integer> circleIds = records.stream()
                .map(PostInfoEntity::getCircleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        //提取所有userId
        Set<Integer> userIds = records.stream()
                .map(PostInfoEntity::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        //批量查询圈子信息
        Map<Integer, String> circleNameMap = new HashMap<>();
        if (!circleIds.isEmpty()) {
            List<PostCircleEntity> circleEntities = postCircleMapper.selectBatchIds(circleIds);
            circleNameMap = circleEntities.stream()
                    .collect(Collectors.toMap(PostCircleEntity::getCircleId, PostCircleEntity::getCircleName));
        }

        //批量查询用户信息
        Map<Integer, UserInfoVO> userInfoMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<UserInfoEntity> userEntities = userMapper.selectBatchIds(userIds);
            userInfoMap = userEntities.stream()
                    .collect(Collectors.toMap(UserInfoEntity::getId, userEntity -> {
                        UserInfoVO vo = new UserInfoVO();
                        vo.setUsername(userEntity.getUsername());
                        vo.setAvatar(userEntity.getAvatar());
                        return vo;
                    }));
        }

        //组装VO
        List<PostInfoVO> postInfoVO = BeanUtil.copyToList(records, PostInfoVO.class);
        for (PostInfoVO infoVO : postInfoVO) {
            infoVO.setCircleName(circleNameMap.getOrDefault(infoVO.getCircleId(), ""));
            UserInfoVO userVO = userInfoMap.get(infoVO.getUserId());
            if (userVO != null) {
                infoVO.setUserName(userVO.getUsername());
                infoVO.setUserAvatar(userVO.getAvatar());
            }
        }
        return postInfoVO;
    }

    // 查询帖子详情
    @Override
    public PostInfoVO detailWePost(Integer postId) {
        // 查询帖子详情
        PostInfoEntity postInfoEntity = postInfoMapper.selectById(postId);
        return BeanUtil.copyProperties(postInfoEntity, PostInfoVO.class);
    }

    // 分页查询帖子评论列表
    @Override
    public List<PostCommentVO> listWePostComment(PageDTO pageDTO) {
        if (pageDTO.getPage() == null || pageDTO.getPageSize() == null) {
            pageDTO.setPage(1);
            pageDTO.setPageSize(10);
        }
        Page<PostCommentEntity> pagePostCommentEntity = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        // 分页查询帖子评论列表
        QueryWrapper<PostCommentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", pageDTO.getId());
        queryWrapper.orderByDesc("create_time");
        // 分页查询帖子评论列表
        postCommentMapper.selectPage(pagePostCommentEntity, queryWrapper);
        return BeanUtil.copyToList(pagePostCommentEntity.getRecords(), PostCommentVO.class);
    }

    // 创建新帖子
    @Override
    public boolean saveWePost(PostInfoDTO postInfoDTO) {
        // 转换为实体类
        PostInfoEntity postInfoEntity = BeanUtil.copyProperties(postInfoDTO, PostInfoEntity.class);
        // 保存帖子
        return postInfoMapper.insert(postInfoEntity) > 0;
    }

    // 发评论
    @Override
    public boolean saveWePostComment(PostCommentDTO postCommentDTO) {
        //先查询帖子是否存在
        PostInfoEntity postInfoEntity = postInfoMapper.selectById(postCommentDTO.getPostId());
        if (postInfoEntity == null) {
            log.info("帖子不存在, postId: {}", postCommentDTO.getPostId());
            return false;
        }
        // 转换为实体类
        PostCommentEntity postCommentEntity = BeanUtil.copyProperties(postCommentDTO, PostCommentEntity.class);
        // 保存评论
        return postCommentMapper.insert(postCommentEntity) > 0;
    }

    // 删除帖子根据postId
    @Override
    public boolean deleteWePost(Integer postId) {
        // 先查询帖子是否存在
        PostInfoEntity postInfoEntity = postInfoMapper.selectById(postId);
        if (postInfoEntity == null) {
            log.info("删除操作：帖子不存在, postId: {}", postId);
            return false;
        }
        // 删除帖子
        return postInfoMapper.deleteById(postId) > 0;
    }

    // 删除评论根据commentId
    @Override
    public boolean deleteWePostComment(Integer commentId) {
        // 先查询评论是否存在
        PostCommentEntity postCommentEntity = postCommentMapper.selectById(commentId);
        if (postCommentEntity == null) {
            log.info("删除操作：评论不存在, commentId: {}", commentId);
            return false;
        }
        // 删除评论
        return postCommentMapper.deleteById(commentId) > 0;
    }

    // 查询圈子列表,随机返回四个圈子
    @Override
    public List<PostCircleVO> listWePostCircle() {
        // MySQL 随机取4条（效率：数据量大时比查全部快）
        QueryWrapper<PostCircleEntity> queryWrapper = new QueryWrapper<>();
        // MySQL 用 ORDER BY RAND() 随机排序，LIMIT 4 取前4条
        queryWrapper.last("ORDER BY RAND() LIMIT 4");

        List<PostCircleEntity> randomCircleList = postCircleMapper.selectList(queryWrapper);

        // 防御性判空
        if (CollectionUtils.isEmpty(randomCircleList)) {
            return Collections.emptyList();
        }

        return BeanUtil.copyToList(randomCircleList, PostCircleVO.class);
    }

    // 查询圈子详情
    @Override
    public PostCircleVO detailWePostCircle(Integer circleId) {
        // 先查询圈子是否存在
        PostCircleEntity postCircleEntity = postCircleMapper.selectById(circleId);
        if (postCircleEntity == null) {
            log.info("查询操作：圈子不存在, circleId: {}", circleId);
            return null;
        }
        return BeanUtil.copyProperties(postCircleEntity, PostCircleVO.class);
    }

    // 查询3张轮播图片
    @Override
    public List<PostCarouselVO> listCarousel() {
        QueryWrapper<PostCarouselEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 3");
        List<PostCarouselEntity> postCarouselEntityList = postCarouselMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(postCarouselEntityList, PostCarouselVO.class);
    }

    // 查询最新5条热点
    @Override
    public List<PostInfoVO> listHotNews() {
//        //先查询热点帖子
//        QueryWrapper<PostInfoEntity> hotQuery = new QueryWrapper<>();
//        hotQuery.orderByDesc("create_time");
//        hotQuery.eq("hot", 1);
//        List<PostInfoEntity> hotList = postInfoMapper.selectList(hotQuery);
//        //计算需要补充的数量
//        int needSupplement = 5 - (CollectionUtils.isEmpty(hotList) ? 0 : hotList.size());
//        List<PostInfoEntity> supplementList = new ArrayList<>();
//        //补充
//        if(needSupplement>0){
//            //查询最新帖子
//            QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
//            queryWrapper.orderByDesc("likes");
//            queryWrapper.ne("hot",1);
//            queryWrapper.last("limit "+needSupplement);//补充几条查几条
//            supplementList = postInfoMapper.selectList(queryWrapper);
//        }
//        //合并热点帖子和补充帖子
//        List<PostInfoEntity> finalList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(hotList)) {
//            finalList.addAll(hotList);
//        }
//        if (!CollectionUtils.isEmpty(supplementList)) {
//            finalList.addAll(supplementList);
//        }
//
//        // 最终只取前5条
//        if (finalList.size() > 5) {
//            finalList = finalList.subList(0, 5);
//        }
//
//        return BeanUtil.copyToList(finalList, PostInfoVO.class);
        QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("hot");
        queryWrapper.orderByDesc("case when hot = 1 then create_time else likes end");
        queryWrapper.last("limit 5");
        List<PostInfoEntity> postInfoEntityList = postInfoMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(postInfoEntityList, PostInfoVO.class);
    }

    // 查询用户加入的圈子列表
    @Override
    public PageResult listWePostCircleByUserId(PageDTO pageDTO) {
        // 1. 分页参数默认值处理
        if (pageDTO.getPage() == null || pageDTO.getPageSize() == null) {
            pageDTO.setPage(1);
            pageDTO.setPageSize(4);
        }

        // startPage(页码, 页大小)，页码从1开始
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getPageSize());
        // 执行查询（PageHelper会自动拦截这条SQL，添加分页条件）
        List<PostCircleEntity> list = postCircleUserMapper.selectUserCircleInfo(pageDTO.getId());
        System.out.println(list);
        // 将查询结果包装为Page对象，获取分页信息（总数、分页数据）
        PageInfo<PostCircleEntity> pageInfo = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageInfo.getTotal()); // 总条数
        pageResult.setRows(BeanUtil.copyToList(pageInfo.getList(), PostCircleVO.class)); // 分页数据

        return pageResult;
    }

}
