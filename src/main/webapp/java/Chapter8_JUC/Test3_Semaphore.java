package Chapter8_JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class Test3_Semaphore {
    public static void main(String[] args) {
// 1. 创建 semaphore 对象
        Semaphore semaphore = new Semaphore(3);
// 2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
// 3. 获取许可
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    Thread.sleep(1000);
                    log.debug("end...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
// 4. 释放许可
                    semaphore.release();
                }
            }).start();
        }
    }
}
