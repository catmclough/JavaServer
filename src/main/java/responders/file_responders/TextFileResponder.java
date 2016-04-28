package responders.file_responders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import http_messages.HTTPStatus;
import http_messages.Header;
import http_messages.Request;
import io.FileReader;
import javaserver.Directory;

public class TextFileResponder extends FileResponder {

    public TextFileResponder(String[] supportedMethods, Directory dir) {
        super(supportedMethods, dir);
    }

    @Override
    public String getSuccessfulStatusLine(Request request) {
        if (request.isPartialRequest()) {
            return HTTPStatus.PARTIAL_CONTENT.getStatusLine();
        } else if (request.isPatchRequest()) {
            return HTTPStatus.NO_CONTENT.getStatusLine();
        } else {
            return HTTPStatus.OK.getStatusLine();
        }
    }

    @Override
    public Header[] getHeaders(Request request) {
        if (request.isPartialRequest()) {
           return PartialResponder.getHeaders(request); 
        } else {
            return null;
        }
    }

    @Override
    public String getBody(Request request) {
        File file = new File(directory.getPath() + request.getURI());
        if (request.isPartialRequest()) {
            return PartialResponder.getPartialBody(request, file);
        } else if (request.isPatchRequest()) {
           PatchResponder.updateFileContents(request, file, getFileContents(request, file)); 
        }
        return getFileContents(request, file);
    }

    @Override
    public byte[] getBodyData(Request request, FileReader reader) {
        return null;
    }

    protected String getFileContents(Request request, File file) {
        int fileLength = (int) file.length();
        byte[] fileContents = new byte[fileLength];

        try {
            FileInputStream fileInput = new FileInputStream(directory.getPath() + file.getName());
            fileInput.read(fileContents);
            fileInput.close();
        } catch (IOException e) {
            System.err.println("Unable to read from file with input stream");
            e.printStackTrace();
        }
        return new String(fileContents).trim();
    }
}