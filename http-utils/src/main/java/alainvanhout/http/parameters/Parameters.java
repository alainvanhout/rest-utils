package alainvanhout.http.parameters;

import java.util.*;

public class Parameters {

    private Map<String, List<String>> map = new LinkedHashMap<>();

    public Parameters add(final String key, final String... values) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).addAll(Arrays.asList(values));
        return this;
    }

    public Parameters add(final Map<String, List<String>> valueMap) {
        this.map.putAll(valueMap);
        return this;
    }

    public Parameters add(final Parameters parameters) {
        return add(parameters.getMap());
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

    public Map<String, List<String>> getMap() {
        return Collections.unmodifiableMap(map);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
