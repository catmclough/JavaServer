package responders.file_responders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import encoders.EtagEncoder;
import http_messages.HTTPStatus;
import http_messages.Header;
import http_messages.Request;
import http_messages.RequestHeader;
import io.FileReader;
import javaserver.Directory;

public class PatchResponder extends TextFileResponder {

    public PatchResponder(String[] supportedMethods, Directory publicDir) {
        super(supportedMethods, publicDir);
    }

    @Override
    public String getSuccessfulStatusLine() {
        return HTTPStatus.NO_CONTENT.getStatusLine();
    }

    @Override
    public Header[] getHeaders(Request request) {
        return null;
    }

    @Override
    public String getBody(Request request) {
        return getUpdatedFileContents(request);
    }

    @Override
    public byte[] getBodyData(Request request, FileReader reader) {
       return null;
    }

    private String getUpdatedFileContents(Request request) {
        if (etagMatchesFileContent(request)) {
            writePatchContentToFile(request);
        }
        return getFileContents(request);
    }

    protected boolean etagMatchesFileContent(Request request) {
        String encodedFileContent = EtagEncoder.encode(getFileContents(request));
        String etag = request.getHeaderData(RequestHeader.ETAG.getKeyword());
        if (etag != null) {
            return (etag.equals(encodedFileContent));
        } else {
            return false;
        }
    }

    private void writePatchContentToFile(Request request) {
        File file = new File(directory.getPath() + request.getURI());
        try {
            Files.write(file.toPath(), request.getData().getBytes());
        } catch (IOException e) {
            System.out.println("Could not write patched content to file.");
            e.printStackTrace();
        }
    }
}
