package alainvanhout.http.common;

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

    public boolean matches(int statusCode) {
        return statusCode >= lowerbound && statusCode <= upperbound;
    }
}
