package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

import static java.time.LocalTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
public final class WaitingApp
{
    public static void main(String[] args) throws InterruptedException
    {
        new WaitingApp().main();
    }

    public void main
            (
            ) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("starting");
                    for (int i = 0; i < 10; i++)
                    {
                        try
                        {
                            log.info("sleeping");
                            Thread.sleep(100);
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
            t.join(100);
            if (now().isAfter(started.plus(500, MILLIS)))
            {
                log.info("patience is over");
                t.interrupt();
                t.join();
            }
        }
    }
}
