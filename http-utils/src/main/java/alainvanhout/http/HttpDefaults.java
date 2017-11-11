package alainvanhout.http;

import alainvanhout.json.JsonConverter;

public class HttpDefaults {
    private static JsonConverter defaultJsonConverter = null;

    public static void setDefaultJsonConverter(final JsonConverter jsonConverter){
        defaultJsonConverter = jsonConverter;
    }

    public static JsonConverter getDefaultJsonConverter() {
        return defaultJsonConverter;
    }
}
