package md.cd.dining_philosophers.apps;

import com.github.freva.asciitable.AsciiTable;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import md.cd.dining_philosophers.resources.Philosopher;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.time.Duration.ofSeconds;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Slf4j
public final class ComparisionApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int seconds = parseInt(args[0]);
        final Duration duration = ofSeconds(seconds);
        final int count = parseInt(args[1]);

        new ComparisionApp().main(duration, count);
    }

    public void main(final Duration duration, final int count) throws InterruptedException
    {
        final Map<Philosopher, Long> result1 = new ResourceHierarchySolutionApp().main(duration, count);
        final Map<Philosopher, Long> result2 = new ArbitratorSolutionApp().main(duration, count);
        final Map<Philosopher, Long> result3 = new LimitedNumberOfDinersSolutionApp().main(duration, count);

        final Map<String, Map<Philosopher, Long>> results = ImmutableMap.of(
                "resource hierarchy", result1,
                "arbitrator", result2,
                "limited number of diners", result3
        );

        final List<String> names =
                results.values().stream().map(
                        Map::keySet
                ).flatMap(
                        Collection::stream
                ).distinct().sorted(
                        comparing(p1 -> p1.name)
                ).collect(toList()).stream().map(
                        p -> p.name
                ).collect(toList());
        names.add(0, "solution\\philosopher");
        final String[] headers = names.toArray(new String[]{});

        final String[][] data = results.entrySet().stream().map(
                entry -> {
                    final String solution = entry.getKey();
                    final Map<Philosopher, Long> map = entry.getValue();
                    final List<String> works = map.values().stream().map(work -> Long.toString(work)).collect(toList());
                    works.add(0, solution);
                    return works.toArray(new String[]{});
                }).collect(toList()).toArray(new String[][]{});

        out.println(AsciiTable.getTable(headers, data));
    }
}
