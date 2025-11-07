package com.anxu.smarthomeunity.pojo.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsComment {
    private int commentId;//商品评论id
    private int goodsId;//商品id
    private int userId;//用户id
    private String commentContent;//评论内容
    private int commentScore;//评论分数
    private int commentLikeNum;//评论点赞数
    private int commentReplyNum;//评论回复数
    private int commentStatus;//评论状态-0表示删除，1表示正常
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
