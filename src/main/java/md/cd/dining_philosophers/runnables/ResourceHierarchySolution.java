package md.cd.dining_philosophers.runnables;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Chopstick;
import md.cd.dining_philosophers.resources.Philosopher;

@Builder
@ToString
@Slf4j
public final class ResourceHierarchySolution implements Runnable
{
    @NonNull
    public final Philosopher philosopher;

    public long worked;

    @Override
    public void run()
    {
        final Chopstick lower = philosopher.left.number < philosopher.right.number ? philosopher.left : philosopher.right;
        final Chopstick upper = philosopher.left.number < philosopher.right.number ? philosopher.right : philosopher.left;
        while (true)
        {
            synchronized (lower)
            {
                synchronized (upper)
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
