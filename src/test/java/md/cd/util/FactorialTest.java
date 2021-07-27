package md.cd.util;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static md.cd.utils.Factorial.factorial;
import static org.assertj.core.api.Assertions.assertThat;

final class FactorialTest
{
    @Test
    void test()
    {
        final BigInteger f = factorial(valueOf(5));
        assertThat(f).isEqualTo(valueOf(120));
    }
}
