package javaserver;

import javaserver.RequestHandlers.*;
import javaserver.ResponseBuilders.*;

public class ResponseBuilderFactory {

	public static ResponseBuilder createResponder(Request request) {
		String requestURI = getURI(request);
		ResponseBuilder responseBuilder;
		String requestHandlerType = Routes.acceptableRoutes.get(requestURI);

		if (requestHandlerType == null) {
			requestHandlerType = "Default Handler";
		}

		switch (requestHandlerType) {
		case "Parameter Handler":
			ParameterHandler paramHandler = new ParameterHandler(request);
			responseBuilder = new ParameterResponseBuilder(paramHandler);
			break;

		case "Directory Handler":
			DirectoryHandler directoryHandler = new DirectoryHandler(request);
			responseBuilder = new DirectoryResponseBuilder(directoryHandler);
			break;

		case "Redirect Handler":
			RedirectHandler redirectHandler = new RedirectHandler(request);
			responseBuilder = new RedirectResponseBuilder(redirectHandler);
			break;

		case "Option Handler":
			OptionHandler optionHandler  = new OptionHandler(request);
			responseBuilder = new OptionResponseBuilder(optionHandler);
			break;

		case "File Handler":
			FileHandler fileHandler  = new FileHandler(request);
			responseBuilder = new FileResponseBuilder(fileHandler);
			break;

		default:
			RequestHandler requestHandler = new RequestHandler(request);
			responseBuilder = new ResponseBuilder(requestHandler);
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
