package md.cd.utils;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public enum Factorial
{
    ;

    public static BigInteger factorial(final BigInteger number)
    {
        BigInteger f = ONE;
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(number) < 1; i = i.add(ONE))
        {
            f = f.multiply(i);
        }
        return f;
    }
}
