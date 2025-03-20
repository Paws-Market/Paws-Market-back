package com.korit.pawsmarket.global.config.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Slf4j
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);   // 동시에 실행할 스레드 수 설정
        scheduler.setThreadNamePrefix("scheduled-task-");   // 스레드 이름 접두사
        scheduler.setAwaitTerminationSeconds(300);  // 이미 실행 중인 작업을 기다릴 최대 시간
        scheduler.setWaitForTasksToCompleteOnShutdown(true);    // 작업 완료까지 스프링 종료를 대기
        scheduler.initialize();     // 스케줄러 초기화

        return scheduler;
    }
}
