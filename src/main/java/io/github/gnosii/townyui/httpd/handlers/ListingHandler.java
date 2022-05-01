package io.github.gnosii.townyui.httpd.handlers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.palmergames.bukkit.towny.object.TownyObject;

import io.github.gnosii.townyui.TUIMain;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class ListingHandler {
	
	/**
     * Handle resident list request.
     * @return {@link HttpServerExchange}
     */
    public static HttpHandler handleResidentList() {
        return (HttpServerExchange exchange) -> {
        	List<String> residents = new ArrayList<>();
        	
        	TUIMain.getUniverse().getResidents().forEach((resident) -> {
        		residents.add(buildChild(resident));
        	});
        	
        	JsonObject list = new JsonObject();
        	list.add("list", TUIMain.buildList(residents));
        	
        	exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
        	exchange.getResponseSender().send(TUIMain.buildJson(list));
        };
    }

    /**
     * Handle nation list request.
     * @return {@link HttpServerExchange}
     */
    public static HttpHandler handleNationList() {
        return (HttpServerExchange exchange) -> {
            List<String> nations = new ArrayList<>();
            
            TUIMain.getUniverse().getNations().forEach((nation) -> {
            	nations.add(buildChild(nation));
            });
            
        	JsonObject list = new JsonObject();
        	list.add("list", TUIMain.buildList(nations));
        	
        	exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
        	exchange.getResponseSender().send(TUIMain.buildJson(list));
        };
    }

    /**
     * Handle town list request.
     * @return {@link HttpServerExchange}
     */
    public static HttpHandler handleTownList() {
        return (HttpServerExchange exchange) -> {
        	List<String> towns = new ArrayList<>();
        	
        	TUIMain.getUniverse().getTowns().forEach((town) -> {
        		towns.add(buildChild(town));
        	});
        	
        	JsonObject list = new JsonObject();
        	list.add("list", TUIMain.buildList(towns));
        	
        	exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
        	exchange.getResponseSender().send(TUIMain.buildJson(list));
        };
    }
    
    /**
     * Build an list child.
     * @param object TownyObject
     * @return valid json object
     */
    private static String buildChild(TownyObject object) {
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty("name", object.getName());
    	return TUIMain.buildJson(jsonObject);
    }
}
