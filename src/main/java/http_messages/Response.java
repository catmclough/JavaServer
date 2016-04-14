package http_messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Response {

    private String statusLine;
    private Header[] headers;
    private String body;
    private byte[] bodyData;
    private String newLine = System.lineSeparator();

    private Response(ResponseBuilder builder) {
        this.statusLine = builder.statusLine;
        this.headers = builder.headers;
        this.body = builder.body;
        this.bodyData = builder.bodyData;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    public byte[] formatResponse() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String output = statusLine + newLine;
        if (headers != null) {
            for (Header header : headers) {
                output += header.getLine() + newLine;
            }
        }
        output += newLine;
        if (body != null) {
          output += body + newLine;
        }
        try {
            outputStream.write(output.getBytes());
            if (bodyData != null) {
                outputStream.write(bodyData);
            }
        } catch (IOException e) {
            System.out.println("Unable to write response body to ByteArrayOutputStream.");
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static class ResponseBuilder {
        protected String statusLine;
        protected Header[] headers;
        protected String body;
        protected byte[] bodyData;

        public ResponseBuilder statusLine(String statusLine) {
            this.statusLine = statusLine;
            return this;
        }

        public ResponseBuilder headers(Header[] headers) {
            this.headers = headers;
            return this;
        }

        public ResponseBuilder body(String body) {
            this.body = body;
            return this;
        }

        public ResponseBuilder bodyData(byte[] bodyData) {
            this.bodyData = bodyData;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
