package com.anxu.smarthomeunity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anxu.smarthomeunity.pojo.Result.PageResult;
import com.anxu.smarthomeunity.pojo.Result.Result;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;
import com.anxu.smarthomeunity.service.GoodsService;

@Slf4j
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/goods/queryGoods")
    public Result queryGoods(@RequestBody GoodsQuery goodsQuery){
        log.info("查询商品信息，参数：{}",goodsQuery);
        PageResult pageResult = goodsService.queryGoods(goodsQuery);
        return Result.success(pageResult);
    }

}
