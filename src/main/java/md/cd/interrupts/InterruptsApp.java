package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;
import md.cd.utils.Factorial;

import java.math.BigDecimal;
import java.time.LocalTime;

import static java.lang.Long.parseLong;
import static java.time.LocalTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
public final class InterruptsApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long allow = parseLong(args[0]);
        final long spin = parseLong(args[1]);
        new InterruptsApp().main(allow, spin);
    }

    public void main
            (
                    long allow,
                    long spin
            ) throws InterruptedException
    {
        worker(allow);
        spinner(spin);
        waiting();
    }

    private void worker(long allow) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("Let's factorize!");
                    for (long n = 100_000; n < 1_000_000; n++)
                    {
                        final BigDecimal f = Factorial.factorial(new BigDecimal(n));
                        log.info("Factorial of {} has {} digits", n, f.toPlainString().length());
                        if (Thread.interrupted())
                        {
                            log.info("We've been requested to interrupt");
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    log.error("We finished herculean task!");
                }, "factorizer");
        t.start();
        Thread.sleep(allow);
        t.interrupt();
        log.info("{} interrupted: {}, alive: {}", t.getName(), t.isInterrupted(), t.isAlive());
    }

    private void spinner(long spin) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("Spinning");
                    while (true)
                        if (Thread.interrupted())
                            return;
                },
                "spinner"
        );
        t.start();
        Thread.sleep(spin);
        log.info("Interrupting");
        while (t.isAlive())
            t.interrupt();
    }

    private void waiting() throws InterruptedException
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
