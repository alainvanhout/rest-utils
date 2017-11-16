package alainvanhout.json;

/**
 * Provides a location where a default implementation of {@link JsonConverter} can be registered.
 */
public class JsonDefaults {
    private static JsonConverter defaultJsonConverter = null;

    public static void setDefaultJsonConverter(final JsonConverter jsonConverter) {
        defaultJsonConverter = jsonConverter;
    }

    public static JsonConverter getDefaultJsonConverter() {
        return defaultJsonConverter;
    }
}
