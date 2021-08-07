package md.cd.dining_philosophers.apps;

import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;
import md.cd.dining_philosophers.runnables.LimitedNumberOfDinersSolution;

import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Integer.parseInt;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;
import static md.cd.dining_philosophers.apps.DiningPhilosophersCommon.createPhilosophersWithForks;

@Slf4j
public final class LimitedNumberOfDinersSolutionApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int seconds = parseInt(args[0]);
        new LimitedNumberOfDinersSolutionApp().main(seconds);
    }

    public void main(final int seconds) throws InterruptedException
    {
        final int count = 5;
        final List<Philosopher> philosophers = createPhilosophersWithForks(count);
        final Semaphore semaphore = new Semaphore(count - 1);
        final List<LimitedNumberOfDinersSolution> runnables = philosophers.stream().map(
                philosopher -> LimitedNumberOfDinersSolution.builder().
                        philosopher(philosopher).
                        semaphore(semaphore).
                        build()
        ).collect(toList());
        final List<Thread> threads = runnables.stream().map(
                runnable -> new Thread(runnable, runnable.philosopher.name)
        ).collect(toList());

        for (final Thread thread : threads)
            thread.start();

        Thread.sleep(ofSeconds(seconds).toMillis());

        for (final Thread thread : threads)
            thread.interrupt();
        for (final Thread thread : threads)
            thread.join();

        for (final Runnable runnable : runnables)
            log.info("{}", runnable);
    }
}
