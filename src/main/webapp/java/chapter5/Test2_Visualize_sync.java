package chapter5;

public class Test2_Visualize_sync {
    static boolean run = true;
    final static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(true){
                // ....
                synchronized (lock){
                    if(!run){
                        break;
                    }
                }
            }
        });
        t.start();
        Thread.sleep(1000);
        synchronized (lock){
            run = false; // 线程t不会如预想的停下来
        }
        System.out.println();
    }
}
