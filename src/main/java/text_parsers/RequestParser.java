package text_parsers;

import java.util.ArrayList;

import decoders.UTF8Decoder;
import http_messages.Header;
import http_messages.Request;
import http_messages.RequestHeader;

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

    public static String[] getKnownRawRequestHeaders(String rawRequest) {
        String[] requestLines = rawRequest.split(System.lineSeparator());
        ArrayList<String> headerCollection = new ArrayList<String>();

        for (int i = 1; i < requestLines.length; i++) {
            String[] headerParts = requestLines[i].split(Header.SEPARATOR);
            if (headerParts != null) {
                if (Header.isKnownRequestHeader(headerParts[0])) {
                    headerCollection.add(requestLines[i]);
                }
            }
        }
        String[] rawHeaders = new String[headerCollection.size()];
        return headerCollection.toArray(rawHeaders);
    }

    public static String getRequestData(String rawRequest) {
        String[] requestLines = rawRequest.split(System.lineSeparator());
        String data = "";
        for (int i = 1; i < requestLines.length; i++) {
            boolean dataLine = true;
            for (RequestHeader knownHeader : RequestHeader.values()) {
               if (requestLines[i].startsWith(knownHeader.getKeyword() + ":")) {
                   dataLine = false;
               }
            }
            if (dataLine) {
                data += requestLines[i].trim();
            }
        }
        return data;
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
