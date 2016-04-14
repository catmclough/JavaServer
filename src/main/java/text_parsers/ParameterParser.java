package text_parsers;

import java.util.ArrayList;

import decoders.UTF8Decoder;

public class ParameterParser {

    public static String getDecodedURIWithoutParams(String uri) {
        String decodedURI = UTF8Decoder.decode(uri);
        if (requestHasParams(decodedURI)) {
            String[] routeParts = decodedURI.split("\\?", 2);
            return decodedURI = routeParts[0];
        }
        return decodedURI;
    }

    public static String[] getDecodedParams(String rawRequest) {
        String codedURI = RequestParser.getRequestURI(rawRequest);
        ArrayList<String> decodedParams = new ArrayList<String>();
        if (requestHasParams(codedURI)) {
            String codedParamLine = codedURI.split("\\?", 2)[1];
            String[] codedParamVars = codedParamLine.split("&");
            for (String var : codedParamVars) {
                decodedParams.add(UTF8Decoder.decode(var.replace("=", " = ")));
            }
        }
        String[] decodedParameters = new String[decodedParams.size()];
        return decodedParams.toArray(decodedParameters);
    }

    private static boolean requestHasParams(String uri) {
        return uri.contains("?");
    }

    public static String getParamBody(String[] params) {
        String parameters = "";
        for (String parameterVar : params) {
            parameters += parameterVar + System.lineSeparator();
        }
        return parameters;
    }
}
