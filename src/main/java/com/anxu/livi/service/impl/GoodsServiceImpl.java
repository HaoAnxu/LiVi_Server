package com.anxu.livi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.anxu.livi.mapper.goods.*;
import com.anxu.livi.mapper.user.UserMapper;
import com.anxu.livi.model.dto.goods.GoodsOrderDTO;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.entity.goods.*;
import com.anxu.livi.model.entity.user.UserInfoEntity;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.vo.goods.*;
import com.anxu.livi.model.vo.user.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;
import com.anxu.livi.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private GoodsModelMapper goodsModelMapper;
    @Autowired
    private GoodsStyleMapper goodsStyleMapper;
    @Autowired
    private GoodsOrderMapper goodsOrderMapper;
    @Autowired
    private GoodsConnectMapper goodsConnectMapper;


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
        if ("by_price_asc".equals(sortRule)) {
            // 价格升序（便宜优先）
            queryWrapper.orderByAsc("goods_price");
        } else if ("by_price_desc".equals(sortRule)) {
            // 价格降序（贵的优先）
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
        // 执行分页查询
        Page<GoodsEntity> resultPage = this.goodsMapper.selectPage(page, queryWrapper);
        List<GoodsEntity> goodsEntityList = resultPage.getRecords();
        if (CollectionUtils.isEmpty(goodsEntityList)) {
            return new PageResult(resultPage.getTotal(), Collections.emptyList());
        }
        // 收集商品ID
        List<Integer> goodsIds = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            goodsIds.add(goodsEntity.getGoodsId());
        }
        // 批量查询所有商品的信息
        QueryWrapper<GoodsConnectEntity> queryWrapperGoods = new QueryWrapper<>();
        queryWrapperGoods.in("goods_id", goodsIds)
                .eq("is_show", 1)
                .gt("goods_stock", 0);
        List<GoodsConnectEntity> allConnectList = goodsConnectMapper.selectList(queryWrapperGoods);
        // 按照goodsId分组，缓存每个商品的最小价格
        Map<Integer, BigDecimal> goodsMinPriceMap = new HashMap<>();
        for (GoodsConnectEntity connect : allConnectList) {
            Integer gid = connect.getGoodsId();
            BigDecimal price = connect.getGoodsPrice();

            // 对比更新最小价格
            if (goodsMinPriceMap.containsKey(gid)) {
                BigDecimal currentMin = goodsMinPriceMap.get(gid);
                if (price.compareTo(currentMin) < 0) {
                    goodsMinPriceMap.put(gid, price);
                }
            } else {
                goodsMinPriceMap.put(gid, price);
            }
        }

        // 组装VO列表（处理空值+计算原价）
        List<GoodsBriefVO> goodsBriefVOList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            GoodsBriefVO goodsBriefVO = BeanUtil.copyProperties(goodsEntity, GoodsBriefVO.class);
            Integer goodsId = goodsEntity.getGoodsId();

            BigDecimal minPrice = goodsMinPriceMap.getOrDefault(goodsId, BigDecimal.ZERO);
            if (minPrice == null) {
                minPrice = BigDecimal.ZERO;
            }

            // 计算原价（最小价格*1.2，保留2位小数）
            BigDecimal originalPrice = minPrice.multiply(new BigDecimal("1.2"))
                    .setScale(2, RoundingMode.HALF_UP);

            // 设置价格
            goodsBriefVO.setGoodsPrice(minPrice); // 商品最低价
            goodsBriefVO.setGoodsOriginalPrice(originalPrice); // 原价

            goodsBriefVOList.add(goodsBriefVO);
        }

        // 返回分页结果
        return new PageResult(resultPage.getTotal(), goodsBriefVOList);
    }

    //    查询单个商品详情
    @Override
    public GoodsDetailVO queryGoodsDetail(Long goodsId) {
        log.info("查询商品详情，商品id：{}", goodsId);
        //先查详情的基础数据
        GoodsEntity goodsEntity = goodsMapper.selectById(goodsId);
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
        List<GoodsImageEntity> goodsImageEntityList = goodsImageMapper.selectList(queryWrapper);
        //将goodsImageEntity转换为goodsImageVO
        List<GoodsImageVO> goodsImageVOList = new ArrayList<>();
        for (GoodsImageEntity goodsImageEntity : goodsImageEntityList) {
            GoodsImageVO goodsImageVO = BeanUtil.copyProperties(goodsImageEntity, GoodsImageVO.class);
            goodsImageVOList.add(goodsImageVO);
        }
        goodsDetailVO.setGoodsImageVOList(goodsImageVOList);

        //批量查询价格
        QueryWrapper<GoodsConnectEntity> queryWrapperConnect = new QueryWrapper<>();
        queryWrapperConnect.eq("goods_id", goodsId)
                .eq("is_show", 1)
                .gt("goods_stock", 0);
        // 执行查询
        List<GoodsConnectEntity> goodsConnectEntityList = goodsConnectMapper.selectList(queryWrapperConnect);
        BigDecimal minPrice = BigDecimal.ZERO;
        for (GoodsConnectEntity goodsConnectEntity : goodsConnectEntityList) {
            BigDecimal price = goodsConnectEntity.getGoodsPrice();
            if (minPrice.compareTo(price) > 0 || minPrice.equals(BigDecimal.ZERO)) {
                minPrice = price;
            }
        }
        // 设置商品最低价
        goodsDetailVO.setGoodsPrice(minPrice);
        //再查型号
        QueryWrapper<GoodsModelEntity> queryWrapperModel = new QueryWrapper<>();
        queryWrapperModel.eq("goods_id", goodsId);
        // 执行查询
        List<GoodsModelEntity> goodsModelEntityList = goodsModelMapper.selectList(queryWrapperModel);
        //将goodsModelEntity转换为goodsModelVO
        List<GoodsModelVO> goodsModelVOList = new ArrayList<>();
        for (GoodsModelEntity goodsModelEntity : goodsModelEntityList) {
            GoodsModelVO goodsModelVO = BeanUtil.copyProperties(goodsModelEntity, GoodsModelVO.class);
            goodsModelVOList.add(goodsModelVO);
        }
        goodsDetailVO.setGoodsModelVOList(goodsModelVOList);

        //再查款式
        QueryWrapper<GoodsStyleEntity> queryWrapperStyle = new QueryWrapper<>();
        queryWrapperStyle.eq("goods_id", goodsId);
        // 执行查询
        List<GoodsStyleEntity> goodsStyleEntityList = goodsStyleMapper.selectList(queryWrapperStyle);
        //将goodsStyleEntity转换为goodsStyleVO
        List<GoodsStyleVO> goodsStyleVOList = new ArrayList<>();
        for (GoodsStyleEntity goodsStyleEntity : goodsStyleEntityList) {
            GoodsStyleVO goodsStyleVO = BeanUtil.copyProperties(goodsStyleEntity, GoodsStyleVO.class);
            goodsStyleVOList.add(goodsStyleVO);
        }
        goodsDetailVO.setGoodsStyleVOList(goodsStyleVOList);

        return goodsDetailVO;
    }

    //    查询12个热卖商品
    @Override
    public List<GoodsBriefVO> queryHotGoods() {
        // 查询12个热卖商品
        QueryWrapper<GoodsEntity> goodsQuery = new QueryWrapper<>();
        goodsQuery.orderByDesc("goods_sales")  // 按销量降序
                .last("limit 12");           // 限制12条
        List<GoodsEntity> goodsEntityList = goodsMapper.selectList(goodsQuery);
        if (CollectionUtils.isEmpty(goodsEntityList)) {
            return Collections.emptyList(); // 无商品直接返回空列表
        }

        // 收集所有商品ID
        List<Integer> goodsIds = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            goodsIds.add(goodsEntity.getGoodsId());
        }
        // 批量查询所有商品的关联价格
        QueryWrapper<GoodsConnectEntity> connectQuery = new QueryWrapper<>();
        connectQuery.in("goods_id", goodsIds)
                .eq("is_show", 1)
                .gt("goods_stock", 0);
        List<GoodsConnectEntity> allConnectList = goodsConnectMapper.selectList(connectQuery);

        // 按goodsId分组，缓存每个商品的最小价格（Map<goodsId, 最小价格>）
        Map<Integer, BigDecimal> goodsMinPriceMap = new HashMap<>();
        for (GoodsConnectEntity connect : allConnectList) {
            Integer gid = connect.getGoodsId();
            BigDecimal price = connect.getGoodsPrice();

            // 对比更新最小价格
            if (goodsMinPriceMap.containsKey(gid)) {
                BigDecimal currentMin = goodsMinPriceMap.get(gid);
                // BigDecimal用compareTo比较大小：-1=小于，0=等于，1=大于
                if (price.compareTo(currentMin) < 0) {
                    goodsMinPriceMap.put(gid, price);
                }
            } else {
                // 首次存入该商品的价格（作为初始最小值）
                goodsMinPriceMap.put(gid, price);
            }
        }
        // 组装VO列表
        List<GoodsBriefVO> goodsBriefVOList = new ArrayList<>();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            GoodsBriefVO goodsBriefVO = BeanUtil.copyProperties(goodsEntity, GoodsBriefVO.class);
            Integer goodsId = goodsEntity.getGoodsId();

            BigDecimal minPrice = goodsMinPriceMap.getOrDefault(goodsId, BigDecimal.ZERO);
            if (minPrice == null) {
                minPrice = BigDecimal.ZERO;
            }

            // 计算原价（最小价格 * 1.2，保留2位小数，四舍五入）
            BigDecimal originalPrice = minPrice.multiply(new BigDecimal("1.2"))
                    .setScale(2, RoundingMode.HALF_UP);

            // 设置价格（VO字段改为BigDecimal类型，避免精度丢失）
            goodsBriefVO.setGoodsPrice(minPrice); // 商品当前最低价
            goodsBriefVO.setGoodsOriginalPrice(originalPrice); // 原价

            goodsBriefVOList.add(goodsBriefVO);
        }
        return goodsBriefVOList;
    }

    //    查询单个商品评论列表
    @Override
    public PageResult queryGoodsComment(PageDTO pageDTO) {
        if (pageDTO.getPage() == null || pageDTO.getPageSize() == null) {
            pageDTO.setPage(1);
            pageDTO.setPageSize(15);
        }
        Page<GoodsCommentEntity> page = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        QueryWrapper<GoodsCommentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", pageDTO.getId());
        queryWrapper.orderByDesc("create_time");
        goodsCommentMapper.selectPage(page, queryWrapper);
        List<GoodsCommentsVO> goodsCommentsVOList = BeanUtil.copyToList(page.getRecords(), GoodsCommentsVO.class);
        Set<Integer> userIds = goodsCommentsVOList.stream()
                .map(GoodsCommentsVO::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Integer, UserInfoVO> userInfoVOMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<UserInfoEntity> userInfoEntities = userMapper.selectBatchIds(userIds);
            userInfoVOMap = userInfoEntities.stream()
                    .collect(Collectors.toMap(UserInfoEntity::getId, userInfoEntity -> {
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

    // 根据model，style和goodsId查询价格
    @Override
    public BigDecimal queryPrice(Integer modelId, Integer styleId, Integer goodsId) {
        // 校验
        Assert.isTrue(goodsId != null, "商品id不能为空");
        QueryWrapper<GoodsConnectEntity> queryWrapper = new QueryWrapper<>();
        if (modelId != null && modelId != 0) {
            queryWrapper.eq("model_id", modelId);
        }
        if (styleId != null && styleId != 0) {
            queryWrapper.eq("style_id", styleId);
        }
        queryWrapper.eq("goods_id", goodsId);
        GoodsConnectEntity goodsConnectEntity = goodsConnectMapper.selectOne(queryWrapper);
        if (goodsConnectEntity == null) {
            throw new IllegalArgumentException("商品不存在");
        }
        return goodsConnectEntity.getGoodsPrice();
    }

    //    创建订单
    @Override
    public void order(GoodsOrderDTO goodsOrderDTO) {
        // 校验
        Assert.isTrue(goodsOrderDTO.getGoodsId() != null, "商品id不能为空");
        Assert.isTrue(goodsOrderDTO.getUserId() != null, "用户id不能为空");
        Assert.isTrue(goodsOrderDTO.getGoodsNum() != null, "商品数量不能为空");
        Assert.isTrue(goodsOrderDTO.getOrderAddress() != null, "订单收货地址不能为空");
        Assert.isTrue(goodsOrderDTO.getOrderPrice() != null, "订单价格不能为空");
        if (goodsMapper.selectById(goodsOrderDTO.getGoodsId()) == null) {
            throw new IllegalArgumentException("商品不存在");
        }
        // 创建订单
        GoodsOrderEntity goodsOrderEntity = BeanUtil.copyProperties(goodsOrderDTO, GoodsOrderEntity.class);
        // 生成订单编号
        StringBuilder orderNoBuilder = new StringBuilder();
        orderNoBuilder.append("LIVI")
                .append(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()))
                .append(UUID.randomUUID().toString().replace("-", ""), 0, 10);
        String orderNo = orderNoBuilder.toString();
        goodsOrderEntity.setOrderNo(orderNo);
        goodsOrderEntity.setOrderStatus(0);
        goodsOrderMapper.insert(goodsOrderEntity);
    }
}
