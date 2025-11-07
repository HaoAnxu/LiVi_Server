package com.anxu.smarthomeunity.pojo.pub.postComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentImage {
    private int imageId;//评论配图id
    private int commentId;//评论id
    private String imageUrl;//图片url
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
