package md.cd.dining_philosophers_2.apps;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

@Slf4j
public final class BlockingWaitNotifyDemoApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final int count = 5;
        final List<Chopstick2> chopsticks =
                rangeClosed(1, count).mapToObj(i ->
                        Chopstick2.builder().
                                number(i).
                                free(true).
                                build()
                ).collect(toList());
        final List<Guard> guards =
                rangeClosed(1, count).mapToObj(i ->
                        Guard.builder().
                                number(i).
                                build()
                ).collect(toList());
        final List<Philosopher2> ps =
                rangeClosed(1, count).mapToObj(i ->
                        Philosopher2.builder().
                                name("philosopher-" + i).
                                leftChopstick(chopsticks.get((i - 1) % count)).
                                rightChopstick(chopsticks.get(1 % count)).
                                leftGuard(new Guard(i)).
                                rightGuard(new Guard(i + count)).
                                build()
                ).collect(toList());
        ps.forEach(p -> log.info("{}", p));
        final List<BlockingProblem2> runnables =
                ps.stream().map(p ->
                        BlockingProblem2.builder().
                                philosopher(p).
                                waiter(
                                        Waiter.builder().
                                                build()
                                ).
                                build()
                ).collect(toList());
        final List<Thread> threads =
                runnables.stream().map(r ->
                        new Thread(r, r.philosopher.name)
                ).collect(toList());
        threads.forEach(Thread::start);
        Thread.sleep(10_000);
        threads.forEach(Thread::interrupt);
        for (final Thread t : threads)
            t.join();
        runnables.forEach(r -> log.trace("{}", r.worked));
    }
}
