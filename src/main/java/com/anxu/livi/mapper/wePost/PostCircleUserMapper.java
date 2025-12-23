package com.anxu.livi.mapper.wePost;

import com.anxu.livi.model.entity.wePost.PostCircleEntity;
import com.anxu.livi.model.entity.wePost.PostCircleUserEntity;
import com.anxu.livi.model.entity.wePost.PostInfoEntity;
import com.anxu.livi.model.vo.wePost.PostCircleUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 【用户-圈子关联Mapper】
 *
 * @Author: haoanxu
 * @Date: 2025/12/23
 */
@Mapper
public interface PostCircleUserMapper extends BaseMapper<PostCircleUserEntity> {
    // 查询用户加入的圈子列表
    List<PostCircleEntity> selectUserCircleInfo(Integer id);
}
