package md.cd.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static md.cd.utils.Factorial.factorial;
import static org.assertj.core.api.Assertions.assertThat;

final class FactorialTest
{
    @Test
    void test()
    {
        final BigDecimal f = factorial(new BigDecimal(5));
        assertThat(f).isEqualTo(new BigDecimal(120));
    }
}
