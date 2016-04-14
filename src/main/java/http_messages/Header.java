package http_messages;

public class Header {
    private String keyWord;
    private String data;
    public static final String SEPARATOR = ": ";

    private Header(HeaderBuilder builder) {
        this.keyWord = builder.keyWord;
        this.data = builder.data;
    }

    public String getKeyWord() {
       return keyWord;
    }

    public String getData() {
       return data.trim();
    }

    public String getLine() {
       return keyWord + SEPARATOR + data;
    }

    public static boolean isKnownRequestHeader(String keyWord) {
       for (RequestHeader constant : RequestHeader.values()) {
          if (constant.getKeyword().equals(keyWord)) {
              return true;
          }
       }
       return false;
    }

    public static class HeaderBuilder {
       protected String keyWord;
       protected String data;

       public HeaderBuilder(String headerLine) {
          try {
              this.keyWord = headerLine.split(SEPARATOR)[0];
              this.data = headerLine.split(SEPARATOR)[1];
          } catch (ArrayIndexOutOfBoundsException e) {
              System.err.println("The server has received an incorrectly formatted raw header.");
          }
       }

       public Header build() {
           return new Header(this);
       }
    }
}
