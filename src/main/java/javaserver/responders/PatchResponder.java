package javaserver.responders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
//import java.util.Formatter;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;

public class PatchResponder extends FileResponder {

	public PatchResponder(String[] supportedMethods, File publicDir) {
		super(supportedMethods, publicDir);
	}

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder(getStatusLine(request))
          .body(getFileContents(request))
          .build();
    }

    @Override
    public String getStatusLine(Request request) {
		if (requestIsSupported(supportedMethods, request.getMethod())) {
            return HTTPStatusCode.TWO_OH_FOUR.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
    }

    private String getFileContents(Request request) {
       if (etagMatchesFileContent(request))
           return patchContentWrittenToFile(request);
       return getBody(request);
    }

    protected boolean etagMatchesFileContent(Request request) {
        String encodedSHA1FileContent = encode(getBody(request).getBytes());
        String etag = request.getHeaders().get("If-Match");
        return (etag.equals(encodedSHA1FileContent));
    }
    
    private String encode(byte[] content) {
        java.security.MessageDigest d = null;
        try {
            d = java.security.MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        d.update(content);
        return encodeToString(d.digest());
      }
    
    private String encodeToString(byte[] hash) {
        StringBuilder hexHash = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            hexHash.append(String.format("%02x", b));
        }
        return hexHash.toString();
    }
    
    private String patchContentWrittenToFile(Request request) {
        File file = new File(directory + request.getURI());
        try {
            Files.write(file.toPath(), request.getData().getBytes());
        } catch (IOException e) {
            System.out.println("Could not write patched content to file.");
            e.printStackTrace();
        }
        return request.getData();
    }
}