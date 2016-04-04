package javaserver.ResponseBuilders;

import javaserver.Request;
import javaserver.Response;

public interface Responder {

	public Response getResponse(Request request);
	public String getStatusLine(Request request);
}
