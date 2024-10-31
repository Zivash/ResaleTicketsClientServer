package com.example.client.model;

import java.util.Map;

public class Request {
    private final String action;
    private String description;
    private Map<String, Object> body;

    public Request(String action) {
        this.action = action;
    }

    public Request(String action, Map<String, Object> body) {
        this.action = action;
        this.body = body;
    }

    public Request(String action, String description) {
        this.action = action;
        this.description = description;
    }
}