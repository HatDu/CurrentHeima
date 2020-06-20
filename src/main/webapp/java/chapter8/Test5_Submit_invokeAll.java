package chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class Test5_Submit_invokeAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
//        List<FutureTask<String>> list =
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
            ()->{
                Thread.sleep(500);
                return "1";
            },()->{
                Thread.sleep(200);
                return "2";
            },()->{
                Thread.sleep(1000);
                return "3";
            }
        ));
        futures.forEach(f -> {
            try {
                log.debug(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }
}
