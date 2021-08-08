package md.cd.cigarette_smokers.apps;

import com.google.common.collect.ImmutableList;
import md.cd.cigarette_smokers.runnables.MatchesSmoker;
import md.cd.cigarette_smokers.runnables.PaperSmoker;
import md.cd.cigarette_smokers.runnables.TobaccoSmoker;

import java.time.Duration;
import java.util.List;

public final class BlockingProblemApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final Duration duration = Duration.parse(args[0]);
        new BlockingProblemApp().main(duration);
    }

    public void main(final Duration duration) throws InterruptedException
    {
        final Object tobacco = new Object();
        final Object paper = new Object();
        final Object matches = new Object();

        final Runnable tobaccoSmoker =
                TobaccoSmoker.builder().
                        paper(paper).
                        matches(matches).
                        build();
        final Runnable paperSmoker =
                PaperSmoker.builder().
                        tobacco(tobacco).
                        matches(matches).
                        build();
        final Runnable matchesSmoker =
                MatchesSmoker.builder().
                        tobacco(tobacco).
                        paper(paper).
                        build();

        final Thread thread1 = new Thread(tobaccoSmoker, "smoker-tobacco");
        final Thread thread2 = new Thread(paperSmoker, "smoker-paper");
        final Thread thread3 = new Thread(matchesSmoker, "smoker-matches");

        final List<Thread> threads = ImmutableList.of(thread1, thread2, thread3);

        for (final Thread t : threads)
            t.start();

        Thread.sleep(duration.toMillis());

        for (final Thread t : threads)
            t.interrupt();

        for (final Thread t : threads)
            t.join();
    }
}
