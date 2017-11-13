package alainvanhout.http;

import alainvanhout.http.client.HttpExecutor;
import alainvanhout.json.JsonConverter;

/**
 * Provides a location where a default implementation of {@link JsonConverter} can be registered, to be used by
 * implementations of {@link HttpExecutor}.
 */
public class HttpDefaults {
    private static JsonConverter defaultJsonConverter = null;

    public static void setDefaultJsonConverter(final JsonConverter jsonConverter) {
        defaultJsonConverter = jsonConverter;
    }

    public static JsonConverter getDefaultJsonConverter() {
        return defaultJsonConverter;
    }
}
