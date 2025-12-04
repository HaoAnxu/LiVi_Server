package com.anxu.smarthomeunity.model.dto.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 设备任务DTO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTaskDTO {
    private Integer deviceId;
    private Integer userId;
    private String taskType;//once,for,long
    private Integer permit;//0-不允许,1-允许
    private String cycle_rule;//循环规则,仅循环周期任务使用
    private Integer task_status;//0 - 已终止 / 1 - 待执行 / 2 - 执行中 / 3 - 异常
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
