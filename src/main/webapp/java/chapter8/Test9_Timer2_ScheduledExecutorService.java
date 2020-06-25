package chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.*;

@Slf4j
public class Test9_Timer2_ScheduledExecutorService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Future<Boolean> f = executor.submit(()->{
            log.debug("Running");
            int i = 1 / 0;
            return true;
        });
        log.debug(String.valueOf(f.get()));
    }
    public static void test3(ScheduledExecutorService executor){
        executor.scheduleWithFixedDelay(() -> {
            log.debug("running");
            try {
                // 任务本身时间会影响延时时间
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 延时是从上次任务结束之后计算
        }, 1, 1, TimeUnit.SECONDS);
    }
    public static void test2(ScheduledExecutorService executor){
        executor.scheduleAtFixedRate(() -> {
            log.debug("running");
            try {
                // 任务本身时间会影响延时时间
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
    public static void test1(ScheduledExecutorService executor){
        // 添加两个任务，希望它们都在 1s 后执行
        executor.schedule(() -> {
            System.out.println("任务1，执行时间：" + new Date());
            try { Thread.sleep(2000); } catch (InterruptedException e) { }
        }, 1000, TimeUnit.MILLISECONDS);
        executor.schedule(() -> {
            System.out.println("任务2，执行时间：" + new Date());
        }, 1000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
