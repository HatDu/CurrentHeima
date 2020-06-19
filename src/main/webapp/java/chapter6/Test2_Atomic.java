package chapter6;

import com.sun.media.sound.SoftMixingMainMixer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class Test2_Atomic {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();
        i.incrementAndGet();
        i.getAndIncrement();
        i.addAndGet(3);

        System.out.println(i.get());

        // lambda表达式
        i.updateAndGet(x -> x * 10);
        System.out.println(i.get());

        updateAndGet(i, x -> x*x);
        System.out.println(i.get());
    }
    public static void updateAndGet(AtomicInteger i, IntUnaryOperator operator){
        while(true){
            int pre = i.get();
            int next = operator.applyAsInt(pre);
            if(i.compareAndSet(pre, next))  break;
        }
    }
}
