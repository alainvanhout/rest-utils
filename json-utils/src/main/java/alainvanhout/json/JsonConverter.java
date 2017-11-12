package alainvanhout.json;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Interface for converting to and from JSON, with a number of convenience methods
 */
public interface JsonConverter {
    /**
     * Converts an object to the equivalent JSON representation.
     * @param object The object to be converted
     * @return The object's JSON representation
     */
    String toJson(final Object object);

    /**
     * Produces an object based on the provided JSON string and a {@link Class} reference.
     * @param json The JSON string
     * @param clazz The class of object that is to be produced
     * @param <T> The generic associated with the class of the object that is to be produced
     * @return The object that is to be produced
     */
    <T> T toObject(final String json, final Class<T> clazz);

    /**
     * Produces a list based on the provided JSON string and a {@link Type} reference.
     * @param json The JSON string
     * @param type The Generic {@link Type} of the list that is to be produced
     * @param <T> The generic associated with the type of the list that is to be produced
     * @return The list that is to be produced
     */
    <T> List<T> toList(final String json, final Type type);

    /**
     * Convenience method to produce a {@link Map} instance based on the provided JSON string
     * @param json The JSON string
     * @return The Map instance that is to be produced
     */
    Map toMap(final String json);
}
