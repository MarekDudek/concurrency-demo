package md.cd.dining_philosophers;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@ToString
@Slf4j
public final class Philosopher implements Runnable
{
    @NonNull
    public final String name;
    @NonNull
    public final Fork left;
    @NonNull
    public final Fork right;

    @Override
    public void run()
    {
        log.info("I'm between {} and {}", left, right);
        while (true)
        {
            log.info("Waiting for left {}", left);
            synchronized (left)
            {
                log.info("I picked up left {}", left);
                log.info("Waiting for right {}", right);
                synchronized (right)
                {
                    log.info("I picked up right {}", right);
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
                log.info("I put down right {}", right);
            }
            log.info("I put down left {}", left);
        }
    }
}
