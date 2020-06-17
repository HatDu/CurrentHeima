package chapter3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Test1_Interrupt {
    static final Object lock = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread current = Thread.currentThread();
                log.debug(String.valueOf(current.isInterrupted()));
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread current = Thread.currentThread();
                    log.debug(String.valueOf(current.isInterrupted()));
                }
            }
        }, "t2");

        Thread t3 = new Thread(() -> {

            while (true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
                    log.debug(String.valueOf(current.isInterrupted()));
                    return;
                }
            }
        }, "t3");

        Thread t4 = new Thread(() -> {
            try {
                t3.join();
            } catch (InterruptedException e) {
                Thread current = Thread.currentThread();
                log.debug(String.valueOf(current.isInterrupted()));
            }
        }, "t4");

        Thread t5 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            LockSupport.park();
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
        }, "t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.interrupt();
        t2.interrupt();
        t4.interrupt();
        t3.interrupt();
        t5.interrupt();
    }
}
