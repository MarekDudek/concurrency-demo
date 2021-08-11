package md.cd.dining_philosophers_2.apps;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
class Chopstick2
{
    public int number;
    public boolean free;
}
