package Chapter8_JUC;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.DATA_CONVERSION;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test2_ReentrantLockReadWriteLock {
    public static void main(String[] args) {
        DataContainer  dataContainer = new DataContainer();
        new Thread(() -> {
//            dataContainer.read();
            dataContainer.write();
        }, "t1").start();
        new Thread(() -> {
//            dataContainer.read();
            dataContainer.write();
        }, "t2").start();
    }
}

@Slf4j
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();
    public Object read() {
        log.debug("获取读锁...");
        r.lock();
        try {
            log.debug("读取");
            Thread.sleep(1000);
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("释放读锁...");
            r.unlock();
        }
        return data;
    }
    public void write() {
        log.debug("获取写锁...");
        w.lock();
        try {
            log.debug("写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("释放写锁...");
            w.unlock();
        }
    }
}