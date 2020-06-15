package chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "Test22_Interrrupt")
public class Test22_Interrrupt {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            }catch (InterruptedException e){
                e.printStackTrace();
                log.debug("在等待过程中被打断");
            }
        }, "t1");

        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            Thread.sleep(1);
            t1.interrupt();
            log.debug("执行打断");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
