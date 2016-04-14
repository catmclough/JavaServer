package text_parsers;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Test;

public class ParameterParserTest {
        String codedRawRequest = "POST /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
        String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String decodedParamTwo = "variable_2 = stuff";
        String[] decodedParams = new String[] {decodedParamOne, decodedParamTwo};

    @Test
    public void canRemoveParamsAndReturnSimpleURI() {
        String uriWithParams = "/parameters?foo=bar?beep=bop";
        String uriWithoutParams = "/parameters";
        assertEquals(ParameterParser.getDecodedURIWithoutParams(uriWithParams), uriWithoutParams);
    }

    @Test
    public void getsDecodedURIWhenRemovingParameters() {
       String codedURIWithParams = "/file%20one.txt/foo?var_1=bar";
       String decodedURIWithoutParams = "/file one.txt/foo";
       assertEquals(ParameterParser.getDecodedURIWithoutParams(codedURIWithParams), decodedURIWithoutParams);
    }

    @Test
    public void separatesDecodedParametersByVariable() {
        assertTrue(Arrays.equals(decodedParams, ParameterParser.getDecodedParams(codedRawRequest)));
    }

    @Test
    public void getsFormatterParamsForResponseBody() {
        String formattedBody = "";
        for (String paramVar : decodedParams) {
           formattedBody += paramVar + System.lineSeparator();
        }
        assertEquals(formattedBody, ParameterParser.getParamBody(decodedParams));
    }
}
