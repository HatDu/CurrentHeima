package chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Test3_SingleThreadPool {
    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 自定义线程名
//        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
//            private AtomicInteger t = new AtomicInteger(1);
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r, "my_pool" + t.getAndIncrement());
//            }
//        });
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("1");
        });
        pool.execute(()->{
            int i = 1 / 0;
            log.debug("2");
        });
        pool.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("3");
        });

    }
}
