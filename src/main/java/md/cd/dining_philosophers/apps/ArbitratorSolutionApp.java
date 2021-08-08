package md.cd.dining_philosophers.apps;

import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;
import md.cd.dining_philosophers.resources.Waiter;
import md.cd.dining_philosophers.runnables.ArbitratorSolution;

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
public final class ArbitratorSolutionApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int seconds = parseInt(args[0]);
        final Duration duration = ofSeconds(seconds);
        new ArbitratorSolutionApp().main(duration, 5);
    }

    public Map<Philosopher, Long> main(final Duration duration, final int count) throws InterruptedException
    {
        final List<Philosopher> philosophers = createPhilosophersWithChopsticks(count);
        final Waiter waiter = new Waiter();
        final List<ArbitratorSolution> runnables = philosophers.stream().map(
                philosopher ->
                        ArbitratorSolution.builder().
                                philosopher(philosopher).
                                waiter(waiter).
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
