package chapter4;

public class Test27_Control_Thread_Order_WAN {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 5);
        Thread t1 = new Thread(() -> {
            wn.print("a", 1, 2);
        });
        Thread t2 = new Thread(() -> {
            wn.print("b", 2, 3);
        });
        Thread t3 = new Thread(() -> {
            wn.print("c", 3, 1);
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
/*
输出内容 等待标记 下一个标记
a          1    2
b          2    3
c          3    1
 */
class WaitNotify{
    // 等待标记
    private int flag;
    private int loopNumber;

    public void print(String str, int waitFlag, int nextFlag){
        for(int i = 0; i < loopNumber; i++){
            synchronized (this){
                while(flag != waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
    WaitNotify(int f, int ln){
        flag = f;
        loopNumber = ln;
    }
}