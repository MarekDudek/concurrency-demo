package md.cd.dining_philosophers.runnables;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Fork;
import md.cd.dining_philosophers.resources.Philosopher;

@Builder
@ToString
@Slf4j
public final class ResourceHierarchySolution implements Runnable
{
    @NonNull
    public final Philosopher philosopher;

    private long eaten;

    @Override
    public void run()
    {
        log.info("I'm between {} and {}", philosopher.left, philosopher.right);
        final Fork lower = philosopher.left.number < philosopher.right.number ? philosopher.left : philosopher.right;
        final Fork upper = philosopher.left.number < philosopher.right.number ? philosopher.right : philosopher.left;
        log.info("Lower is {}, upper is {}", lower, upper);
        while (true)
        {
            log.trace("Waiting for lower {}", lower);
            synchronized (lower)
            {
                log.trace("I picked up lower {}", lower);
                log.trace("Waiting for upper {}", upper);
                synchronized (upper)
                {
                    log.trace("I picked up upper {}", upper);
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
                log.trace("I put down upper {}", upper);
            }
            log.trace("I put down lower {}", lower);
        }
        log.info("Finished");
    }
}