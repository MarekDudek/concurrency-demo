package md.cd.dining_philosophers.apps;

import md.cd.dining_philosophers.resources.Fork;
import md.cd.dining_philosophers.resources.Philosopher;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public enum DiningPhilosophersCommon
{
    ;

    static List<Philosopher> createPhilosophersWithForks(final int count)
    {
        final List<Fork> fs =
                rangeClosed(0, count).mapToObj(
                        Fork::new
                ).collect(toList());
        return rangeClosed(0, count).mapToObj(i ->
                Philosopher.builder().
                        name("philosopher-" + i).
                        left(
                                fs.get(i)
                        ).
                        right(
                                fs.get((i + 1) % count)
                        ).
                        build()
        ).collect(toList());
    }
}