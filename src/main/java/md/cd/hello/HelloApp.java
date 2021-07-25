package md.cd.hello;

public final class HelloApp
{
    public static void main(String[] args)
    {
        new HelloApp().main();
    }

    public void main()
    {
        final Runnable runnable = new HelloRunnable();
        final Thread thread = new Thread(runnable);
        thread.start();
    }
}
