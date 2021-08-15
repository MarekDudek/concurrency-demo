package md.cd.dining_philosophers_2.apps;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class Philosopher2
{
    @NonNull
    public String name;
    @NonNull
    public Chopstick2 left;
    @NonNull
    public Chopstick2 right;
}
