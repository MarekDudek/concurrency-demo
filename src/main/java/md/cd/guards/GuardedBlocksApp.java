package md.cd.guards;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Long.parseLong;

@Slf4j
public final class GuardedBlocksApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long sleep = parseLong(args[0]);
        new GuardedBlocksApp().main(sleep);
    }

    public void main(long sleep) throws InterruptedException
    {
        final WaitingForJoy r = new WaitingForJoy();
        final Thread t = new Thread(r, "waiter");
        t.start();
        Thread.sleep(sleep);
        r.notifyJoy();
        t.join();
    }
}

@RequiredArgsConstructor
@Slf4j
final class WaitingForJoy implements Runnable
{
    @NonNull
    private final AtomicBoolean joy = new AtomicBoolean();

    @Override
    public synchronized void run()
    {
        log.info("Waiting for joy");
        while (!joy.get())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                log.info("Interrupted");
            }
        }
        log.info("Joy has been achieved");
    }

    public synchronized void notifyJoy()
    {
        log.info("Notifying");
        joy.set(true);
        notify();
    }
}