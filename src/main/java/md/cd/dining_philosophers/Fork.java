package md.cd.dining_philosophers;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
public final class Fork
{
    @NonNull
    public final String name;
}
