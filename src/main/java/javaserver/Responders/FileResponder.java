package javaserver.Responders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javaserver.App;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class FileResponder implements Responder {

	private String[] supportedMethods;

	public FileResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		return new ResponseBuilder()
		.statusLine(getStatusLine(request))
		.body(getBody(request))
		.build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(request.getMethod())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
	}
	
	private String getBody(Request request) {
		File thisFile = new File(App.publicDirectory.getRoute() + getFileName(request));
		byte[] fileContents = new byte[(int) thisFile.length()];
				
		if (requestIsSupported(request.getMethod())) {
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

	private boolean requestIsSupported(String method) {
		return Arrays.asList(supportedMethods).contains(method);
	}
}
