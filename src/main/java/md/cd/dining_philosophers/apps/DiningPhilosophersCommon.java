package md.cd.dining_philosophers.apps;

import md.cd.dining_philosophers.resources.Chopstick;
import md.cd.dining_philosophers.resources.Philosopher;

import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public enum DiningPhilosophersCommon
{
    ;

    static List<Philosopher> createPhilosophersWithChopsticks(final int count)
    {
        final List<Chopstick> cs =
                rangeClosed(0, count).mapToObj(
                        Chopstick::new
                ).collect(toList());
        return rangeClosed(0, count).mapToObj(i ->
                Philosopher.builder().
                        name("philosopher-" + i).
                        left(
                                cs.get(i)
                        ).
                        right(
                                cs.get((i + 1) % count)
                        ).
                        build()
        ).collect(toList());
    }

    static void run(final List<Thread> threads, final Duration duration) throws InterruptedException
    {
        for (final Thread thread : threads)
            thread.start();
        Thread.sleep(duration.toMillis());
        for (final Thread thread : threads)
            thread.interrupt();
        for (final Thread thread : threads)
            thread.join();
    }
}
