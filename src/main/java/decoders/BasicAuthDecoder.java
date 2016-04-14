package decoders;

import java.util.Base64;

public class BasicAuthDecoder {

    public static String decode(String codedString) {
        byte[] decodedText = Base64.getMimeDecoder().decode(codedString.getBytes());
        return  new String(decodedText);
    }
}
