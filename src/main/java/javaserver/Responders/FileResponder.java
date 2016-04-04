package javaserver.Responders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javaserver.App;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;

public class FileResponder implements Responder {

	private String[] supportedMethods;

	public FileResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		return new Response.ResponseBuilder(getStatusLine(request))
      .body(getBody(request))
      .build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(supportedMethods, request.getMethod())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
	}

	private String getBody(Request request) {
		File thisFile = new File(App.getPublicDirectory().getRoute() + getFileName(request));
		byte[] fileContents = new byte[(int) thisFile.length()];

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

	private String getFileName(Request request) {
		return request.getURI().substring(1);
	}
}
