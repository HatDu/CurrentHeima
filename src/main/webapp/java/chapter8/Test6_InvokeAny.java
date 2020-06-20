package chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Test6_InvokeAny {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
//        List<FutureTask<String>> list =
        String rst = pool.invokeAny(Arrays.asList(
                ()->{
                    Thread.sleep(500);
                    return "1";
                }
//                ,()->{
//                    Thread.sleep(200);
//                    return "2";
//                },()->{
//                    Thread.sleep(1000);
//                    return "3";
//                }
        ));
        log.debug(rst);
        pool.shutdown();
    }
}
