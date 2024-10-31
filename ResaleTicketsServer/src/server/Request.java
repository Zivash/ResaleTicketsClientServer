package server;

import java.util.Map;

public class Request {
    private String action;
    private String description;
    private Map<String, Object> body;

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Object> getBody() {
        return body;
    }
}