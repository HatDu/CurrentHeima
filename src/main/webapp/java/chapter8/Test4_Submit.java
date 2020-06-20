package chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class Test4_Submit {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(() ->  {
                log.debug("running");
                Thread.sleep(1000);
                return "ok";
            }
        );
        log.debug(future.get());
        pool.shutdown();
    }
}
