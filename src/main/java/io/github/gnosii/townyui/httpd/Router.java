package io.github.gnosii.townyui.httpd;

import io.github.gnosii.townyui.httpd.handlers.FallbackHandler;
import io.github.gnosii.townyui.httpd.handlers.ListingHandler;
import io.github.gnosii.townyui.httpd.handlers.ProfileHandler;
import io.undertow.server.RoutingHandler;

public class Router {
    /**
     * Router object.
     */
    RoutingHandler router;

    /**
     * Internal routing handler for the {@link TUIServer}.
     * Handles most (if not all) GET requests made to the Undertow server.
     */
    public Router() {
        router = new RoutingHandler()
        		.get("/test/routertest", FallbackHandler.handleDebugFallback())
                .get("/nations", ListingHandler.handleNationList())
                .get("/residents", ListingHandler.handleResidentList())
                .get("/towns", ListingHandler.handleTownList())
                .get("/nations/{name}", ProfileHandler.handleNationProfile())
                .get("/residents/{name}", ProfileHandler.handleResidentProfile())
                .get("/towns/{name}", ProfileHandler.handleTownProfile())
                .setFallbackHandler(FallbackHandler.handleFallback());
    }

    /**
     * Get the instance of this router.
     * @return routing handler
     */
    public RoutingHandler getRouter() {
        return router;
    }
}
