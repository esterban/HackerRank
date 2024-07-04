package interviewpreparationkit.random.threading;

import java.util.concurrent.atomic.AtomicBoolean;

public class RunnableAtomicBoolean implements Runnable {
    private final AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public AtomicBoolean getAtomicBoolean() {
        return atomicBoolean;
    }

    public void release() {
        atomicBoolean.set(false);
    }

    @Override
    public void run() {
        while (atomicBoolean.get()) {
        }

        System.out.println(getClass() + " has finished " + Thread.currentThread().getStackTrace());

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int index = 1; index < stackTraceElements.length; ++index) {
            System.out.println(stackTraceElements[index]);
        }

        System.out.println();
    }
}
