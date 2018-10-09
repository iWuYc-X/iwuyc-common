package com.iwuyc.tools.commons.thread;

import com.iwuyc.tools.commons.thread.conf.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;

/**
 * 执行服务的工厂方法
 *
 * @author @Neil
 * @since @2017年10月15日
 */
public interface ExecutorServiceFactory {

    /**
     * 创建线程池服务的钩子函数
     *
     * @param config 线程池的配置项
     * @return
     * @author @Neil
     */
    ExecutorService create(ThreadPoolConfig config);
}
