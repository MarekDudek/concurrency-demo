package md.cd;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
final class MainTest
{
    @Test
    void test()
    {
        assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    void log()
    {
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warning");
        log.error("error");
    }
}
