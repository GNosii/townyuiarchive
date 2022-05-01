package io.github.gnosii.townyui;

import java.util.List;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import io.github.gnosii.townyui.httpd.TUIServer;
import io.github.gnosii.townyui.config.Config;

public class TUIMain extends JavaPlugin {

    private static TUIMain instance;
    private static Config config;

    private static TownyAPI apiInstance;
    private static TownyUniverse universeInstance;

    private static Gson gson;

    private TUIServer server;

    @Override
    public void onEnable() {
        instance = this;
        config = new Config(this);

        config.createDefaultConfig();

        apiInstance = TownyAPI.getInstance();
        universeInstance = TownyUniverse.getInstance();

        gson = new Gson();

        new Metrics(this, 13547);

        server = new TUIServer(config.getHost(), config.getPort());

        try {
            server.start();
        } catch (IllegalStateException e) {
            this.getLogger().severe("Could not start internal web server: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {

        try {
            server.stop();
        } catch(IllegalStateException e) {
            this.getLogger().severe("Could not stop internal web server: " + e.getMessage());
        }

        server = null;
    }

    /**
     * Get the instance of TownyUI currently active.
     * @return TownyUI instance
     */
    public static TUIMain getInstance() {
        return instance;
    }

    /**
     * Get the instance of TownyUniverse currently active.
     * @return TownyUniverse instance
     */
    public static TownyUniverse getUniverse() {
        return universeInstance;
    }

    /**
     * Get the instance of the TownyAPI currently active.
     * @return TownyAPI instance
     */
    public static TownyAPI getApi() {
        return apiInstance;
    }

    /**
     * Get the instance of the configuration currently active.
     * Typo is intentional as function already exists: {@link JavaPlugin#getConfig()}
     * @return Config instance
     */
    public static Config getConfigg() {
        return config;
    }
    
    /**
     * Build an JSON object. Meant for building final JSON objects.
     * @param initialObject Object to build
     * @return valid JSON string
     */
    public static String buildJson(JsonObject initialObject) {
        return gson.toJson(initialObject);
    }
    
    /**
     * Build an JSON string from an {@link List}.
     * @param list list to build
     * @return valid JSON string
     */
    public static JsonArray buildList(List<String> list) {
    	return gson.toJsonTree(list).getAsJsonArray();
    }
}
