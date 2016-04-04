package javaserver;

import java.io.File;

public class PublicDirectory {

	private String route;

	public PublicDirectory(String route) {
		this.route = route;
	}

	public String[] getDirectoryListing() {
		File directory = new File(getDirectoryName());
		return directory.list();
	}

	public String getRoute() {
		if (route.startsWith("/")) {
			route = route.substring(1);
		}

		if (route.endsWith("/")) {
			return route;
		} else {
			return route + "/";
		}
	}

	public String getDirectoryName() {
		if (route.startsWith("/")) {
			return route.substring(1);
		} else {
			return route;
		}
	}
}
