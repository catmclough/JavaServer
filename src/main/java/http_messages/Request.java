package http_messages;

import java.util.ArrayList;
import text_parsers.ParameterParser;
import text_parsers.RequestParser;

public class Request {
    private String method;
    private String uri;
    private String[] params;
    private Header[] headers;
    private String data;

    private Request(RequestBuilder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.params = builder.params;
        this.headers = builder.headers;
        this.data = builder.data;
    }

    public String getMethod() {
        return method;
    }

    public String getURI() {
        return uri;
    }

    public String[] getParams() {
        return params;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public String getHeaderData(String headerKeyWord) {
       for (Header header : headers) {
          if (header.getKeyWord().equals(headerKeyWord)) {
              return header.getData();
          }
       }
       return null;
    }

    public String getData() {
        return data;
    }

    public boolean isPatchRequest() {
        return method.equals("PATCH");
    }

    public boolean isPartialRequest() {
        return getHeaderData(RequestHeader.PARTIAL_REQUEST.getKeyword()) != null;
    }

    public static class RequestBuilder {
        protected String method;
        protected String uri;
        protected String[] params;
        protected Header[] headers;
        protected String data;

        public RequestBuilder(String rawRequest) {
            this.method = RequestParser.getRequestMethod(rawRequest);
            this.uri = RequestParser.getDecodedRequestURI(rawRequest);
            this.params = ParameterParser.getDecodedParams(rawRequest);
            this.headers = getKnownHeaders(rawRequest);
            this.data = RequestParser.getRequestData(rawRequest);
        }

        private Header[] getKnownHeaders(String rawRequest) {
            ArrayList<Header> headerCollection = new ArrayList<Header>();
            for (String rawHeader : RequestParser.getRawRequestHeaders(rawRequest)) {
                headerCollection.add(new Header.HeaderBuilder(rawHeader).build());
            }
            Header[] allHeaders = new Header[headerCollection.size()];
            return headerCollection.toArray(allHeaders);
        }

        public Request build() {
            return new Request(this);
        }
    }
}