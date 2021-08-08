package md.cd.dining_philosophers.runnables;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;

import java.util.concurrent.Semaphore;

@Builder
@ToString
@Slf4j
public final class LimitedNumberOfDinersSolution implements Runnable
{
    @NonNull
    public final Philosopher philosopher;
    @NonNull
    public final Semaphore semaphore;

    public long worked;

    @Override
    public void run()
    {
        log.trace("I'm between {} and {}", philosopher.left, philosopher.right);
        while (true)
        {
            try
            {
                log.trace("Acquiring {}", semaphore);
                semaphore.acquire();
                log.trace("Acquired {}", semaphore);
            }
            catch (InterruptedException e)
            {
                log.trace("I was interrupted during acquiring: {}", e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
            log.trace("Waiting for left {}", philosopher.left);
            synchronized (philosopher.left)
            {
                log.trace("I picked up left {}", philosopher.left);
                log.trace("Waiting for right {}", philosopher.right);
                synchronized (philosopher.right)
                {
                    log.trace("I picked up right {}", philosopher.right);
                    try
                    {
                        Thread.sleep(0, 1);
                        worked++;
                    }
                    catch (InterruptedException e)
                    {
                        log.trace("I was interrupted during sleeping: {}", e.getMessage());
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                log.trace("I put down right {}", philosopher.right);
            }
            log.trace("I put down left {}", philosopher.left);
            log.trace("Releasing {}", semaphore);
            semaphore.release();
            log.trace("Released {}", semaphore);
        }
        log.trace("Finished");
    }
}
