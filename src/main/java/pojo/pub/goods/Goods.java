package pojo.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int goodsId;//商品id
    private String goodsName;//商品名称
    private String goodsType;//商品类型-关联设备类型
    private double goodsPrice;//商品价格
    private String goodsStock;//商品库存
    private int goodsSales;//商品销售量
    private String goodsIntro;//商品介绍
    private int goodsStatus;//商品状态-0表示下架，1表示上架
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
