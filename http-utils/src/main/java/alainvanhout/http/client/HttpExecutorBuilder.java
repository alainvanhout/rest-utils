package alainvanhout.http.client;

import alainvanhout.json.JsonConverter;

/**
 * An interface for building {@link HttpExecutor} instances.
 */
public interface HttpExecutorBuilder {

    /**
     * Returns a functional {@link HttpExecutor} instance based on the previously provided configuration,
     * or the defaults for those configuration options.
     *
     * @return A functional {@link HttpExecutor} instance
     */
    HttpExecutor init();

    /**
     * Allows declaring which {@link JsonConverter} should be used by the {@link HttpExecutor} that is to be
     * built. Need not be set, if no use is made of JSON-related functionality.
     *
     * @param jsonConverter A {@link JsonConverter} instance
     * @return The builder itself
     */
    HttpExecutorBuilder jsonConverter(JsonConverter jsonConverter);
}
