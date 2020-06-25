package chapter8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test10_Schedule {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime time = now.withHour(10).withMinute(16).withSecond(30).withNano(0).with(DayOfWeek.THURSDAY);
        System.out.println(time);
        // 如果当前时间大于周四，则必须跳到下周周四
        if(now.compareTo(time) > 0)
            time = time.plusWeeks(1);
        System.out.println(time);

        long initialDelay = Duration.between(now, time).toMillis();
//        long peroid = 1000*60*60*24*7;
        long period = 1000;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(()->{
            System.out.println("running...");
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
