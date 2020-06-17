package chapter5;

public class Test1_Visualize {
//    static boolean run = true;
    volatile static boolean run = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
                // ....
            }
        });
        t.start();
        Thread.sleep(1000);
        run = false; // 线程t不会如预想的停下来
    }

}
