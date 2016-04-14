package responders;

import forms.Form;
import http_messages.Request;
import http_messages.Response;

public class FormResponder implements Responder {
    private String[] supportedMethods;
    private Form form;

    public FormResponder(String[] supportedMethods, Form form) {
        this.supportedMethods = supportedMethods;
        this.form = form;
    }

    @Override
    public Response getResponse(Request request) {
    return new Response.ResponseBuilder()
        .statusLine(getStatusLine(request, supportedMethods))
        .body(getData(request))
        .build();
    }

    private String getData(Request request) {
        if (requestChangesData(request)) {
            form = new Form(request.getData());
        }
        return form.getData();
    }

    private boolean requestChangesData(Request request) {
        return (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) || request.getMethod().equals("DELETE");
    }
}
