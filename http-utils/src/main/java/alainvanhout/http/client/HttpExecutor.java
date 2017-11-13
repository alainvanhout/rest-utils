package alainvanhout.http.client;

import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

/**
 * Interface for performing http(s) requests.
 */
public interface HttpExecutor {

    /**
     * Executes an http request based on a {@link Request} dto and provides a {@link Response} dto.
     * This process in no way alters the {@link Request} dto.
     *
     * @param request The request to be performed
     * @return The response that was received (including associated meta-data)
     */
    Response execute(final Request request);
}
