package md.cd.interference;

public class Counter
{
    private long c = 0;

    public synchronized void increment()
    {
        c++;
    }

    public synchronized void decrement()
    {
        c--;
    }

    public long value()
    {
        return c;
    }
}