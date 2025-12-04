package com.anxu.smarthomeunity.model.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备任务Entity
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:47
 */
@Data
@TableName("device_task")
public class DeviceTaskEntity {
    @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    private Integer deviceId;

    private Integer userId;

    private String taskType;//once,for,long

    private Integer permit;//0-不允许，1-允许

    private String cycleRule;//循环规则,仅循环周期任务使用

    private Integer taskStatus;//0 - 已终止 / 1 - 待执行 / 2 - 执行中 / 3 - 异常

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
