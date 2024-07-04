package interviewpreparationkit.random;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.yield;

public class SpinLockScratchPad {
    private final AtomicBoolean spinAtomicBoolean = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        SpinLockScratchPad spinlock = new SpinLockScratchPad();
//        spinlock.spin();

        Runnable runnableAtomic = new Runnable() {
            @Override
            public void run() {
                while (spinlock.spinAtomicBoolean.get()) {
                }

                System.out.println("Runnable finished");
            }
        };

        AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        Runnable runnableAtomic2 = new Runnable() {
            @Override
            public void run() {
                while (atomicBoolean.get()) {
                }

                System.out.println("RunnableAtomic2 finished");
            }
        };


        Semaphore semaphore = new Semaphore(0);

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Runnable2 finished");
            }
        };


//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 100, TimeUnit.DAYS, new ArrayBlockingQueue<>(100));
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 8, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        ThreadPoolExecutor threadPoolExecutorAtomic = new ThreadPoolExecutor(0, 8, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

        threadPoolExecutor.execute(runnableAtomic);
        threadPoolExecutor.execute(runnableAtomic);
        threadPoolExecutor.execute(runnable2);
        threadPoolExecutor.execute(runnable2);

        threadPoolExecutorAtomic.execute(runnableAtomic2);
        threadPoolExecutorAtomic.execute(runnableAtomic2);

        Thread.sleep((long) 3e4);
        spinlock.spinAtomicBoolean.set(false);

        Thread.sleep((long) 1e4);
        atomicBoolean.set(false);

        Thread.sleep((long) 1e4);
        semaphore.release(2);

        Thread.sleep((long) 1e4);

        threadPoolExecutor.purge();
    }


    private void spin() {
        yield();
    }
}
