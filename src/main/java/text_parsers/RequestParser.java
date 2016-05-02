package text_parsers;

import java.util.Arrays;
import decoders.UTF8Decoder;
import http_messages.Request;

public class RequestParser {

    public static String getRequestMethod(String rawRequest) {
        return rawRequest.split("\\s")[0];
    }

    public static String getRequestURI(String rawRequest) {
        try {
            return rawRequest.split("\\s")[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static String getDecodedRequestURI(String rawRequest) {
       return UTF8Decoder.decode(getRequestURI(rawRequest));
    }

    public static String[] getRawRequestHeaders(String rawRequest) {
        String[] requestParts = rawRequest.split(System.lineSeparator() + System.lineSeparator());
        String[] requestLineAndHeaders = requestParts[0].split(System.lineSeparator()); 
        String[] rawHeaders = Arrays.copyOfRange(requestLineAndHeaders, 1, requestLineAndHeaders.length);
        return rawHeaders;
    }

    public static String getRequestData(String rawRequest) {
        String[] requestParts = rawRequest.split(System.lineSeparator() + System.lineSeparator());
        try {
            return requestParts[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static String getImageFormat(Request request) {
        try {
            return request.getURI().split("\\.")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Request has no image format extension.");
            return "";
        }
    }
}
