package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.pojo.pub.goods.Goods;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> queryGoods(GoodsQuery goodsQuery);
}
