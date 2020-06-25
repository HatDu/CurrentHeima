package chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class Test7_Shutdown {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(() ->  {
                    log.debug("task1 running");
                    Thread.sleep(1000);
                    log.debug("task1 finishing");
                    return "task1 finishing";
                }
        );
        Future<String> future2 = pool.submit(() ->  {
                    log.debug("task2 running");
                    Thread.sleep(1000);
                    log.debug("task2 finishing");
                    return "task2 finishing";
                }
        );
        Future<String> future3 = pool.submit(() ->  {
                    log.debug("task3 running");
                    Thread.sleep(1000);
                    log.debug("task3 finishing");
                    return "task3 finishing";
                }
        );
//        log.debug(future.get());
        log.debug("Shutdown");
//        pool.shutdown();
//        pool.awaitTermination(7, TimeUnit.SECONDS);
//        log.debug("other");
        List<Runnable> results =  pool.shutdownNow();

//        Future<String> future4 = pool.submit(() ->  {
//                    log.debug("task4 running");
//                    Thread.sleep(1000);
//                    log.debug("task4 finishing");
//                    return "task4 finishing";
//                }
//        );

    }
}
