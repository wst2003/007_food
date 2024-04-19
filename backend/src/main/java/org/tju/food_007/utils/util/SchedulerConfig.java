package org.tju.food_007.utils.util;

import jakarta.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() {
        try {
            if (scheduler.isInStandbyMode()) {
                scheduler.start();
                System.out.println("调度器已从待机模式中启动。");
                System.out.println(scheduler.isStarted());

            }
        } catch (SchedulerException e) {
            System.err.println("启动调度器时出错：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
