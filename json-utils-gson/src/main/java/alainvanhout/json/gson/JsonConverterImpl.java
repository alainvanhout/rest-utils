package alainvanhout.json.gson;

import alainvanhout.json.JsonConverter;
import alainvanhout.json.JsonConverterBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverterImpl implements JsonConverter, JsonConverterBuilder {

    private Gson gson = null;

    // configuration
    private boolean configFormatted = false;

    /**
     * Use {JsonConverterImpl::with} to create new {@link JsonConverterBuilder} instances
     */
    private JsonConverterImpl(){
    }

    /**
     * Produces a new {@link JsonConverterBuilder} instance, to which configuration can be applied,
     * before calling {@link JsonConverterBuilder::init} to create a {@link JsonConverter} instance
     * @return A new {@link JsonConverterBuilder} instance
     */
    public static JsonConverterBuilder with(){
        return new JsonConverterImpl();
    }

    @Override
    public JsonConverterBuilder formatted() {
        this.configFormatted = true;
        return this;
    }

    @Override
    public JsonConverter init() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        if (configFormatted){
            gsonBuilder.setPrettyPrinting();
        }

        gson = gsonBuilder.create();

        return this;
    }

    @Override
    public String toJson(final Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T toObject(final String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> List<T> toList(final String json, final Type type) {
        return gson.fromJson(json, type);
    }

    @Override
    public Map toMap(final String json) {
        return gson.fromJson(json, HashMap.class);
    }
}
