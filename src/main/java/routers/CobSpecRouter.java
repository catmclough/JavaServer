package routers;

import java.util.HashMap;
import javaserver.Directory;
import responders.*;
import responders.file_responders.*;
import forms.Form;

public class CobSpecRouter implements Router {
    private Directory directory;
    private String defaultRedirectLocation = "http://localhost:5000/";

    public CobSpecRouter(Directory dir) {
        this.directory = dir;
    }

    private String[] getOption() {
        return new String[] {"GET"};
    }

    @Override
    public HashMap<String, Responder> getRoutesAndResponders() {
        HashMap<String, Responder> routes = new HashMap<String, Responder>();
        routes.put("/", new DirectoryResponder(getOption(), directory));
        routes.put("/parameters", new ParameterResponder(getOption()));
        routes.put("/method_options", new OptionResponder(new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"}));
        routes.put("/form", new FormResponder(new String[] {"GET", "POST", "PUT", "DELETE"}, new Form("")));
        routes.put("/redirect", new RedirectResponder(getOption(), defaultRedirectLocation));
        routes.put("/logs", new LogResponder(getOption()));
        for (String fileName : directory.getNonImageFileNames()) {
            routes.put("/" + fileName, new TextFileResponder(new String[] {"GET", "PATCH"}, directory));
        }
        for (String imageFileName : directory.getImageFileNames()) {
            routes.put("/" + imageFileName, new ImageResponder(getOption(), directory));
        }
        return routes;
    }
}
