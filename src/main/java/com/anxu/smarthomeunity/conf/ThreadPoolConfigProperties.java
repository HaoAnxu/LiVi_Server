package com.anxu.smarthomeunity.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池配置属性类
 *
 * @Author: haoanxu
 * @Date: 2025/11/25 10:56
 */
@Data
@ConfigurationProperties(prefix = "thread-pool")// 关键：指定配置文件的前缀，和.yml里的 thread-pool 对应
public class ThreadPoolConfigProperties {
    private Integer coreSize;          // 核心线程数
    private Integer maxSize;           // 最大线程数
    private Integer queueCapacity;     // 任务队列容量
    private Integer keepAliveTime;     // 空闲线程存活时间（秒）
    private String poolPrefixName;     // 线程名称前缀
}
