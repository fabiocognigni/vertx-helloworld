package com.fabero;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;

public class HelloWorldVerticle extends AbstractVerticle {
    final static int PORT = 8080;
    final static String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

    final static String RESP_BODY_SUCCESS = "{\"response\" : \"Hello world!\"}";
    final static String RESP_BODY_ERROR = "{\"response\" : \"Internal Server Error!!!\"}";

    static boolean nastyServer = false;

    @Override
    public void start(Future<Void> fut) throws Exception {

        Router router = Router.router(vertx);

        router.route()
                //interceptor on the request
                .handler(ctx -> {
                    ctx.response().putHeader("Cache-Control", "no-store, no-cache");
                    ctx.next();
                })
                //Default failure handling for all (sub)routes
                .failureHandler(ctx -> {
                    ctx.response()
                            .setStatusCode(500)
                            .putHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON)
                            .end(RESP_BODY_ERROR);
                });

        router.get("/hello")
                .handler(ctx -> {
                    if (nastyServer) {
                        throw new RuntimeException("Testing a failure!!!");
                    } else {
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON)
                                .end(RESP_BODY_SUCCESS);
                    }
                });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(PORT, result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }

}
