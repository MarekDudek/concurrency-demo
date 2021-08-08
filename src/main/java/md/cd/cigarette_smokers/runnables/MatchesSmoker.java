package md.cd.cigarette_smokers.runnables;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@ToString
@Slf4j
public final class MatchesSmoker implements Runnable
{
    @NonNull
    public final Object tobacco;
    @NonNull
    public final Object paper;

    @Override
    public void run()
    {
        log.trace("Started");
        while (true)
        {
            log.trace("Wating for tobacco");
            synchronized (tobacco)
            {
                log.trace("Wating for paper");
                synchronized (paper)
                {
                    try
                    {
                        Thread.sleep(0, 1);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                log.trace("Released paper");
            }
            log.trace("Released tobacco");
        }
        log.trace("Finished");
    }
}
