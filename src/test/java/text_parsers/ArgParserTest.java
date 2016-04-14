package text_parsers;

import static org.junit.Assert.*;
import org.junit.Test;
import text_parsers.ArgParser;

public class ArgParserTest {
    private int defaultPort = 5623;
    private String defaultPubDirectory = "cool_directory/";

    @Test
    public void testGetsChosenPort() {
        String[] args = new String[] {"-D", "zoobeezoo", "-P", "1234"};
        assertEquals(ArgParser.getPortChoice(args, defaultPort), 1234);
    }

    @Test
    public void testReturnsDefaultPortWithNoArgs() {
        String[] args = new String[] {};
        assertEquals(ArgParser.getPortChoice(args, defaultPort), defaultPort);
    }

    @Test
    public void testReturnsDefaultPortWithInvalidArgs() {
        String[] args = new String[] {"-P", "zoobeezoo"};
        assertEquals(ArgParser.getPortChoice(args, defaultPort), defaultPort);
    }

    @Test
    public void testGetsChosenDirectory() {
        String[] args = new String[] {"-D", "zoobeezoo/", "-P", "1234"};
        assertEquals(ArgParser.getDirectoryChoice(args, defaultPubDirectory), "zoobeezoo/");
    }

    @Test
    public void testGetsHandlesIncorrectFormatOfChosenDirectory() {
        String[] args = new String[] {"-D", "/public", "-P", "1234"};
        assertEquals(ArgParser.getDirectoryChoice(args, defaultPubDirectory), "public");
    }

    @Test
    public void testReturnsDefaultDirectoryWithNoArgs() {
        String[] args = new String[] {};
        assertEquals(ArgParser.getDirectoryChoice(args, defaultPubDirectory), defaultPubDirectory);
    }

    @Test
    public void testReturnsDefaultDirectoryWithInvalidArgs() {
        String[] args = new String[] {"-D"};
        assertEquals(ArgParser.getDirectoryChoice(args, defaultPubDirectory), defaultPubDirectory);
    }
}
