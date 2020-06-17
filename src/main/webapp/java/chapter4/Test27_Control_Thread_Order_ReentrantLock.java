package chapter4;

import java.io.PrintStream;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test27_Control_Thread_Order_ReentrantLock {
    static char ch = 'a';
    static int loopNum = 5;


    public static void main(String[] args) {
        ReetrantLockOrder rlo = new ReetrantLockOrder(3, 5);
        new Thread(() -> {
            rlo.print(0);
        }).start();

        new Thread(() -> {
            rlo.print(1);
        }).start();

        new Thread(() -> {
            rlo.print(2);
        }).start();
    }
}

class ReetrantLockOrder{
    private int flag;
    private int loopNum;
    private int maxFlags;
    static ReentrantLock lock;
    Condition[] conditions;
    static final char ch = 'a';
    public ReetrantLockOrder(int mf, int l){
        maxFlags = mf;
        flag = 0;
        loopNum = l;
        lock = new ReentrantLock();
        conditions = new Condition[mf];
        for(int i = 0; i < maxFlags; i++)
            conditions[i] = lock.newCondition();
    }
    public void print(int waitFlag){
        for(int i = 0; i < loopNum; i++){
            try {
                lock.lock();
                if(flag != waitFlag)
                    conditions[waitFlag].await();
                System.out.println((char)(ch + waitFlag));
                flag += 1;
                flag %= maxFlags;
                conditions[flag].signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
