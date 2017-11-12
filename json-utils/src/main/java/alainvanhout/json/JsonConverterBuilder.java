package alainvanhout.json;

/**
 * An interface for building {@link JsonConverter} instances.
 *
 * Currently it this includes few configuration setters, but more will be added in the future.
 */
public interface JsonConverterBuilder {

    /**
     * Returns a functional {@link JsonConverter} instance based on the previously provided configuration,
     * or the defaults for those configuration options.
     * @return A functional {@link JsonConverter}
     */
    JsonConverter init();

    /**
     * Configuration option, to specify that a converted should output multi-line JSON.
     * @return The builder itself
     */
    JsonConverterBuilder formatted();
}
