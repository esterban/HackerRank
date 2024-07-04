package interviewpreparationkit.random;

import interviewpreparationkit.random.threading.RunnableAtomicBoolean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SpinLock {
    public static void main(String[] args) throws InterruptedException {
        List<RunnableAtomicBoolean> runnableAtomicBooleanList = new ArrayList<>();
        List<ThreadPoolExecutor> threadPoolExecutorList = new ArrayList<>();

        final int runnableCount = 10;

        for (int counter = 1; counter < runnableCount; ++counter) {
            runnableAtomicBooleanList.add(new RunnableAtomicBoolean());
            threadPoolExecutorList.add(new ThreadPoolExecutor(2, 16, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100)));
        }

        SpinLock spinLock = new SpinLock();
        spinLock.execute(runnableAtomicBooleanList, threadPoolExecutorList);
        spinLock.wait(runnableAtomicBooleanList, (long) 15e3);
        spinLock.terminate(threadPoolExecutorList);
    }

    private void execute(List<RunnableAtomicBoolean> runnableAtomicBooleanList, List<ThreadPoolExecutor> threadPoolExecutorList) {
        for (int index = 0; index < runnableAtomicBooleanList.size(); ++index) {
            threadPoolExecutorList.get(index).execute(runnableAtomicBooleanList.get(index));
        }
    }

    private void wait(List<RunnableAtomicBoolean> runnableAtomicBooleanList, long waitMillis) throws InterruptedException {
        for (RunnableAtomicBoolean runnableAtomicBoolean : runnableAtomicBooleanList) {
            Thread.sleep(waitMillis);

            runnableAtomicBoolean.release();
        }
    }

    private void terminate(List<ThreadPoolExecutor> threadPoolExecutorList) {
        for (ThreadPoolExecutor threadPoolExecutor : threadPoolExecutorList) {
            threadPoolExecutor.shutdown();
        }
    }
}
