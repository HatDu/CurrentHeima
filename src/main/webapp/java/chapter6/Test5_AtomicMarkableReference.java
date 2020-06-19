package chapter6;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

@Slf4j
public class Test5_AtomicMarkableReference {
    public static void main(String[] args) {
        GarbageBag gb = new GarbageBag("满的垃圾袋");
        AtomicMarkableReference<GarbageBag> gbmr = new AtomicMarkableReference<>(gb, true);

        GarbageBag ref = gbmr.getReference();
        log.debug(ref.toString());

        new Thread(() -> {
            boolean success = gbmr.compareAndSet(gbmr.getReference(), new GarbageBag("空的垃圾袋"), true, false);
            log.debug("清洁工替换垃圾袋：{}", success);
        }, "清洁工").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("想换新的垃圾袋？");
        boolean success = gbmr.compareAndSet(ref, new GarbageBag("空的垃圾袋"), true, false);
        log.debug("替换成功？{}", success);
    }
}

class GarbageBag{
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GarbageBag{" +
                "desc='" + desc + '\'' +
                '}';
    }

    String desc;
    public GarbageBag(String desc){
        this.desc = desc;
    }
}