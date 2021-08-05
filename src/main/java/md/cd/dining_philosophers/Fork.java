package md.cd.dining_philosophers;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public final class Fork
{
    @NonNull
    public final String name;
}
