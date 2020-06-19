package chapter6;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Test39_AtomicArrayLambda {
    public static void main(String[] args) {
        // 普通数组
        demo(
                () -> new int[10],
                (array) -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );
        // 原子数组
        demo(
                () -> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                (array) -> System.out.println(array)
        );
        // 字段更新器
    }

    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFunc,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer
    ){
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFunc.apply(array);
        for(int i = 0; i < length; i++){
            ts.add(new Thread(() -> {
                for(int j = 0; j < 10000; j++){
                    putConsumer.accept(array, j%length);
                }
            }));
        }

        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); // 等待所有线程结束
        printConsumer.accept(array);
    }
}


