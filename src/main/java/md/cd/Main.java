package md.cd;

import lombok.extern.slf4j.Slf4j;
import md.cd.hello.HelloApp;

@Slf4j
public final class Main
{
    public static void main(String[] args)
    {
        log.info("Concurrency Demo");
        new HelloApp().main();
    }
}
