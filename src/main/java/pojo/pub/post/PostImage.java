package pojo.pub.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImage {
    private int imageId;//帖子配图id
    private int postId;//帖子id
    private String imageUrl;//图片url
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
