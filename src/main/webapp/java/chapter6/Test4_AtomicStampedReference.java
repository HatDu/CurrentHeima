package chapter6;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
public class Test4_AtomicStampedReference {
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
    public static void main(String[] args) {
        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("{}:v{}", prev, stamp);
        other();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("{}", stamp);
        log.debug("change A --> C {}", ref.compareAndSet(prev, "C", stamp, stamp+1));

    }
    public static void other(){
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("{}", stamp);
            log.debug("change A --> B {}", ref.compareAndSet(ref.getReference(), "B", stamp, stamp+1));
        }, "t1").start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("{}", stamp);
            log.debug("change B --> A {}", ref.compareAndSet(ref.getReference(), "A", stamp, stamp+1));
        }, "t2").start();
    }
}

