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
    public String getSuccessfulStatusLine() {
        return HTTPStatus.OK.getStatusLine();
    }

    @Override
    public Header[] getHeaders(Request request) {
        return null;
    }

    @Override
    public String getBody(Request request) {
        return getFileContents(request);
    }

    @Override
    public byte[] getBodyData(Request request, FileReader reader) {
        return null;
    }

    protected String getFileContents(Request request) {
        File thisFile = new File(directory.getPath() + request.getURI());
        int fileLength = (int) thisFile.length();
        byte[] fileContents = new byte[fileLength];

        try {
            FileInputStream fileInput = new FileInputStream(directory.getPath() + thisFile.getName());
            fileInput.read(fileContents);
            fileInput.close();
        } catch (IOException e) {
            System.err.println("Unable to read from file with input stream");
            e.printStackTrace();
        }
        return new String(fileContents).trim();
    }
}
