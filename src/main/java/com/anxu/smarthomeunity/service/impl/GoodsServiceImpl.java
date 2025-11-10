package com.anxu.smarthomeunity.service.impl;

import com.anxu.smarthomeunity.mapper.GoodsMapper;
import com.anxu.smarthomeunity.pojo.pub.goods.Goods;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxu.smarthomeunity.pojo.Result.PageResult;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;
import com.anxu.smarthomeunity.service.GoodsService;

import java.util.List;

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    //    查询商品列表
    @Override
    public PageResult queryGoods(GoodsQuery goodsQuery) {
        PageHelper.startPage(goodsQuery.getPage(), goodsQuery.getPageSize());
        List<Goods> goodsList = goodsMapper.queryGoods(goodsQuery);
        Page<Goods> goodsPage = (Page<Goods>) goodsList;
        return new PageResult(goodsPage.getTotal(), goodsPage.getResult());
    }
     //    更新并重新统计商品评分
    @Override
    public Integer resetScore() {
        int updateProductCount = goodsMapper.updateScore();
        if(updateProductCount > 0) {
            log.info("更新商品评分成功，更新商品数量：{}", updateProductCount);
        } else {
            log.info("暂无商品有评论，或所有商品评分已最新，未执行更新");
        }
        return updateProductCount;
    }
    //    查询单个商品详情
    @Override
    public Goods queryGoodsDetail(Long goodsId) {
        log.info("查询商品详情，商品id：{}", goodsId);
        return goodsMapper.selectByPrimaryKey(goodsId);
    }
}
