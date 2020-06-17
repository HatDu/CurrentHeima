package chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Test27_Control_Thread_Order {
    static final Object obj = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t1");

        t1.start();
        t2.start();
    }
}
