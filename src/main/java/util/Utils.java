package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Utils {

    public static <T> Collection<T> emptyIfNull(final Collection<T> collection) {
        return Objects.isNull(collection) ? new ArrayList<>() : collection;
    }
}
