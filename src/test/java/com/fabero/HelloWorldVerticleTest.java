package com.fabero;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class HelloWorldVerticleTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(HelloWorldVerticle.class.getName(),
                context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testSuccessResponse(TestContext context) {
        final Async async = context.async();
        HelloWorldVerticle.nastyServer = false;
        vertx.createHttpClient().getNow(HelloWorldVerticle.PORT, "localhost", "/hello",
                response -> {
                    context.assertEquals(200, response.statusCode());
                    response.handler(body -> {
                        context.assertEquals(HelloWorldVerticle.RESP_BODY_SUCCESS, body.toString());
                        async.complete();
                    });
                });
    }

    @Test
    public void testErrorResponse(TestContext context) {
        final Async async = context.async();
        HelloWorldVerticle.nastyServer = true;
        vertx.createHttpClient().getNow(HelloWorldVerticle.PORT, "localhost", "/hello",
                response -> {
                    context.assertEquals(500, response.statusCode());
                    response.handler(body -> {
                        context.assertEquals(HelloWorldVerticle.RESP_BODY_ERROR, body.toString());
                        async.complete();
                    });
                });
    }
}
