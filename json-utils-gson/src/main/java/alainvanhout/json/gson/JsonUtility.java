package alainvanhout.json.gson;

import alainvanhout.json.JsonConverter;

public class JsonUtility {
    public static final JsonConverter DEFAULT = JsonConverterImpl.with().init();
}
