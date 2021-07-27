package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

import static java.lang.Long.parseLong;
import static java.time.LocalTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
public final class WaitingApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long sleep = parseLong(args[0]);
        final long patience = parseLong(args[1]);
        final long repetitions = parseLong(args[2]);
        new WaitingApp().main(sleep, patience, repetitions);
    }

    public void main(long sleep, long patience, long repetitions) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("starting");
                    for (int i = 0; i < repetitions; i++)
                    {
                        try
                        {
                            log.info("sleeping");
                            Thread.sleep(sleep);
                        }
                        catch (InterruptedException e)
                        {
                            log.info("interrupted");
                        }
                    }
                    log.info("finishing");
                },
                "waitee"
        );
        t.start();
        LocalTime started = now();
        while (t.isAlive())
        {
            log.info("waiting to join");
            t.join(sleep);
            if (now().isAfter(started.plus(patience, MILLIS)))
            {
                log.info("patience is over");
                t.interrupt();
                t.join();
            }
        }
    }
}
