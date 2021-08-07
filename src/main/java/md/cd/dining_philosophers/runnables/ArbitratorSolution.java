package md.cd.dining_philosophers.runnables;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;
import md.cd.dining_philosophers.resources.Waiter;

@Builder
@ToString
@Slf4j
public final class ArbitratorSolution implements Runnable
{
    @NonNull
    public final Philosopher philosopher;
    @NonNull
    public final Waiter waiter;

    private long eaten;

    @Override
    public void run()
    {
        log.info("I'm between {} and {}", philosopher.left, philosopher.right);
        while (true)
        {
            log.trace("Waiting for permission from waiter");
            synchronized (waiter)
            {
                log.trace("Got permission from waiter");
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
                            eaten++;
                        }
                        catch (InterruptedException e)
                        {
                            log.info("I was interrupted, quitting");
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    log.trace("I put down right {}", philosopher.right);
                }
                log.trace("I put down left {}", philosopher.left);
            }
            log.trace("Finished");
        }
        log.trace("Disengaged from waiter");
    }
}
