package alainvanhout.http.parameters;

import java.util.*;

public class Parameters {

    private Map<String, List<String>> map = new HashMap<>();

    public Parameters add(final String key, final String... values) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).addAll(Arrays.asList(values));
        return this;
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public List<String> get(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Key not found: " + key);
        }
        return map.get(key);
    }

    public String getOne(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Key not found: " + key);
        }
        if (map.get(key).isEmpty()) {
            throw new IllegalArgumentException("No value found for key: " + key);
        }
        return map.get(key).get(0);
    }
}
