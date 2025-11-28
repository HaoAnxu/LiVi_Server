package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.Result.PageResult;
import com.anxu.smarthomeunity.model.vo.goods.GoodsDetailVO;
import com.anxu.smarthomeunity.model.dto.goods.GoodsQueryDto;

/**
 * 商品相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/7 13:32
 */
public interface GoodsService {
    //    查询商品列表
    PageResult queryGoods(GoodsQueryDto goodsQueryDto);
    //    更新并重新统计商品评分
    Integer resetScore();
    //    查询单个商品详情
    GoodsDetailVO queryGoodsDetail(Long goodsId);
}
