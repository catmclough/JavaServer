package http_messages;

public enum HTTPStatus {
    OK ("HTTP/1.1 200 OK"),
    NO_CONTENT ("HTTP/1.1 204 No Content"),
    PARTIAL_CONTENT ("HTTP/1.1 206 Partial Content"),
    FOUND ("HTTP/1.1 302 Found"),
    UNAUTHORIZED ("HTTP/1.1 401 Unauthorized"),
    NOT_FOUND ("HTTP/1.1 404 Not Found"),
    METHOD_NOT_ALLOWED ("HTTP/1.1 405 Method Not Allowed");

    private final String statusLine;

    private HTTPStatus(String statusLine) {
        this.statusLine = statusLine;
    }

    public String getStatusLine() {
        return this.statusLine;
    }
}
