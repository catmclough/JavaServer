package http_messages;

import static org.junit.Assert.*;

import org.junit.Test;

public class HeaderTest {
    private String rangeHeader = "Range: bytes=10-11";
    private Header exampleHeader = new Header.HeaderBuilder(rangeHeader).build();

    @Test
    public void headerHasKeyWord() {
        assertEquals(exampleHeader.getKeyWord(), "Range");
    }

    @Test
    public void headerHasData() {
        assertEquals(exampleHeader.getData(), "bytes=10-11");
    }

    @Test
    public void formatsKeyWordAndDataAsRequestLine() {
       assertEquals(exampleHeader.getLine(), rangeHeader);
    }
}
