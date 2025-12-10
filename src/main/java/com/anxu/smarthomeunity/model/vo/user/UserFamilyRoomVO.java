package com.anxu.smarthomeunity.model.vo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户家庭匹配房间VO
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFamilyRoomVO {
    private Integer roomId;//房间id
    private String roomName;//房间名称
}
