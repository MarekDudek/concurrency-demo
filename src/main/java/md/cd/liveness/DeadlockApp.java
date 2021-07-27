package md.cd.liveness;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

public final class DeadlockApp
{
    public static void main(String[] args) throws InterruptedException
    {
        new DeadlockApp().main();
    }

    public void main()
    {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        final Thread t1 = new Thread(
                () -> alphonse.bow(gaston),
                "alphonse"
        );

        final Thread t2 = new Thread(
                () -> gaston.bow(alphonse),
                "gaston"
        );
        t1.start();
        t2.start();
    }
}

@RequiredArgsConstructor
@Slf4j
final class Friend
{
    @NonNull
    public final String name;

    public synchronized void bow(final Friend friend)
    {
        log.info("{} bowed to me", friend.name);
        friend.bowBack(this);
    }

    private synchronized void bowBack(final Friend friend)
    {
        log.info("{} bowed back to me", friend.name);
    }
}