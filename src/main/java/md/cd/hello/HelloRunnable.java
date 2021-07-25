package md.cd.hello;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HelloRunnable implements Runnable
{
    @Override
    public void run()
    {
        log.info("Hello from a thread!");
    }
}
