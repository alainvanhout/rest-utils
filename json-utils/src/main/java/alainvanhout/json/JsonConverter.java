package alainvanhout.json;

import java.lang.reflect.Type;
import java.util.List;

public interface JsonConverter {
    String toJson(final Object object);

    <T> T toObject(final String json, final Class<T> clazz);

    <T> List<T> toList(final String json, final Type type);
}
