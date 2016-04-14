package routers;

import java.util.HashMap;
import forms.Form;
import http_messages.Request;
import javaserver.Directory;
import responders.*;
import responders.file_responders.*;
import text_parsers.ParameterParser;

public class CobSpecRouter {
    private Directory directory;
    private HashMap<String, Responder> routeResponders;

    public CobSpecRouter(Directory dir) {
        directory = dir;
        this.routeResponders = getRoutesAndResponders();
    }

    private HashMap<String, Responder> getRoutesAndResponders() {
        HashMap<String, Responder> routes = new HashMap<String, Responder>();
        routes.put("/", new DirectoryResponder(getOption(), directory));
        routes.put("/parameters", new ParameterResponder(getOption()));
        routes.put("/method_options", new OptionResponder(new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"}));
        routes.put("/form", new FormResponder(new String[] {"GET", "POST", "PUT", "DELETE"}, new Form("")));
        routes.put("/redirect", new RedirectResponder(getOption()));
        routes.put("/logs", new LogResponder(getOption()));
        for (String fileName : directory.getNonImageFileNames()) {
            routes.put("/" + fileName, new TextFileResponder(getOption(), directory));
        }
        for (String imageFileName : directory.getImageFileNames()) {
            routes.put("/" + imageFileName, new ImageResponder(getOption(), directory));
        }
        return routes;
    }

    private String[] getOption() {
        return new String[] {"GET"};
    }

    public Responder getResponder(Request request, Directory directory) {
        String route = ParameterParser.getDecodedURIWithoutParams(request.getURI());
        Responder responder = routeResponders.get(route);
        if (responder == null) {
            return new ErrorResponder();
        } else {
            if (request.isPatchRequest()) {
                return new PatchResponder(new String[] {"GET", "PATCH"}, directory);
            } else if (request.isPartialRequest()) {
                return new PartialResponder(getOption(), directory);
            } else {
                return responder;
            }
        }
    }
}
