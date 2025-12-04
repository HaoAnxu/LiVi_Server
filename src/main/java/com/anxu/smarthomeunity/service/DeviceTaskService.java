package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.device.DeviceTaskDTO;

import java.time.LocalDateTime;

/**
 * 设备定时任务服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 15:39
 */
public interface DeviceTaskService {
    //创建设备定时任务
    boolean createTask(DeviceTaskDTO deviceTaskDTO);
    //停止到时间的定时任务
    void stopDeviceTask(LocalDateTime now);
    //启动到时间的定时任务
    void startDeviceTask(LocalDateTime now);
}
