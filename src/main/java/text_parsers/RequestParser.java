package text_parsers;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
        String nonDataLines = rawRequest.split(Request.newLine + Request.newLine)[0];
        String[] lines = nonDataLines.split(Request.newLine);
        return Arrays.copyOfRange(lines, 1, lines.length);
     }

    public static String getRequestData(String rawRequest) {
        try {
            Scanner scanner = new Scanner(rawRequest);
            scanner.useDelimiter(Request.newLine + Request.newLine);
            scanner.next();
            String data = scanner.next();
            scanner.close();
            return data;
        } catch (NoSuchElementException|IllegalStateException e) {
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
