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
        while (true)
        {
            try
            {
                semaphore.acquire();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                break;
            }
            synchronized (philosopher.left)
            {
                synchronized (philosopher.right)
                {
                    try
                    {
                        Thread.sleep(0, 1);
                        worked++;
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            semaphore.release();
        }
    }
}
