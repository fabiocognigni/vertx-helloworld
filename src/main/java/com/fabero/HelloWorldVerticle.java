package com.fabero;

import io.vertx.core.AbstractVerticle;

public class HelloWorldVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            request.response().end("Hello world!");
        }).listen(8080);
    }

}
