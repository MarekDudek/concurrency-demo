package md.cd.sleeping;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalTime;

import static java.util.Objects.isNull;

@Slf4j
public final class SleepingApp
{

    private static final int SLEEP = 100;

    public static void main(String[] args) throws Exception
    {
        new SleepingApp().main();
    }

    public void main() throws InterruptedException
    {
        LocalTime then = null;
        for (int i = 0; i < 5; i++)
        {
            final LocalTime now = LocalTime.now();
            if (isNull(then))
                log.info("{} - started", now);
            else
            {
                final Duration between = Duration.between(now, then);
                log.info("{} - after {} millis elapsed {}", now, SLEEP, between);
            }
            then = now;
            Thread.sleep(SLEEP);
        }
    }
}
