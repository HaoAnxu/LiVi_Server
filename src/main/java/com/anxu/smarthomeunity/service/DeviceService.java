package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.device.DeviceInfoDTO;
import com.anxu.smarthomeunity.model.dto.device.DeviceTaskDTO;
import com.anxu.smarthomeunity.model.vo.device.DeviceInfoVO;

import java.util.List;

/**
 * 设备服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:09
 */
public interface DeviceService {
    //查询所有设备信息
    List<DeviceInfoVO> queryMyDeviceList(Integer userId);
    //查询单个设备信息
    DeviceInfoVO queryMyDevice(Integer deviceId);
    //更改设备执行状态
    boolean changeStatus(Integer deviceId, Integer deviceStatus);
    //添加新设备
    boolean addDevice(DeviceInfoDTO deviceInfoDTO);
    //删除设备
    boolean deleteDevice(Integer deviceId);
}
