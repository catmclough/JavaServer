package javaserver;

import java.util.HashMap;
import http_messages.Request;
import responders.*;
import text_parsers.ParameterParser;

public class Router {
    private HashMap<String, Responder> routeResponders;

    public Router(HashMap<String, Responder> routes) {
        this.routeResponders = routes;
    }

    public Responder getResponder(Request request) {
        String route = ParameterParser.getDecodedURIWithoutParams(request.getURI());
        Responder responder = routeResponders.get(route);
        if (responder == null) {
            return new ErrorResponder();
        } else {
            return responder;
        }
    }
}
