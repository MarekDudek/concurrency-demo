package md.cd.interrupts;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Long.parseLong;

@Slf4j
public final class SpinnerApp
{
    public static void main(String[] args) throws InterruptedException
    {
        final long spin = parseLong(args[0]);
        new SpinnerApp().main(spin);
    }

    public void main(long spin) throws InterruptedException
    {
        final Thread t = new Thread(
                () -> {
                    log.info("Spinning");
                    while (true)
                        if (Thread.interrupted())
                            return;
                },
                "spinner"
        );
        t.start();
        Thread.sleep(spin);
        log.info("Interrupting");
        t.interrupt();
        t.join();
    }
}
