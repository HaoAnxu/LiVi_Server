package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.pojo.Result.PageResult;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;

public interface GoodsService {
    PageResult queryGoods(GoodsQuery goodsQuery);
}
