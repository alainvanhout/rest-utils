package alainvanhout.http.parameters;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility that provides convenience method for when working with {@link Parameters} instances.
 */
public class ParametersUtility {

    /**
     * Builds an url query parameter string based on the provided {@link Parameters} instance.
     *
     * @param parameters A {@link Parameters} instance
     * @return An url query parameter string
     */
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

    /**
     * Builds a {@link Parameters} instance based on the provided url query parameter string.
     *
     * @param queryString An url query parameter string
     * @return A {@link Parameters} instance
     */
    public static Parameters fromQueryString(final String queryString) {
        final Parameters parameters = new Parameters();

        final String[] pairs = StringUtils.split(queryString, "&");
        for (String pair : pairs) {
            if (pair.contains("=")) {
                parameters.add(StringUtils.substringBefore(pair, "="), StringUtils.substringAfter(pair, "="));
            } else {
                parameters.add(pair);
            }
        }

        return parameters;
    }
}
