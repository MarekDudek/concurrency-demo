package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;
import md.cd.utils.Factorial;

import java.math.BigDecimal;

import static java.lang.Long.parseLong;

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
}
