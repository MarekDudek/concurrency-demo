package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;
import md.cd.utils.Factorial;

import java.math.BigDecimal;

import static java.lang.Integer.parseInt;

@Slf4j
public final class InterruptsApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int sleep = parseInt(args[0]);
        final int wait = parseInt(args[1]);
        final int allow = parseInt(args[2]);
        new InterruptsApp().main(sleep, wait, allow);
    }

    public void main
            (
                    int sleep,
                    int wait,
                    int allow
            ) throws InterruptedException
    {
        sleeper(sleep, wait);
        worker(allow);
    }

    private void sleeper(int sleep, int wait) throws InterruptedException
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

    private void worker(int allow) throws InterruptedException
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
                            return;
                        }
                    }
                    log.error("We finished herculean task!");
                }, "factorizer");
        t.start();
        Thread.sleep(allow);
        t.interrupt();
        log.info("{} interrupted: {}", t.getName(), t.isInterrupted());
    }
}
