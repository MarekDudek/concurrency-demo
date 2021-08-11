package md.cd.dining_philosophers_2.apps;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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
        while (true)
        {
            synchronized (waiter)
            {
                log.trace("Waiting for right {}", philosopher.rightGuard);
                synchronized (philosopher.rightGuard)
                {
                    log.trace("Got right {}, waiting for right {}", philosopher.rightGuard, philosopher.rightChopstick);
                    while (!philosopher.rightChopstick.free)
                        try
                        {
                            philosopher.rightGuard.wait();
                        }
                        catch (InterruptedException e)
                        {
                            log.trace("Interrupted waiting for right {}", philosopher.rightGuard);
                            return;
                        }
                    log.trace("Taking right {}", philosopher.rightChopstick);
                    philosopher.rightChopstick.free = false;
                    log.trace("Waiting for left {}", philosopher.leftGuard);
                    synchronized (philosopher.leftGuard)
                    {
                        log.trace("Got left {}, waiting for left {}", philosopher.leftGuard, philosopher.leftChopstick);
                        while (!philosopher.leftChopstick.free)
                            try
                            {
                                philosopher.leftGuard.wait();
                            }
                            catch (InterruptedException e)
                            {
                                log.trace("Interrupted waiting for left {}", philosopher.leftGuard);
                                return;
                            }
                        log.trace("Taking left {}", philosopher.leftChopstick);
                        philosopher.leftChopstick.free = false;
                        try
                        {
                            log.trace("About to work for a while");
                            worked++;
                            Thread.sleep(0, 1);
                        }
                        catch (InterruptedException e)
                        {
                            break;
                        }
                        log.trace("Dropping left {}", philosopher.leftChopstick);
                        philosopher.leftChopstick.free = true;
                        philosopher.leftGuard.notifyAll();
                    }
                    log.trace("Dropping right {}", philosopher.rightChopstick);
                    philosopher.rightChopstick.free = true;
                    philosopher.rightGuard.notifyAll();
                }
            }
        }
        log.trace("Finished");
    }
}
