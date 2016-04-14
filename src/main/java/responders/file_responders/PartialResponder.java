package responders.file_responders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import http_messages.HTTPStatus;
import http_messages.Header;
import http_messages.Request;
import http_messages.RequestHeader;
import http_messages.ResponseHeader;
import javaserver.Directory;

public class PartialResponder extends TextFileResponder {
    private byte[] partialContent;

    public PartialResponder(String[] supportedMethods, Directory dir) {
        super(supportedMethods, dir);
    }

    @Override
    public String getSuccessfulStatusLine() {
        return HTTPStatus.PARTIAL_CONTENT.getStatusLine();
    }

    @Override
    public String getBody(Request request) {
        this.partialContent = readPartialContentsOfFile(request);
        return new String(partialContent);
    }

    @Override
    public Header[] getHeaders(Request request) {
        String headerLine = ResponseHeader.CONTENT_LENGTH.getKeyword() + this.partialContent.length;
        Header contentLength = new Header.HeaderBuilder(headerLine).build();
        return new Header[] {contentLength};
    }


    private byte[] readPartialContentsOfFile(Request request) {
        File thisFile = new File(directory.getPath() + request.getURI());
        int fileLength = (int) thisFile.length();
        byte[] fileContents;
        try {
            fileContents = Files.readAllBytes(thisFile.toPath());
        } catch (IOException e) {
            System.out.println("Unable to read partial.");
            e.printStackTrace();
            fileContents = "".getBytes();
        }

        int[] range = splitRange(request.getHeaderData(RequestHeader.PARTIAL_REQUEST.getKeyword()), fileLength);
        int startOfRange = range[0];
        int endOfRange = range[1];
        return Arrays.copyOfRange(fileContents, startOfRange, endOfRange);
    }

    private int[] splitRange(String requestData, int fileLength) {
        String rawRange = requestData.split("=")[1];
        String[] rangeData = rawRange.trim().split("-");
        return getRange(rangeData, fileLength);
    }

    private int[] getRange(String[] range, int fileLength) {
        int rangeStart = 0;
        int rangeEnd = fileLength;

        if (rangeHasNoEnd(range)) {
            rangeStart = getInt(range, 0);
        } else if (rangeHasNoBeginning(range)) {
            rangeStart = fileLength - getInt(range, 1);
        } else {
            rangeStart = getInt(range, 0);
            rangeEnd = getInt(range, 1) + 1; //add one to make range exclusive
        }

        return new int[] {rangeStart, rangeEnd};
    }

    private boolean rangeHasNoEnd(String[] range) {
        return range.length == 1;
    }

    private boolean rangeHasNoBeginning(String[] range) {
        return range[0].isEmpty();
    }

    private int getInt(String[] range, int index) {
        try {
            return Integer.parseInt(range[index]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid range given in partial request");
            return -1;
        }
    }
}
