package http_messages;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HTMLContentTest {
	private String openingHTML;
	private String closingHTML;

	@Before
	public void setUp() {
		openingHTML = HTMLContent.openHTMLAndBody("Some Page Title");
		closingHTML = HTMLContent.closeBodyAndHTML();
	}

	@Test
	public void pageHasDocType() {
		assertTrue(openingHTML.contains("<!DOCTYPE html>"));
	}

	@Test
	public void pageOpeningHasHTMLTag() {
		assertTrue(openingHTML.contains("<html>"));
	}

	@Test
	public void pageHasHeadTags() {
		assertTrue(openingHTML.contains("<head>") && openingHTML.contains("</head>"));
	}

	@Test
	public void pageHasTitleTags() {
		assertTrue(openingHTML.contains("<title>") && openingHTML.contains("</title>"));
	}

	@Test
	public void pageOpeningHasBodyTag() {
		assertTrue(openingHTML.contains("<body>"));
	}

	@Test
	public void listOfLinksHasProperFormat() {
		String[] linkNames = {"link1", "link2"};
		String listOfLinks = HTMLContent.listOfLinks(linkNames);
		assertTrue(listOfLinks.contains("<ul>") && listOfLinks.contains("</ul>"));

		for (String linkName : linkNames) {
			assertTrue(listOfLinks.contains("<li><a href=\"/" + linkName + "\">") && listOfLinks.contains("</a></li>"));
		}
	}

	@Test
	public void pageClosesBodyTag() {
		assertTrue(closingHTML.contains("</body>"));
	}

	@Test
	public void pageClosesHTMLTag() {
		assertTrue(closingHTML.contains("</html>"));
	}
}
