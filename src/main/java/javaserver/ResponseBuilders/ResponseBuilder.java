package javaserver.ResponseBuilders;

import javaserver.Request;
import javaserver.Response;

public interface ResponseBuilder {

	public Response getResponse(Request request);
	public void setResponseData(Request request);
	public String getStatusLine(Request request);
}
