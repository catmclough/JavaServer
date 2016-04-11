package javaserver;

import java.util.ArrayList;

public class RequestLog {
    public ArrayList<String> log;

    public RequestLog() {
        this.log = new ArrayList<String>();
    }

    public void addRequest(String request) {
        log.add(request);
    }
}
