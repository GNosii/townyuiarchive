package io.github.gnosii.townyui.httpd;

import io.github.gnosii.townyui.TUIMain;
import io.undertow.Undertow;

public class TUIServer {
    private Undertow server;

    private int port;
    private String host;
    
    /**
     * Internal server using Undertow under the cover.
     * Used in TownyUI for serving the internal web API.
     */
    public TUIServer(String host, int port) {
    	this.port = port;
    	this.host = host;
    	
        this.server = Undertow.builder()
                .addHttpListener(this.port, this.host, new Router().getRouter())
                .build();
    }

    /**
     * Start the server
     * @throws IllegalStateException If the server is null
     */
    public void start() throws IllegalStateException {
        TUIMain.getInstance().getLogger().info("Trying to start internal API server.");
        
        if (server == null)
            throw new IllegalStateException("Internal server is null.");

        server.start();
        
        TUIMain.getInstance().getLogger().info("Server is running at " + this.host + ":" + this.port);
        TUIMain.getInstance().getLogger().info("Started internal API server!");
    }

    /**
     * Stop the server.
     * @throws IllegalStateException If the server is null
     */
    public void stop() throws IllegalStateException {
        if (server == null)
            throw new IllegalStateException("Internal server is null");

        server.stop();
        
        server = null;
        TUIMain.getInstance().getLogger().info("Stopped internal API server.");
    }
}
