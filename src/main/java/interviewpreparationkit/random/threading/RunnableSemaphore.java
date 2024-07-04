package interviewpreparationkit.random.threading;

import java.util.concurrent.Semaphore;

public class RunnableSemaphore {
    private final Semaphore semaphore = new Semaphore(0);

    public void release(int permits) {
        semaphore.release(permits);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
