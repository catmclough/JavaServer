package javaserver;

import java.util.ArrayList;

public class RequestLog {
    private static RequestLog instance = null;
    private static ArrayList<String> log = new ArrayList<String>();

    public static RequestLog getInstance() {
        if (instance == null) {
            instance = new RequestLog();
            log = new ArrayList<String>();
        }
        return instance;
    }

    public void addRequest(String request) {
        log.add(request);
    }

    public String getLogContents() {
        return String.join(System.lineSeparator(), log);
    }

    public void clearLog() {
        log.clear();
    }
}
