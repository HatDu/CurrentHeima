package chapter4;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test27_Control_Thread_Order_ReentrantLock2 {
    public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(5);
        Condition c1 = as.newCondition();
        Condition c2 = as.newCondition();
        Condition c3 = as.newCondition();
        new Thread(() -> {
           as.print("a", c1, c2);
        }).start();
        new Thread(() -> {
           as.print("b", c2, c3);
        }).start();
        new Thread(() -> {
           as.print("c", c3, c1);
        }).start();

        as.start(c1);
    }
}

@Slf4j
class AwaitSignal extends ReentrantLock{
    private int loopNumber;
    public AwaitSignal(int loopNumber){
        this.loopNumber = loopNumber;
    }
    public void print(String msg, Condition current, Condition next){
        for(int i = 0; i < loopNumber; i++){
            this.lock();
            try {
                current.await();
                System.out.print(msg);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                unlock();
            }
        }
    }
    public void start(Condition d){
        this.lock();
        try {
            log.debug("start");
            d.signal();
        }finally {
            this.unlock();
        }
    }
}