package javaserver;

import javaserver.ResponseBuilders.*;

public class ResponseBuilderFactory {

	public static ResponseBuilder createResponder(Request request) {
		String requestURI = getURI(request);
		ResponseBuilder responseBuilder;
		String requestHandlerType = Routes.routeHandlers.get(requestURI);

		if (requestHandlerType == null) {
			requestHandlerType = "Default Handler"; //make Responder for Bad Requests
		}

		switch (requestHandlerType) {
		case "Parameter Handler":
			responseBuilder = new ParameterResponseBuilder(request);
			break;

		case "Directory Handler":
			responseBuilder = new DirectoryResponseBuilder(request);
			break;

		case "Redirect Handler":
			responseBuilder = new RedirectResponseBuilder(request);
			break;

		case "Option Handler":
			responseBuilder = new OptionResponseBuilder(request);
			break;

		case "File Handler":
			responseBuilder = new FileResponseBuilder(request);
			break;

		case "Form Handler":
			responseBuilder = new FormResponseBuilder(request);
			break;

		default:
			responseBuilder = new ResponseBuilder(request);
			break;
		}
		return responseBuilder;
	}

	private static String getURI(Request request) {
		if (request.hasParams()) {
			return request.getURIWithoutParams();
		} else {
			return request.getURI();
		}
	}
}