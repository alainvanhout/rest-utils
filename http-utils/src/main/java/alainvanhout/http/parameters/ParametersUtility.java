package alainvanhout.http.parameters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParametersUtility {
    public static String toQueryString(final Parameters parameters) {
        return parameters.getMap().entrySet().stream()
                .map(ParametersUtility::valueListForKey)
                .collect(Collectors.joining("&"));
    }

    private static String valueListForKey(Map.Entry<String, List<String>> entry) {
        final List<String> values = entry.getValue();
        if (values.isEmpty()) {
            return entry.getKey();
        }
        return values.stream()
                .map(value -> entry.getKey() + "=" + value)
                .collect(Collectors.joining("&"));
    }
}
