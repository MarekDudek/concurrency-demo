package md.cd.dining_philosophers;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@ToString
@Slf4j
public final class BlockingDiningPhilosopher implements Runnable
{
    @NonNull
    public final Philosopher philosopher;

    @Override
    public void run()
    {
        log.info("I'm between {} and {}", philosopher.left, philosopher.right);
        while (true)
        {
            log.info("Waiting for left {}", philosopher.left);
            synchronized (philosopher.left)
            {
                log.info("I picked up left {}", philosopher.left);
                log.info("Waiting for right {}", philosopher.right);
                synchronized (philosopher.right)
                {
                    log.info("I picked up right {}", philosopher.right);
                    try
                    {
                        Thread.sleep(0, 1);
                    }
                    catch (InterruptedException e)
                    {
                        log.info("I was interrupted, quitting");
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                log.info("I put down right {}", philosopher.right);
            }
            log.info("I put down left {}", philosopher.left);
        }
    }
}
