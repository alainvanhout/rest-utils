package alainvanhout.json.gson;

import alainvanhout.json.JsonConverter;
import alainvanhout.json.JsonConverterBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConverterImpl implements JsonConverter, JsonConverterBuilder {

    private Gson gson = null;

    private JsonConverterImpl(){
    }

    public static JsonConverterBuilder with(){
        return new JsonConverterImpl();
    }

    @Override
    public JsonConverter init() {
        gson = new GsonBuilder().create();
        return this;
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T toObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> List<T> toList(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
