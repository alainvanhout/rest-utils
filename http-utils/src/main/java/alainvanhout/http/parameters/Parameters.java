package alainvanhout.http.parameters;

import java.util.*;

/**
 * A wrapper for e.g. url query parameter or http header information, with various convenience methods.
 * <p>
 * The internal state is based on a {@link LinkedHashMap}, to ensure that the internal order consistently reflects the
 * order of addition, to e.g. facilitate testing. {@link Parameters::equals} and {@link Parameters::hashCode} are
 * however based on {@link HashMap}, since order is not inherently relevant.
 */
public class Parameters {

    /**
     * The internal state.
     */
    private Map<String, List<String>> map = new LinkedHashMap<>();

    /**
     * Convenience method to add a parameter key, with one or more values as varargs.
     *
     * @param key    The parameter key
     * @param values The parameter values (may be zero, one or more)
     * @return The {@link Parameters} instance itself
     */
    public Parameters add(final String key, final String... values) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Convenience method to add a parameter key, with one or more values as a {@link List}.
     *
     * @param values A list of values
     * @return The {@link Parameters} instance itself
     */
    public Parameters add(final Map<String, List<String>> values) {
        this.map.putAll(values);
        return this;
    }

    /**
     * Add all the content of a given {@link Parameters} instance to this one.
     *
     * @param parameters The given {@link Parameters} instance
     * @return The {@link Parameters} instance itself
     */
    public Parameters add(final Parameters parameters) {
        return add(parameters.getMap());
    }

    /**
     * Whether the given key is present, with zero, one or more associated values.
     *
     * @param key A key
     * @return The {@link Parameters} instance itself
     */
    public boolean contains(String key) {
        return map.containsKey(key);
    }

    /**
     * Returns the list of values associated with the key.
     * If the key is not present, an {@link IllegalArgumentException} is thrown.
     *
     * @param key The given key
     * @return The {@link Parameters} instance itself
     */
    public List<String> get(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Key not found: " + key);
        }
        return map.get(key);
    }

    /**
     * Returns the first value associated with the key.
     * If the key is not present or does not contain any values, an {@link IllegalArgumentException} is thrown.
     *
     * @param key The given key
     * @return The {@link Parameters} instance itself
     */
    public String getOne(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Key not found: " + key);
        }
        if (map.get(key).isEmpty()) {
            throw new IllegalArgumentException("No value found for key: " + key);
        }
        return map.get(key).get(0);
    }

    /**
     * Returns the internal state of the {@link Parameters} instance, as an unmodifiable map.
     *
     * @return An unmodifiable map
     */
    public Map<String, List<String>> getMap() {
        return Collections.unmodifiableMap(map);
    }

    /**
     * The number of keys present, with zero, one or more values.
     *
     * @return The {@link Parameters} instance itself
     */
    public int size() {
        return map.size();
    }

    /**
     * Whether the number of keys present is zero.
     *
     * @return The {@link Parameters} instance itself
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Implementation based on {@link HashMap}.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (Objects.isNull(o) && Objects.isNull(map)) {
            return true;
        }
        if (Objects.isNull(o) || Objects.isNull(map)) {
            return false;
        }
        if (!(o instanceof Parameters)) {
            return false;
        }
        Parameters parameters = (Parameters) o;
        final HashMap<String, List<String>> unsorted1 = new HashMap<>(this.getMap());
        final HashMap<String, List<String>> unsorted2 = new HashMap<>(parameters.getMap());
        return unsorted1.equals(unsorted2);
    }

    /**
     * Implementation based on {@link HashMap}.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return map != null ? new HashMap<>(map).hashCode() : 0;
    }
}
