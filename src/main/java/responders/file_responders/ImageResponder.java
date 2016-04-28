package responders.file_responders;

import java.io.File;
import java.util.Arrays;

import http_messages.HTTPStatus;
import http_messages.Header;
import http_messages.Request;
import http_messages.ResponseHeader;
import io.FileReader;
import javaserver.Directory;
import text_parsers.RequestParser;

public class ImageResponder extends FileResponder {
    private String[] acceptedImageFormats = new String[] {"jpeg", "gif", "png"};
    protected byte[] imageData;

    public ImageResponder(String[] supportedMethods, Directory directory) {
        super(supportedMethods, directory);
    }

    @Override
    public String getSuccessfulStatusLine(Request request) {
        return HTTPStatus.OK.getStatusLine();
    }

    @Override
    public String getBody(Request request) {
        return null;
    }

    @Override
    public byte[] getBodyData(Request request, FileReader fileReader) {
        File thisFile = new File(directory.getPath() + request.getURI());
        this.imageData = fileReader.readBytes(thisFile);
        return imageData;
    }

    @Override
    public Header[] getHeaders(Request request) {
        Header headerOne = new Header.HeaderBuilder(ResponseHeader.CONTENT_LENGTH.getKeyword() + imageData.length).build();
        Header headerTwo = new Header.HeaderBuilder(ResponseHeader.CONTENT_TYPE.getKeyword() + "image/" + getImageFormat(request)).build();
        return new Header[] {headerOne, headerTwo};
    }

    private String getImageFormat(Request request) {
        String format = RequestParser.getImageFormat(request);
        if (!Arrays.asList(acceptedImageFormats).contains(format)) {
            System.err.println("Unsupported image format.");
        }
        return format;
    }
}