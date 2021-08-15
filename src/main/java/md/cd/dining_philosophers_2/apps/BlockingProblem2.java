package md.cd.dining_philosophers_2.apps;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Builder
@Slf4j
class BlockingProblem2 implements Runnable
{
    @NonNull
    public final Philosopher2 philosopher;
    @NonNull
    public final Waiter waiter;
    public long worked;

    @Override
    public void run()
    {
        log.trace("Started");
        main:
        while (true)
        {
            if (philosopher.left.free)
            {
                log.trace("Taking uncontested left {}", philosopher.left);
                philosopher.left.free = false;
            }
            else
            {
                log.trace("Synchronizing on left {}", philosopher.left);
                synchronized (philosopher.left)
                {
                    while (isFalse(philosopher.left.free))
                    {
                        try
                        {
                            log.trace("Waiting for left {}", philosopher.left);
                            philosopher.left.wait();
                        }
                        catch (InterruptedException e)
                        {
                            Thread.currentThread().interrupt();
                            log.trace("Interrupted waiting for left {}", philosopher.left);
                            break main;
                        }
                    }
                    log.trace("Taking contested left {}", philosopher.left);
                    philosopher.left.free = false;
                }
            }
            if (philosopher.right.free)
            {
                log.trace("Taking uncontested right {}", philosopher.right);
                philosopher.right.free = false;
            }
            else
            {
                log.trace("Synchronizing on right {}", philosopher.right);
                synchronized (philosopher.right)
                {
                    while (isFalse(philosopher.right.free))
                    {
                        try
                        {
                            log.trace("Waiting for right {}", philosopher.right);
                            philosopher.right.wait();
                        }
                        catch (InterruptedException e)
                        {
                            Thread.currentThread().interrupt();
                            log.trace("Interrupted waiting for right {}", philosopher.right);
                            break main;
                        }
                    }
                    log.trace("Taking contested right {}", philosopher.right);
                    philosopher.right.free = false;
                }
            }
            try
            {
                log.trace("Working");
                Thread.sleep(0, 1);
                log.trace("Worked");
                worked++;
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                log.trace("Interrupted working");
                break;
            }
            log.trace("Dropping right {}", philosopher.right);
            philosopher.right.free = true;
            log.trace("Dropping left {}", philosopher.left);
            philosopher.left.free = true;
        }
        log.trace("Finished");
    }
}
