package com.anxu.smarthomeunity.model.vo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 设备任务VO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTaskVO {
    private Integer taskId;
    private Integer deviceId;
    private Integer userId;
    private String taskType;//once,for,long
    private Integer permit;//0-不允许，1-允许
    private Integer task_status;//0 - 已终止 / 1 - 待执行 / 2 - 执行中 / 3 - 异常
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
}
