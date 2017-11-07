package alainvanhout.http.client;

import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

public interface HttpExecutor {

    Response execute(final Request request);
}
