package alainvanhout.http.common;

/**
 * Provides a convenient way to check whether a given htpp response status code is in a given range.
 */
public enum StatusCodeRange {
    _100(100, 199),
    _200(200, 299),
    _300(300, 399),
    _400(400, 499),
    _500(500, 599);

    private final int lowerbound;
    private final int upperbound;

    StatusCodeRange(int lowerbound, int upperbound) {
        this.lowerbound = lowerbound;
        this.upperbound = upperbound;
    }

    /**
     * Whether the provided http response status code is within the given range.
     *
     * @param statusCode A http response status code
     * @return Whether it in the given range
     */
    public boolean matches(int statusCode) {
        return statusCode >= lowerbound && statusCode <= upperbound;
    }
}
