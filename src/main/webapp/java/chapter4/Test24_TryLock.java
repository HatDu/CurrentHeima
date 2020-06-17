package chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test24_TryLock {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
        //            if (!lock.tryLock()) {
        //                log.debug("获取立刻失败，返回");
        //                // 没有获得锁，立即返回
        //                return;
        //            }
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("延时获得锁失败，返回");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            Thread.sleep(2);
            // t1可以被打断
        //            t1.interrupt();
        } finally {
            lock.unlock();
        }
    }
}
