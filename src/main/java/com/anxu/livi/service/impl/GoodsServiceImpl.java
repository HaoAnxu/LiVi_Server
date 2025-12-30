package com.anxu.livi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.livi.mapper.goods.GoodsCommentMapper;
import com.anxu.livi.mapper.goods.GoodsImageMapper;
import com.anxu.livi.mapper.goods.GoodsMapper;
import com.anxu.livi.mapper.user.UserMapper;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.entity.goods.GoodsCommentEntity;
import com.anxu.livi.model.entity.user.UserInfoEntity;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.vo.goods.GoodsBriefVO;
import com.anxu.livi.model.vo.goods.GoodsCommentsVO;
import com.anxu.livi.model.vo.goods.GoodsDetailVO;
import com.anxu.livi.model.entity.goods.GoodsEntity;
import com.anxu.livi.model.entity.goods.GoodsImageEntity;
import com.anxu.livi.model.vo.goods.GoodsImageVO;
import com.anxu.livi.model.vo.user.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;
import com.anxu.livi.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品相关服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/11/7 13:32
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsImageMapper goodsImageMapper;
    @Autowired
    private GoodsCommentMapper goodsCommentMapper;
    @Autowired
    private UserMapper userMapper;


    //    查询商品列表
    @Override
    public PageResult queryGoods(GoodsQueryDTO goodsQueryDto) {
        //创建分页对象
        Page<GoodsEntity> page = new Page<>(goodsQueryDto.getPage(), goodsQueryDto.getPageSize());
        //构建查询条件
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_status", 1);
        if (goodsQueryDto.getGoodsName() != null && !goodsQueryDto.getGoodsName().trim().isEmpty()) {
            queryWrapper.like("goods_name", goodsQueryDto.getGoodsName());
        }
        if (goodsQueryDto.getGoodsType() != null && !goodsQueryDto.getGoodsType().trim().isEmpty()) {
            queryWrapper.eq("goods_type", goodsQueryDto.getGoodsType());
        }
        String sortRule = goodsQueryDto.getSortRule();
        if ("by_price_desc".equals(sortRule)) {
            // 价格升序（便宜优先）：orderByAsc(字段名)
            queryWrapper.orderByAsc("goods_price");
        } else if ("by_price_asc".equals(sortRule)) {
            // 价格降序（贵的优先）：orderByDesc(字段名)
            queryWrapper.orderByDesc("goods_price");
        } else if ("by_sales".equals(sortRule)) {
            // 销量降序
            queryWrapper.orderByDesc("goods_sales");
        } else if ("by_rating".equals(sortRule)) {
            // 评分降序
            queryWrapper.orderByDesc("goods_score");
        } else {
            // 默认：更新时间降序
            queryWrapper.orderByDesc("update_time");
        }
        Page<GoodsEntity> resultPage = this.goodsMapper.selectPage(page, queryWrapper);
        // 转换为VO列表
        List<GoodsBriefVO> goodsBriefVOList = new ArrayList<>();
        for (GoodsEntity goodsEntity : resultPage.getRecords()) {
            GoodsBriefVO goodsBriefVO = BeanUtil.copyProperties(goodsEntity, GoodsBriefVO.class);
            Double goodsOriginalPrice = goodsEntity.getGoodsPrice();
            BigDecimal res = new BigDecimal(goodsOriginalPrice)
                    .multiply(new BigDecimal("1.2"))//计算原价
                    .setScale(2, RoundingMode.HALF_UP);//保留2位小数，四舍五入
            goodsBriefVO.setGoodsOriginalPrice(res.doubleValue());
            goodsBriefVOList.add(goodsBriefVO);
        }
        // 转换类型
        return new PageResult(resultPage.getTotal(), goodsBriefVOList);
    }

    //    查询单个商品详情
    @Override
    public GoodsDetailVO queryGoodsDetail(Long goodsId) {
        log.info("查询商品详情，商品id：{}", goodsId);
        //先查详情的基础数据
        GoodsEntity goodsEntity = this.goodsMapper.selectById(goodsId);
        if (goodsEntity == null) {
            log.info("商品不存在，商品id：{}", goodsId);
            return null;
        }
        //将goods转换为goodsDetail
        GoodsDetailVO goodsDetailVO = BeanUtil.copyProperties(goodsEntity, GoodsDetailVO.class);
        //再查图片
        QueryWrapper<GoodsImageEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsId);
        // 执行查询
        List<GoodsImageEntity> goodsImageEntityList = this.goodsImageMapper.selectList(queryWrapper);
        //将goodsImageEntity转换为goodsImageVO
        List<GoodsImageVO> goodsImageVOList = new ArrayList<>();
        for (GoodsImageEntity goodsImageEntity : goodsImageEntityList) {
            GoodsImageVO goodsImageVO = BeanUtil.copyProperties(goodsImageEntity, GoodsImageVO.class);
            goodsImageVOList.add(goodsImageVO);
        }
        goodsDetailVO.setGoodsImageVOList(goodsImageVOList);
        return goodsDetailVO;
    }

    //    查询12个热卖商品
    @Override
    public List<GoodsBriefVO> queryHotGoods() {
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("goods_sales");
        queryWrapper.last("limit 12");
        queryWrapper.gt("goods_stock", 0);
        List<GoodsEntity> goodsEntityList = this.goodsMapper.selectList(queryWrapper);
        List<GoodsBriefVO> goodsBriefVOList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            GoodsBriefVO goodsBriefVO = BeanUtil.copyProperties(goodsEntity, GoodsBriefVO.class);
            Double goodsOriginalPrice = goodsEntity.getGoodsPrice();
            BigDecimal res = new BigDecimal(goodsOriginalPrice)
                    .multiply(new BigDecimal("1.2"))//计算原价
                            .setScale(2, RoundingMode.HALF_UP);//保留2位小数，四舍五入
            goodsBriefVO.setGoodsOriginalPrice(res.doubleValue());
            goodsBriefVOList.add(goodsBriefVO);
        }
        return goodsBriefVOList;
    }

     //    查询单个商品评论列表
    @Override
    public PageResult queryGoodsComment(PageDTO pageDTO) {
        if(pageDTO.getPage() == null || pageDTO.getPageSize() == null){
            pageDTO.setPage(1);
            pageDTO.setPageSize(15);
        }
        Page<GoodsCommentEntity> page = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        QueryWrapper<GoodsCommentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", pageDTO.getId());
        queryWrapper.orderByDesc("create_time");
        goodsCommentMapper.selectPage(page,queryWrapper);
        List<GoodsCommentsVO> goodsCommentsVOList = BeanUtil.copyToList(page.getRecords(), GoodsCommentsVO.class);
        Set<Integer> userIds = goodsCommentsVOList.stream()
                .map(GoodsCommentsVO::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Integer, UserInfoVO> userInfoVOMap = new HashMap<>();
        if(!userIds.isEmpty()){
            List<UserInfoEntity> userInfoEntities = userMapper.selectBatchIds(userIds);
            userInfoVOMap = userInfoEntities.stream()
                    .collect(Collectors.toMap(UserInfoEntity::getId,userInfoEntity -> {
                        UserInfoVO userInfoVO = new UserInfoVO();
                        userInfoVO.setUsername(userInfoEntity.getUsername());
                        userInfoVO.setAvatar(userInfoEntity.getAvatar());
                        return userInfoVO;
                    }));
        }
        // 组装VO
        for (GoodsCommentsVO goodsCommentsVO : goodsCommentsVOList) {
            UserInfoVO userInfoVO = userInfoVOMap.get(goodsCommentsVO.getUserId());
            if (userInfoVO != null) {
                goodsCommentsVO.setUserName(userInfoVO.getUsername());
                goodsCommentsVO.setUserAvatar(userInfoVO.getAvatar());
            }
        }
        return new PageResult(page.getTotal(), goodsCommentsVOList);
    }
}
