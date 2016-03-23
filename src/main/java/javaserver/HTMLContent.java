package javaserver;

public class HTMLContent {

	public static String listOfLinks(String[] list) {
		String unorderedList = "<ul>" + System.lineSeparator();
		for (String listItem : list) {
			unorderedList += "<li><a href=\"/" + listItem + "\">" + listItem + "</a></li>" + System.lineSeparator();
		}
		unorderedList += "</ul>" + System.lineSeparator();
		return unorderedList;
	}
}
