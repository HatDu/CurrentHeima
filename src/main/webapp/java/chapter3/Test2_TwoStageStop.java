package chapter3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test2_TwoStageStop {
    public static void main(String[] args) {
        TPTInterrupt tpt = new TPTInterrupt();
        tpt.start();
        try {
//            Thread.sleep(200);
            Thread.sleep(2000);
            tpt.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Slf4j
class TPTInterrupt{
    private Thread thread;
    public void start(){
        thread = new Thread(() -> {
            while(true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
                    log.debug("正常中断");
                    clear();
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug("睡眠时被打断");
                    log.debug(String.valueOf(current.isInterrupted()));
                    current.interrupt();
                }
            }
        }, "监控线程");
        thread.start();
    }
    public void stop(){
        thread.interrupt();
    }
    public void clear(){
        log.debug("料理后事");
    }
}