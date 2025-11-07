package com.anxu.smarthomeunity.service.impl;

import com.anxu.smarthomeunity.mapper.GoodsMapper;
import com.anxu.smarthomeunity.pojo.pub.goods.Goods;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxu.smarthomeunity.pojo.Result.PageResult;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;
import com.anxu.smarthomeunity.service.GoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public PageResult queryGoods(GoodsQuery goodsQuery) {
        PageHelper.startPage(goodsQuery.getPage(), goodsQuery.getPageSize());
        List<Goods> goodsList = goodsMapper.queryGoods(goodsQuery);
        Page<Goods> goodsPage = (Page<Goods>) goodsList;
        return new PageResult(goodsPage.getTotal(), goodsPage.getResult());
    }
}
