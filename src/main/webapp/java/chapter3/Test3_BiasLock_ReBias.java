package chapter3;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

@Slf4j
public class Test3_BiasLock_ReBias {
    public static void main(String[] args) {
        Vector<Object> list = new Vector<>();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 30; i++){
                Object o1 = new Object();
                list.add(o1);
                log.debug("前：" + i + "\t" + ClassLayout.parseInstance(o1).toPrintable());
                synchronized (o1){
                    log.debug(i + "\t" + ClassLayout.parseInstance(o1).toPrintable());
                }
            }
            synchronized (list){
                list.notify();
            }
        }, "t1");
        t1.start();

//        Thread t2 = new Thread(() -> {
//           synchronized (list){
//               try {
//                   list.wait();
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//
//               for(int i = 0; i < 30; i++){
//                   Object o = list.get(i);
//                   log.debug("前");
//                   System.out.println(i + "\t" + ClassLayout.parseInstance(o).toPrintable());
//
//                   synchronized (o){
//                       log.debug("中");
//                       System.out.println(i + "\t" + ClassLayout.parseInstance(o).toPrintable());
//                   }
//                   log.debug("后");
//                   System.out.println(i + "\t" + ClassLayout.parseInstance(o).toPrintable());
//               }
//           }
//        }, "t2");
//        t2.start();
    }
}
