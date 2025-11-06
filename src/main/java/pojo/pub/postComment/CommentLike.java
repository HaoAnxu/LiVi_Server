package pojo.pub.postComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLike {
    private int likeId;//评论点赞id
    private int commentId;//评论id
    private int userId;//用户id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
