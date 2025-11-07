package com.anxu.smarthomeunity.pojo.pub.goods.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsQuery {
    private String goodsName;
    private String goodsType;
    private String sortRule;
    private int page = 1;
    private int pageSize = 10;
}
