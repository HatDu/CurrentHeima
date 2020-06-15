package chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "Test21_ReIn")
public class Test21_ReIn {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        try {
            lock.lock();
            log.debug("execute main");
        }finally {
            lock.unlock();
        }
        f1();
        f2();
    }

    public static void f1() {
        try {
            lock.lock();
            log.debug("execute f1()");
        }finally {
            lock.unlock();
        }
    }

    public static void f2() {
        try {
            lock.lock();
            log.debug("execute f2()");
        }finally {
            lock.unlock();
        }
    }
}
