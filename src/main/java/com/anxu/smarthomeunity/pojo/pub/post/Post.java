package com.anxu.smarthomeunity.pojo.pub.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int postId;//社区帖子id
    private int communityId;//社区id
    private int userId;//用户id
    private String postTitle;//帖子标题
    private String postContent;//帖子内容
    private int postLikeNum;//帖子点赞数
    private int postReplyNum;//帖子评论数
    private int postStatus;//帖子状态(0 正常，1 被删除)
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
