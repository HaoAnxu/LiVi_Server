package com.anxu.livi.mapper.goods;

import com.anxu.livi.model.entity.goods.GoodsCommentEntity;
import com.anxu.livi.model.vo.goods.GoodsCommentsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品评论MP接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 11:09
 */
@Mapper
public interface GoodsCommentMapper extends BaseMapper<GoodsCommentEntity> {
}
