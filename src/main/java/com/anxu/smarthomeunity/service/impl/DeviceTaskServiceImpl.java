package com.anxu.smarthomeunity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.smarthomeunity.common.emums.Device.DeviceTaskTypeEnum;
import com.anxu.smarthomeunity.mapper.device.DeviceInfoMapper;
import com.anxu.smarthomeunity.mapper.device.DeviceTaskMapper;
import com.anxu.smarthomeunity.model.dto.device.DeviceTaskDTO;
import com.anxu.smarthomeunity.model.entity.device.DeviceTaskEntity;
import com.anxu.smarthomeunity.service.DeviceTaskService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 设备定时任务服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 15:40
 */
@Slf4j
@Service
public class DeviceTaskServiceImpl implements DeviceTaskService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private DeviceTaskMapper deviceTaskMapper;

    //创建设备执行任务
    @Override
    public boolean createTask(DeviceTaskDTO deviceTaskDTO) {
        log.info("开始创建设备任务，入参：{}", deviceTaskDTO);
        DeviceTaskEntity deviceTask = BeanUtil.copyProperties(deviceTaskDTO, DeviceTaskEntity.class);
        if (Objects.equals(deviceTask.getTaskType(), DeviceTaskTypeEnum.FOR.getCode())) {
            //先校验非空，再校验格式
            if (deviceTask.getCycleRule() == null || deviceTask.getCycleRule().trim().isEmpty()
                    || !CronExpression.isValidExpression(deviceTask.getCycleRule().trim())) {
                log.error("周期任务cron表达式校验失败，cycleRule：{}", deviceTask.getCycleRule());
                return false;
            }
            log.info("周期任务cron表达式校验通过：{}", deviceTask.getCycleRule());
        } else {
            // 非for类型强制置空
            deviceTask.setCycleRule(null);
            log.info("非周期任务，清空cycleRule，任务类型：{}", deviceTask.getTaskType());
        }
        boolean result = deviceTaskMapper.insert(deviceTask) > 0;
        log.info("创建设备任务结束，任务ID：{}，结果：{}", deviceTask.getTaskId(), result);
        return result;
    }

    //停止到时间的定时任务
    @Async("taskExecutor")
    @Override
    public void stopDeviceTask(LocalDateTime now) {
        log.info("【停止任务】开始执行，当前时间：{}", now);
        //查询所有符合条件的任务
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_type", "once", "for")
                .eq("task_status", 2)
                .eq("permit", String.valueOf(1))
                .lt("end_time", now);
        log.info("【停止任务】查询条件：{}", queryWrapper.getCustomSqlSegment());
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【停止任务】查询到符合条件的任务数：{}，任务列表：{}", deviceTaskList.size(), deviceTaskList);

        if (deviceTaskList.isEmpty()) {
            log.warn("【停止任务】未查询到需要停止的任务");
            return;
        }

        for (DeviceTaskEntity currentItem : deviceTaskList) {
            log.info("【停止任务】处理任务ID：{}，设备ID：{}，任务类型：{}",
                    currentItem.getTaskId(), currentItem.getDeviceId(), currentItem.getTaskType());
            // 先更新设备状态为0（关闭）
            deviceInfoMapper.updateByDeviceId(currentItem.getDeviceId(), 0);
            log.info("【停止任务】设备ID：{} 状态已更新为0（关闭）", currentItem.getDeviceId());
            // 再更新任务状态
            switch (currentItem.getTaskType()) {
                case "for":
                    currentItem.setTaskStatus(1); // 重置为待执行，次日仍可触发
                    log.info("【停止任务】周期任务ID：{} 状态重置为1（待执行）", currentItem.getTaskId());
                    break;
                case "once":
                    currentItem.setTaskStatus(0); // 终止
                    currentItem.setPermit(0);     // 禁止执行，避免重复触发
                    log.info("【停止任务】一次性任务ID：{} 状态置为0（终止），permit置为0", currentItem.getTaskId());
                    break;
            }
        }
        // 批量更新
        deviceTaskMapper.stopTaskByTaskId(deviceTaskList);
        log.info("【停止任务】批量更新{}条任务状态完成", deviceTaskList.size());
    }

    //启动到时间的定时任务
    @Async("taskExecutor")
    @Override
    public void startDeviceTask(LocalDateTime now) {
        log.info("【启动任务】开始执行，当前时间：{}", now);
        //筛选任务，统一条件
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_type", "once", "for","long")
                .eq("task_status", 1)
                .eq("permit", String.valueOf(1));
        //分组
        queryWrapper.and(wrapper->wrapper
                .nested(nested->nested
                        .eq("task_type","once")
                        .le("begin_time",now)
                        .ge("end_time",now)
                )
                .or()
                .nested(nested->nested
                        .eq("task_type","for")
                        .ge("end_time",now)
                        // cron匹配需要自定义SQL函数或内存过滤，这里先保留字段条件，后续内存过滤
                        .isNotNull("cycle_rule")
                )
                .or()
                .nested(nested->nested
                        .eq("task_type","long")
                        .le("begin_time",now)
                        .ge("end_time",now)
                )
        );
        log.info("【启动任务】查询条件：{}", queryWrapper.getCustomSqlSegment());
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【启动任务】初始查询到任务数：{}，任务列表：{}", deviceTaskList.size(), deviceTaskList);

        //对for类型的进行过滤
        List<DeviceTaskEntity> finalDeviceTaskList = deviceTaskList.stream()
                .filter(task->{
                    if("for".equals(task.getTaskType())){
                        boolean cronMatch = isCronMatch(task,now);
                        log.info("【启动任务】FOR类型任务ID：{}，cron表达式：{}，当前时间：{}，匹配结果：{}",
                                task.getTaskId(), task.getCycleRule(), now, cronMatch);
                        return cronMatch;
                    }
                    log.info("【启动任务】非FOR类型任务ID：{}，直接通过过滤", task.getTaskId());
                    return true;
                }).toList();

        log.info("【启动任务】过滤后最终任务数：{}，任务列表：{}", finalDeviceTaskList.size(), finalDeviceTaskList);

        if (finalDeviceTaskList.isEmpty()) {
            log.warn("【启动任务】无符合条件的任务，无需更新");
            return;
        }

        // 批量更新任务状态
        for (DeviceTaskEntity task : finalDeviceTaskList) {
            task.setTaskStatus(2);//设置为执行中
            task.setUpdateTime(now);//设置更新时间为当前时间
            log.info("【启动任务】任务ID：{} 状态更新为2（执行中），更新时间：{}", task.getTaskId(), now);
        }
        deviceTaskMapper.startTaskByTaskId(finalDeviceTaskList);
        log.info("【启动任务】批量更新{}条任务状态完成", finalDeviceTaskList.size());

        //更新设备状态
        finalDeviceTaskList.stream()
                .map(DeviceTaskEntity::getDeviceId)
                .distinct()
                .forEach(deviceId -> {
                    log.info("【启动任务】设备ID：{} 状态更新为1（执行中）", deviceId);
                    deviceInfoMapper.updateByDeviceId(deviceId, 1); // 设备状态置为执行中
                });
        log.info("【启动任务】所有设备状态更新完成，总计更新{}个设备",
                finalDeviceTaskList.stream().map(DeviceTaskEntity::getDeviceId).distinct().count());
    }

    //for类型的cron校验
    private boolean isCronMatch(DeviceTaskEntity task, LocalDateTime now) {
        log.info("【Cron校验】任务ID：{}，cycleRule：{}，当前时间：{}", task.getTaskId(), task.getCycleRule(), now);
        if (task.getCycleRule() == null || task.getCycleRule().isEmpty()) {
            log.error("【Cron校验】任务ID：{} cron表达式为空，校验失败", task.getTaskId());
            return false;
        }
        try {
            CronExpression cron = CronExpression.parse(task.getCycleRule());
            LocalDateTime nextTrigger = cron.next(now.truncatedTo(ChronoUnit.SECONDS).minusNanos(1));
            log.info("【Cron校验】任务ID：{}，计算出的下一次触发时间：{}，当前时间（截断秒）：{}",
                    task.getTaskId(), nextTrigger, now.truncatedTo(ChronoUnit.SECONDS));
            return nextTrigger != null && nextTrigger.truncatedTo(ChronoUnit.SECONDS).equals(now.truncatedTo(ChronoUnit.SECONDS));
        } catch (Exception e) {
            log.error("【Cron校验】任务ID：{} cron表达式解析失败，错误：{}", task.getTaskId(), e.getMessage(), e);
            return false;
        }
    }
}
