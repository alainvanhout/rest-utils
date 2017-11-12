package alainvanhout.json.gson;

import alainvanhout.json.JsonConverter;

/**
 * Convenience class that provides varying typical {@link JsonConverter} instances for common use-cases.
 */
public class JsonUtility {
    /**
     * A {@link JsonConverter} instance that relies for all configuration relies on the defaults.
     */
    public static final JsonConverter DEFAULT = JsonConverterImpl.with().init();
}
