package com.example.springschedulershedlock;

import java.time.LocalDateTime;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class SchedulerComponent {

    @Scheduled(cron = "0 * * * * *")
    @SchedulerLock(name = "shortRunningTask", lockAtMostFor = "60s", lockAtLeastFor = "20s")
    public void schedule() {
        // lock 구성 확인. 오류 방지.
        LockAssert.assertLocked();

        // do SomeThing.
        try {
            System.out.println(LocalDateTime.now() + " scheduler job start!");
            Thread.sleep(1000 * 10);
            System.out.println(LocalDateTime.now() + " scheduler job end!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
