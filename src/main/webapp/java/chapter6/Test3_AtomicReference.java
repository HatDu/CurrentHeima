package chapter6;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Test3_AtomicReference {
    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountCas(new BigDecimal("10000")));
    }
}

class DecimalAccountCas implements DecimalAccount{
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal balance){
        this.balance = new AtomicReference<>(balance);
    }
    @Override
    public void withDraw(BigDecimal amount) {
        while(true){
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if(balance.compareAndSet(prev, next))   break;
        }
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }
}

interface DecimalAccount{
    public void withDraw(BigDecimal amount);
    public BigDecimal getBalance();

    public static void demo(DecimalAccount account){
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withDraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}
