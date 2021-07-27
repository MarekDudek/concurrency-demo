package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Long.parseLong;

@Slf4j
public final class SleeperApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long sleep = parseLong(args[0]);
        final long wait = parseLong(args[1]);
        new SleeperApp().main(sleep, wait);
    }

    public void main(long sleep, long wait) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("Started");
                    try
                    {
                        Thread.sleep(sleep);
                    }
                    catch (InterruptedException e)
                    {
                        log.info("We've been interrupted");
                        return;
                    }
                    log.error("Stopped");
                }, "sleeper");
        t.start();
        Thread.sleep(wait);
        t.interrupt();
        log.info("{} interrupted: {}", t.getName(), t.isInterrupted());
    }
}
