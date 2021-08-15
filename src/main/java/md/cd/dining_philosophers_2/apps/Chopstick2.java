package md.cd.dining_philosophers_2.apps;

import lombok.Builder;
import lombok.ToString;


@Builder
@ToString
final class Chopstick2
{
    public final int number;
    public volatile boolean free;
}
