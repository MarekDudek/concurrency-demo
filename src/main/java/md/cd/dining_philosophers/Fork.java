package md.cd.dining_philosophers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
public final class Fork
{
    @NonNull
    public final String name;
}
