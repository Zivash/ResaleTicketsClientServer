package server;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private final List<T> body;

    public Response(List<T> body) {
        this.body = body;
    }

    public Response(T object) {
        this.body = new ArrayList<>();
        this.body.add(object);
    }

    public List<T> getBody() {
        return body;
    }
}