package md.cd;

import lombok.extern.slf4j.Slf4j;
import md.cd.hello.HelloApp;
import md.cd.interference.CounterApp;
import md.cd.interrupts.SleeperApp;
import md.cd.interrupts.SpinnerApp;
import md.cd.interrupts.WaitingApp;
import md.cd.interrupts.WorkerApp;
import md.cd.sleep.SleepingApp;

@Slf4j
public final class Main
{
    public static void main(String[] args) throws Exception
    {
        log.info("Concurrency Demo");
        new HelloApp().main();
        new SleepingApp().main(5, 100);
        new SleeperApp().main(10_000, 1_000);
        new WorkerApp().main(4_000);
        new SpinnerApp().main(2_000);
        new WaitingApp().main();
        new CounterApp().main(100);
    }
}
