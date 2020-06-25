package Chapter8_JUC;

import lombok.extern.slf4j.Slf4j;

import javax.activation.MailcapCommandMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test1_AQS {
    public static void main(String[] args) {
        ReentrantLock l = new ReentrantLock();
        myLock lock = new myLock();
        new Thread(()->{
            lock.lock();
            log.debug("加锁成功");
            // lock.lock(); 不可重入锁
            try {
                log.debug("locking...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking...");
                lock.unlock();

            }
        }, "t1").start();
        new Thread(()->{
            lock.lock();
            try {
                log.debug("locking...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                log.debug("unlocking...");
            }
        }, "t2").start();
    }
}

class myLock implements Lock{
    // 独占锁
    class MySync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)){
                // 成功，加锁，将owner设置为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            // 状态设空， state是volatile的，具有写屏障，保证在此之前的屏障可见
            setState(0);
            return true;
        }

        @Override //是否持有独占锁
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCOndition(){
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();

    @Override // 加锁（不成功进入等待队列）
    public void lock() {
        sync.acquire(1);
    }

    @Override // 加锁，可打断
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override // 尝试加锁（1次）
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override // 带超时的加锁
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override // 解锁
    public void unlock() {
        sync.release(1);
    }

    @Override // 创建条件变量
    public Condition newCondition() {
        return sync.newCOndition();
    }
}

