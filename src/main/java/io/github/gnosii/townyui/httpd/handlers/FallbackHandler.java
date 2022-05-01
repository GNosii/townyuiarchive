package io.github.gnosii.townyui.httpd.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.github.gnosii.townyui.httpd.error.Error;

public class FallbackHandler {

    /**
     * Handle 404 errors.
     * @return exchange
     */
    public static HttpHandler handleFallback() {
        return (HttpServerExchange exchange) -> {
            Commons.handleError(exchange, Error.NOT_FOUND_ENDPOINT);
        };
    }
    
    /**
     * Handle test requests.
     * @return exchange
     */
    public static HttpHandler handleDebugFallback() {
    	return (HttpServerExchange exchange) -> {
    		Commons.handleError(exchange, Error.TEAPOT);
    	};
    }
}
