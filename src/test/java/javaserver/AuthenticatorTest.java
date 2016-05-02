package javaserver;

import static org.junit.Assert.*;
import org.junit.Test;
import http_messages.Request;

public class AuthenticatorTest {
    private String routeRequiringAuth = "/logs";
    private String invalidCredentialsAuthHeader = "Authorization: Basic xxx";
    private String nonAuthHeader = "Host: localhost://5000";
    private String validCredentialsAuthHeader = "Authorization: Basic YWRtaW46aHVudGVyMg==";

    private Request noAuth = new Request.RequestBuilder("GET " + routeRequiringAuth).build();
    private Request unauthorizedCredentials = new Request.RequestBuilder("GET " + routeRequiringAuth + Request.newLine + invalidCredentialsAuthHeader).build();

    private Request authorizedCredentials = new Request.RequestBuilder("GET " + routeRequiringAuth +  Request.newLine + validCredentialsAuthHeader).build();

    private Request requestWithMultipleHeaders = new Request.RequestBuilder("GET " + routeRequiringAuth +  Request.newLine 
        + nonAuthHeader + Request.newLine
        + validCredentialsAuthHeader).build();

    @Test
    public void validCredentialsAreAuthorized() {
       assertTrue(Authenticator.isAuthorized(authorizedCredentials));
    }

    @Test
    public void invalidCredentialsAreNotAuthorized() {
       assertFalse(Authenticator.isAuthorized(unauthorizedCredentials));
    }

    @Test
    public void requestForRestrictedContentIsNotAuthorizedWithoutCredentials() {
       assertFalse(Authenticator.isAuthorized(noAuth));
    }

    @Test
    public void onlyEvaluatesAuthHeader() {
        boolean errorCaught = false;
        try {
            Authenticator.isAuthorized(requestWithMultipleHeaders);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorCaught = true;
        }
        assertFalse(errorCaught);
    }
}
