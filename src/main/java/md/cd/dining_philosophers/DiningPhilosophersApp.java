package md.cd.dining_philosophers;

import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.util.stream.Collectors.toList;

public class DiningPhilosophersApp
{
    public static void main(String[] args) throws InterruptedException
    {
        new DiningPhilosophersApp().main();
    }

    public void main() throws InterruptedException
    {
        final int count = 5;
        final List<Fork> forks = newArrayListWithCapacity(count);
        for (int i = 0; i < 5; i++)
            forks.add(new Fork("fork-" + i));
        final List<Philosopher> philosophers = newArrayListWithCapacity(count);
        for (int i = 0; i < count; i++)
            philosophers.add(new Philosopher("philosopher-" + i, forks.get(i), forks.get((i + 1) % count)));
        final List<Thread> threads = philosophers.stream().map(
                philosopher -> new Thread(philosopher, philosopher.name)
        ).collect(toList());
        for (final Thread thread : threads)
            thread.start();
        Thread.sleep(1_000);
        for (final Thread thread : threads)
            thread.interrupt();
        for (final Thread thread : threads)
            thread.join();
    }
}
