package http_messages;

public enum RequestHeader {
    PARTIAL_REQUEST ("Range"),
    ETAG ("If-Match"),
    AUTH ("Authorization"),
    CONTENT_LENGTH ("Content-Length");

    private final String headerKeyword;

    private RequestHeader(String headerKeyword) {
        this.headerKeyword = headerKeyword;
    }

    public String getKeyword() {
       return this.headerKeyword;
    }
}
