package md.cd.interference;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Long.parseLong;

@Slf4j
public final class CounterApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long sleep = parseLong(args[0]);
        new CounterApp().main(sleep);
    }

    public void main(long sleep) throws InterruptedException
    {
        final Counter counter = new Counter();

        final AtomicLong increments = new AtomicLong();
        final Thread incrementer = new Thread(
                () -> {
                    long i = 0;
                    while (!Thread.interrupted())
                    {
                        counter.increment();
                        i += 1;
                    }
                    log.info("{} increments", i);
                    increments.set(i);
                },
                "incrementer"
        );

        final AtomicLong decrements = new AtomicLong();
        final Thread decrementer = new Thread(
                () -> {
                    long i = 0;
                    while (!Thread.interrupted())
                    {
                        counter.decrement();
                        i += 1;
                    }
                    log.info("{} decrements", i);
                    decrements.set(i);
                },
                "decrementer"
        );

        incrementer.start();
        decrementer.start();
        Thread.sleep(sleep);

        incrementer.interrupt();
        decrementer.interrupt();
        incrementer.join();
        decrementer.join();

        final long expected = increments.get() - decrements.get();
        final long actual = counter.value();
        log.info("actual {}, expected {}", actual, expected);
        if (expected - actual != 0)
            log.error("do not match");
    }
}
