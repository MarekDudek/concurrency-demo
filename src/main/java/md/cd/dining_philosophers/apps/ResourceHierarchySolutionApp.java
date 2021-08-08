package md.cd.dining_philosophers.apps;

import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;
import md.cd.dining_philosophers.runnables.ResourceHierarchySolution;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static md.cd.dining_philosophers.apps.DiningPhilosophersCommon.createPhilosophersWithChopsticks;
import static md.cd.dining_philosophers.apps.DiningPhilosophersCommon.run;

@Slf4j
public final class ResourceHierarchySolutionApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int seconds = parseInt(args[0]);
        final Duration duration = ofSeconds(seconds);
        new ResourceHierarchySolutionApp().main(duration, 5);
    }

    public Map<Philosopher, Long> main(final Duration duration, final int count) throws InterruptedException
    {
        final List<Philosopher> philosophers = createPhilosophersWithChopsticks(count);
        final List<ResourceHierarchySolution> runnables = philosophers.stream().map(
                philosopher ->
                        ResourceHierarchySolution.builder().
                                philosopher(philosopher).
                                build()
        ).collect(toList());

        final List<Thread> threads = runnables.stream().map(
                runnable ->
                        new Thread(runnable, runnable.philosopher.name)
        ).collect(toList());

        run(threads, duration);

        return runnables.stream().collect(
                toMap(
                        runnable -> runnable.philosopher,
                        runnable -> runnable.worked
                )
        );
    }
}
