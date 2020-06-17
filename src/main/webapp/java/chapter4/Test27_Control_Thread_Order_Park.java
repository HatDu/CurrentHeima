package chapter4;

import javax.sound.midi.Soundbank;
import java.util.concurrent.locks.LockSupport;

public class Test27_Control_Thread_Order_Park {
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args) {
        ParkUnpark pu = new ParkUnpark(5);

        t1 = new Thread(() -> {
            pu.print("a", t2);
        });
        t2 = new Thread(() -> {
            pu.print("b", t3);
        });
        t3 = new Thread(() -> {
            pu.print("c", t1);
        });
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }
}

class ParkUnpark{
    private int loopNumber;
    public ParkUnpark(int loopNumber){
        this.loopNumber = loopNumber;
    }
    public void print(String msg, Thread next){
        for(int i = 0; i < loopNumber; i++){
            LockSupport.park();
            System.out.println(msg);
            LockSupport.unpark(next);
        }
    }
}