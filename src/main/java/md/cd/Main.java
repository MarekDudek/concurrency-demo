package md.cd;

import lombok.extern.slf4j.Slf4j;
import md.cd.hello.HelloApp;
import md.cd.sleeping.SleepingApp;

@Slf4j
public final class Main
{
    public static void main(String[] args) throws Exception
    {
        log.info("Concurrency Demo");
        new HelloApp().main();
        new SleepingApp().main(5, 100);
    }
}
