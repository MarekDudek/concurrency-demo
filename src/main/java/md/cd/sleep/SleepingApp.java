package md.cd.sleep;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalTime;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

@Slf4j
public final class SleepingApp
{
    public static void main(String[] args) throws Exception
    {
        final int repetitions = parseInt(args[0]);
        final int sleep = parseInt(args[1]);
        new SleepingApp().main(repetitions, sleep);
    }

    public void main
            (
                    final int repetitions,
                    final int sleep
            ) throws InterruptedException
    {
        LocalTime then = null;
        for (int i = 0; i < repetitions; i++)
        {
            final LocalTime now = LocalTime.now();
            if (isNull(then))
                log.info("{} - started", now);
            else
            {
                final Duration between = Duration.between(now, then);
                log.info("{} - after {} millis elapsed {} - {}", now, sleep, between, i);
            }
            then = now;
            Thread.sleep(sleep);
        }
    }
}
