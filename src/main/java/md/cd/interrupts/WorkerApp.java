package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

import static java.lang.Long.parseLong;
import static java.math.BigInteger.valueOf;
import static md.cd.utils.Factorial.factorial;

@Slf4j
public final class WorkerApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long allow = parseLong(args[0]);
        new WorkerApp().main(allow);
    }

    public void main(long allow) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("Let's factorize!");
                    for (long n = 100_000; n < 1_000_000; n++)
                    {
                        final BigInteger f = factorial(valueOf(n));
                        log.info("Factorial of {} has {} digits", n, f.toString().length());
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
        t.join();
        log.info("{} interrupted: {}, alive: {}", t.getName(), t.isInterrupted(), t.isAlive());
    }
}
