package responders;

import static org.junit.Assert.*;
import org.junit.Test;
import forms.Form;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import responders.FormResponder;

public class FormResponderTest {
    private String formRoute = "/form";
    private String mockPostData = "snack=crackerjack";

    private Request getForm = new Request.RequestBuilder("GET " + formRoute).build();
    private Request postFormWithData = new Request.RequestBuilder("POST " + formRoute + Request.newLine + Request.newLine + mockPostData).build();
    private Request deleteForm = new Request.RequestBuilder("DELETE " + formRoute).build();

    private String[] supportedFormMethods = new String[] {"GET", "POST", "PUT", "DELETE"};

    private String mockData = "XOXOXOXO";
    private Form mockForm = new Form(mockData);
    private FormResponder responder = new FormResponder(supportedFormMethods, mockForm);

    private String twoHundred = HTTPStatus.OK.getStatusLine();

    @Test
    public void testGetFormResponseCode() {
        Response getFormResponse = responder.getResponse(getForm);
        assertEquals(getFormResponse.getStatusLine(), twoHundred);
    }

    @Test
    public void testPostToFormResponseCode() {
        Response postFormResponse = responder.getResponse(postFormWithData);
        assertEquals(postFormResponse.getStatusLine(), twoHundred);
    }

    @Test
    public void testPutFormResponseCode() {
        Request putForm = new Request.RequestBuilder("PUT " + formRoute).build();
        Response putFormResponse = responder.getResponse(putForm);
        assertEquals(putFormResponse.getStatusLine(), twoHundred);
    }

    @Test
    public void testResponseToDeleteForm() {
        Response deleteFormResponse = responder.getResponse(deleteForm);
        assertEquals(deleteFormResponse.getStatusLine(), twoHundred);
    }

    @Test
    public void testInvalidMethodForm() {
        Request invalidRequest = new Request.RequestBuilder("PATCH " + formRoute).build();
        Response deleteFormResponse = responder.getResponse(invalidRequest);
        assertEquals(deleteFormResponse.getStatusLine(), HTTPStatus.NOT_FOUND.getStatusLine());
    }

    @Test
    public void testPostDataToForm() {
        Response getFormResponse = responder.getResponse(getForm);
        assertFalse(getFormResponse.getBody().contains(mockPostData));

        responder.getResponse(postFormWithData);
        Response getUpdatedForm = responder.getResponse(getForm);
        assertTrue(getUpdatedForm.getBody().contains(mockPostData));
    }

    @Test
    public void testDeleteForm() {
        Response getFormResponse =  responder.getResponse(getForm);
        assertFalse(getFormResponse.getBody().isEmpty());

        responder.getResponse(deleteForm);
        Response newFormResponse = responder.getResponse(getForm);
        assertTrue(newFormResponse.getBody().isEmpty());
    }
}
