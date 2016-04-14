package javaserver;

import decoders.BasicAuthDecoder;
import http_messages.Header;
import http_messages.Request;
import http_messages.RequestHeader;

public class Authenticator {
    private static String username = "admin";
    private static String password = "hunter2";

    public static boolean isAuthorized(Request request) {
        boolean authorized = false;
        for (Header header : request.getHeaders()) {
            if (header.getKeyWord().equals(RequestHeader.AUTH.getKeyword())) {
                String codedCredentials = getCodedCredentials(header.getData());
                if (hasValidCredentials(codedCredentials)) {
                   authorized = true;
                }
            }
        }
        return authorized;
    }

    private static boolean hasValidCredentials(String codedCredentials) {
        return BasicAuthDecoder.decode(codedCredentials).equals(username + ":" + password);
    }

    private static String getCodedCredentials(String headerData) {
        return headerData.split("Basic")[1];
    }
}
