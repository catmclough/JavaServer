package routers;

import java.util.HashMap;
import http_messages.Request;
import responders.ErrorResponder;
import responders.Responder;
import text_parsers.ParameterParser;

public interface Router {
    public HashMap<String, Responder> getRoutesAndResponders();

    default public Responder getResponder(Request request) {
        String route = ParameterParser.getDecodedURIWithoutParams(request.getURI());
        Responder responder = getRoutesAndResponders().get(route);
        if (responder == null) {
            return new ErrorResponder();
        } else {
            return responder;
        }
    }
}
