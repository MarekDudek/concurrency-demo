package md.cd.dining_philosophers;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.lang.Integer.parseInt;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;

@Slf4j
public class DiningPhilosophersApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int seconds = parseInt(args[0]);
        new DiningPhilosophersApp().main(seconds);
    }

    public void main(final int seconds) throws InterruptedException
    {
        final List<Philosopher> philosophers = createDiningRoom();
        final List<ResourceHierarchyPhilosopher> runnables = philosophers.stream().map(
                philosopher -> ResourceHierarchyPhilosopher.builder().
                        philosopher(philosopher).
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

    private static List<Philosopher> createDiningRoom()
    {
        final int count = 5;
        final List<Fork> forks = newArrayListWithCapacity(count);
        for (int i = 0; i < count; i++)
            forks.add(
                    Fork.builder().
                            number(i).
                            build()
            );
        final List<Philosopher> philosophers = newArrayListWithCapacity(count);
        for (int i = 0; i < count; i++)
            philosophers.add(
                    Philosopher.builder().
                            name("philosopher-" + i).
                            left(forks.get(i)).
                            right(forks.get((i + 1) % count)).
                            build()
            );
        return philosophers;
    }
}
