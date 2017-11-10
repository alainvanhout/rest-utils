package alainvanhout.http.parameters;

import org.apache.commons.lang3.StringUtils;

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

    public static Parameters fromQueryString(final String queryString) {
        final Parameters parameters = new Parameters();

        final String[] pairs = StringUtils.split(queryString, "&");
        for (String pair : pairs) {
            if (pair.contains("=")){
                parameters.add(StringUtils.substringBefore(pair, "="), StringUtils.substringAfter(pair, "="));
            } else {
                parameters.add(pair);
            }
        }

        return parameters;
    }
}
