package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;
import md.cd.utils.Factorial;

import java.math.BigDecimal;

import static java.lang.Long.parseLong;

@Slf4j
public final class InterruptsApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long sleep = parseLong(args[0]);
        final long wait = parseLong(args[1]);
        final long allow = parseLong(args[2]);
        final long spin = parseLong(args[3]);
        new InterruptsApp().main(sleep, wait, allow, spin);
    }

    public void main
            (
                    long sleep,
                    long wait,
                    long allow,
                    long spin
            ) throws InterruptedException
    {
        sleeper(sleep, wait);
        worker(allow);
        spinner(spin);
    }

    private void sleeper(long sleep, long wait) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("Started");
                    try
                    {
                        Thread.sleep(sleep);
                    }
                    catch (InterruptedException e)
                    {
                        log.info("We've been interrupted");
                        return;
                    }
                    log.error("Stopped");
                }, "sleeper");
        t.start();
        Thread.sleep(wait);
        t.interrupt();
        log.info("{} interrupted: {}", t.getName(), t.isInterrupted());
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
}
