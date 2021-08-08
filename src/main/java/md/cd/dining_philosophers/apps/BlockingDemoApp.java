package md.cd.dining_philosophers.apps;

import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;
import md.cd.dining_philosophers.runnables.BlockingProblem;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static md.cd.dining_philosophers.apps.DiningPhilosophersCommon.createPhilosophersWithForks;
import static md.cd.dining_philosophers.apps.DiningPhilosophersCommon.run;

@Slf4j
public final class BlockingDemoApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int seconds = parseInt(args[0]);
        final Duration duration = ofSeconds(seconds);
        new BlockingDemoApp().main(duration, 5);
    }

    public Map<Philosopher, Long> main(final Duration duration, int count) throws InterruptedException
    {
        final List<Philosopher> philosophers = createPhilosophersWithForks(count);
        final List<BlockingProblem> runnables = philosophers.stream().map(
                philosopher ->
                        BlockingProblem.builder().
                                philosopher(philosopher).
                                build()
        ).collect(toList());

        final List<Thread> threads = runnables.stream().map(
                runnable -> new Thread(runnable, runnable.philosopher.name)
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
