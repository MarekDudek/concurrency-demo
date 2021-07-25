package md.cd.utils;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;

public enum Factorial
{
    ;

    public static BigDecimal factorial(final BigDecimal number)
    {
        BigDecimal f = ONE;
        for (BigDecimal i = new BigDecimal(2); i.compareTo(number) < 1; i = i.add(ONE))
        {
            f = f.multiply(i);
        }
        return f;
    }
}
