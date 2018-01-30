package alainvanhout.json.gson;

import alainvanhout.json.JsonConverter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Convenience class that provides varying typical {@link JsonConverter} instances for common use-cases.
 */
public class JsonUtility {
    /**
     * A {@link JsonConverter} instance that relies for all configuration relies on the defaults.
     */
    public static final JsonConverter DEFAULT = JsonConverterImpl.with().init();

    /**
     * A {@link JsonConverter} instance that produced formatted (i.e. multi-line) JSON.
     */
    public static final JsonConverter FORMATTED = JsonConverterImpl.with().formatted().init();

    public static String toJson(final Object object) {
        return DEFAULT.toJson(object);
    }

    public static <T> T toObject(final String json, Class<T> clazz) {
        return DEFAULT.toObject(json, clazz);
    }

    public static <T> T toObject(final String json, final Type type) {
        return DEFAULT.toObject(json, type);
    }

    public static <T> List<T> toList(final String json, final Type type) {
        return DEFAULT.toList(json, type);
    }

    public static Map toMap(final String json) {
        return DEFAULT.toMap(json);
    }

}
