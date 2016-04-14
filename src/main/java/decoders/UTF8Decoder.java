package decoders;

import java.io.UnsupportedEncodingException;

public class UTF8Decoder {

    public static String decode(String text){
        try {
            String encoding = "UTF-8";
            text = java.net.URLDecoder.decode(text, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("ParameterHandler could not decode one or more of the request's parameters");
        }
        return text;
    }
}
