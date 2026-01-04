package com.anxu.livi.service;

import com.anxu.livi.model.dto.goods.GoodsOrderDTO;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.vo.goods.GoodsBriefVO;
import com.anxu.livi.model.vo.goods.GoodsCommentsVO;
import com.anxu.livi.model.vo.goods.GoodsDetailVO;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/7 13:32
 */
public interface GoodsService {
    //    查询商品列表
    PageResult queryGoods(GoodsQueryDTO goodsQueryDto);
    //    查询单个商品详情
    GoodsDetailVO queryGoodsDetail(Long goodsId);
    //    查询20个热卖商品
    List<GoodsBriefVO> queryHotGoods();
    //    查询单个商品评论列表
    PageResult queryGoodsComment(PageDTO pageDTO);
    //    下单
    void order(GoodsOrderDTO goodsOrderDTO);
    //    根据model，style和goodsId查询价格
    BigDecimal queryPrice(Integer modelId, Integer styleId, Integer goodsId);
}
