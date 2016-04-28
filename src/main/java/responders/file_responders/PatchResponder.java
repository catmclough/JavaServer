package responders.file_responders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import encoders.EtagEncoder;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.RequestHeader;

public class PatchResponder {

    public static String getSuccessfulStatusLine() {
        return HTTPStatus.NO_CONTENT.getStatusLine();
    }

    public static void updateFileContents(Request request, File file, String fileContent) {
        if (etagMatchesFileContent(request, fileContent)) {
            writePatchContentToFile(request, file);
        }
    }

    protected static boolean etagMatchesFileContent(Request request, String content) {
        String encodedFileContent = EtagEncoder.encode(content.getBytes());
       
        String etag = request.getHeaderData(RequestHeader.ETAG.getKeyword());
        if (etag != null) {
            return (etag.equals(encodedFileContent));
        } else {
            return false;
        }
    }

    private static void writePatchContentToFile(Request request, File file) {
        try {
            Files.write(file.toPath(), request.getData().getBytes());
        } catch (IOException e) {
            System.out.println("Could not write patched content to file.");
            e.printStackTrace();
        }
    }
}