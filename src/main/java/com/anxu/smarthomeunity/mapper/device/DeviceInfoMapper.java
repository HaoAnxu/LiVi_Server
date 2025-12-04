package com.anxu.smarthomeunity.mapper.device;

import com.anxu.smarthomeunity.model.entity.device.DeviceInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备信息Mapper
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:10
 */
@Mapper
public interface DeviceInfoMapper extends BaseMapper<DeviceInfoEntity> {
    //根据设备id更新设备状态
    void updateByDeviceId(Integer deviceId,Integer deviceStatus);
}
