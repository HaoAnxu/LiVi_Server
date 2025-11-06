package pojo.pub.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {
    private int likeId;//帖子点赞id
    private int postId;//帖子id
    private int userId;//用户id
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
