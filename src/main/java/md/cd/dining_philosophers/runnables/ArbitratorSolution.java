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

    public long worked;

    @Override
    public void run()
    {
        while (true)
        {
            synchronized (waiter)
            {
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
            }
        }
    }
}
