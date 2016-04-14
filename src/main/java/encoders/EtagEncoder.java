package encoders;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class EtagEncoder {

    public static String encode(String text) {
        byte[] content = text.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(content);
            return getHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could not encode file contents to SHA-1");
        }
        return null;
    }

    private static String getHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String hex = formatter.toString();
        formatter.close();
        return hex;
    }
}
