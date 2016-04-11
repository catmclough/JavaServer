package javaserver.responders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;

public class FileResponder implements Responder {
	protected String[] supportedMethods;
	protected File directory;

	public FileResponder(String[] supportedMethods, File publicDir) {
		this.supportedMethods = supportedMethods;
		this.directory = publicDir;
	}

	@Override
	public Response getResponse(Request request) {
		if (request.getHeaders().containsKey("Range")) {
			PartialResponder partialResponder = new PartialResponder(supportedMethods, directory);
			return partialResponder.getResponse(request);
		} else if (request.getMethod().equals("PATCH")) {
		    PatchResponder patchResponder = new PatchResponder(supportedMethods, directory);
		    return patchResponder.getResponse(request);
		} else {
			return new Response.ResponseBuilder(getStatusLine(request))
              .body(getBody(request))
              .build();
		}
	}

	protected String getBody(Request request) {
        File thisFile = new File(directory + request.getURI());
        int fileLength = (int) thisFile.length();
        byte[] fileContents = new byte[fileLength];

        if (requestIsSupported(supportedMethods, request.getMethod())) {
            try {
                FileInputStream fileInput = new FileInputStream(thisFile);
                fileInput.read(fileContents);
                fileInput.close();
            } catch (IOException e) {
                System.out.println("Unable to read from file with input stream");
                e.printStackTrace();
            }
        }
        return new String(fileContents);
    }

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(supportedMethods, request.getMethod())) {
            return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
	}

	public String getFileName(Request request) {
		return request.getURI().substring(1);
	}
}
