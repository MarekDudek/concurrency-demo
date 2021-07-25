package md.cd;

import lombok.extern.slf4j.Slf4j;
import md.cd.hello.HelloApp;
import md.cd.interrupts.InterruptsApp;
import md.cd.sleep.SleepingApp;

@Slf4j
public final class Main
{
    public static void main(String[] args) throws Exception
    {
        log.info("Concurrency Demo");
        new HelloApp().main();
        new SleepingApp().main(5, 100);
        new InterruptsApp().main(10_000, 1_000, 5_000);
    }
}
