package http_messages;

public enum ResponseHeader {
    AUTHENTICATION ("WWW-Authenticate: "),
    CONTENT_LENGTH ("Content-Length: "),
    CONTENT_TYPE ("Content-Type: "),
    OPTIONS ("Allow: "),
    REDIRECT ("Location: ");

    private String keyWord;

    private ResponseHeader(String keyWord) {
       this.keyWord = keyWord;
    }

    public String getKeyword() {
       return this.keyWord;
    }
}
