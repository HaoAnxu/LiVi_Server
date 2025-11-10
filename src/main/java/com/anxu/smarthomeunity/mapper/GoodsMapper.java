package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.pojo.pub.goods.Goods;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    //    查询商品列表
    List<Goods> queryGoods(GoodsQuery goodsQuery);
    //    更新并重新统计商品评分
    int updateScore();
    //    查询单个商品详情
    Goods selectByPrimaryKey(Long goodsId);
}
