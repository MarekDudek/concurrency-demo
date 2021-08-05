package md.cd.dining_philosophers;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@ToString
@Slf4j
public final class Philosopher
{
    @NonNull
    public final String name;
    @NonNull
    public final Fork left;
    @NonNull
    public final Fork right;
}
