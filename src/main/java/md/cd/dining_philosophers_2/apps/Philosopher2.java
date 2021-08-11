package md.cd.dining_philosophers_2.apps;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
class Philosopher2
{
    @NonNull
    public final String name;
    @NonNull
    public final Chopstick2 leftChopstick;
    @NonNull
    public final Chopstick2 rightChopstick;
    @NonNull
    public final Guard leftGuard;
    @NonNull
    public final Guard rightGuard;
}
