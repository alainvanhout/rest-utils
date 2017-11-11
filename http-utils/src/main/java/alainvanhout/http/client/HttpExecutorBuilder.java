package alainvanhout.http.client;

import alainvanhout.json.JsonConverter;

public interface HttpExecutorBuilder {

    HttpExecutorBuilder jsonConverter(JsonConverter jsonConverter);

    HttpExecutor init();
}
