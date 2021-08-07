package md.cd.dining_philosophers.resources;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Philosopher
{
    @NonNull
    public String name;
    @NonNull
    public Fork left;
    @NonNull
    public Fork right;
}
