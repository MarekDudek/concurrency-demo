package md.cd.cigarette_smokers.runnables;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@ToString
@Slf4j
public final class TobaccoSmoker implements Runnable
{
    @NonNull
    public final Object paper;
    @NonNull
    public final Object matches;

    @Override
    public void run()
    {
        log.trace("Started");
        while (true)
        {
            log.trace("Wating for paper");
            synchronized (paper)
            {
                log.trace("Wating for matches");
                synchronized (matches)
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
                log.trace("Released matches");
            }
            log.trace("Released paper");
        }
        log.trace("Finished");
    }
}
