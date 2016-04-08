package javaserver.responders;

import java.util.Base64;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestLog;
import javaserver.RequestParser;
import javaserver.Response;

public class LogResponder implements Responder {
    private RequestLog requestLog;
    private String[] supportedMethods;
    private String realm = "Cob Spec Logs";
    private String username = "admin";
    private String password = "hunter2";

	public LogResponder(String[] supportedMethods, RequestLog log) {
	    this.supportedMethods = supportedMethods;
	    this.requestLog = log;
	}

	@Override
	public Response getResponse(Request request) {
		return new Response.ResponseBuilder(getStatusLine(request))
		    .header(getBasicAuthHeader())
		    .body(getLog())
            .build();
	}
	
	@Override
	public String getStatusLine(Request request) {
	    if (requestIsSupported(supportedMethods, request.getMethod()) 
	            && request.hasBasicAuthHeader()
	            && hasValidCredentials(RequestParser.getCodedCredentials(request))) {
	        return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	    } else {
            return HTTPStatusCode.FOUR_OH_ONE.getStatusLine();
	    }
	}
	
	private String getBasicAuthHeader() {
	   return "WWW-Authenticate: Basic realm=\"" + realm + "\"";
	}

	private String getLog() {
	    String body = "";
	    if (!requestLog.log.isEmpty()) {
	        System.out.println("request in log.");
            for (String request : requestLog.log) {
                body += request;
            }
	    }
	    return body;
	}

	private boolean hasValidCredentials(String codedCredentials) {
	    byte[] decodedCredentials = Base64.getMimeDecoder().decode(codedCredentials.getBytes());
	    String credentials = new String(decodedCredentials);
	    return credentials.equals(username + ":" + password);
	}
}
