package io.github.gnosii.townyui.httpd.handlers;

import com.google.gson.JsonObject;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import io.github.gnosii.townyui.TUIMain;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.PathTemplateMatch;
import io.github.gnosii.townyui.httpd.error.Error;

public class ProfileHandler {
	
	/**
	 * Handle resident profile requests.
	 * @return {@link HttpServerExchange}
	 */
    public static HttpHandler handleResidentProfile() {
        return (HttpServerExchange exchange) -> {
            PathTemplateMatch pathMatch = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
            String name = pathMatch.getParameters().get("name");

            if (name == null) {
                Commons.handleError(exchange, Error.MISSING_PARAMETER);
                return;
            }

            Resident res = TUIMain.getApi().getResident(name);

            if (res != null) {
                JsonObject residentJson = new JsonObject();
                residentJson.addProperty("name", name);
                residentJson.addProperty("id", res.getUUID().toString());
                residentJson.addProperty("formattedName", res.getFormattedName());
                residentJson.addProperty("hasTown", res.hasTown());
                residentJson.addProperty("isNPC", res.isNPC());
                residentJson.addProperty("isTownyAdmin", TUIMain.getUniverse().getPermissionSource().isTownyAdmin(res.getPlayer()));
                if (res.hasTown()) {
                    residentJson.addProperty("town", res.getTown().getName());
                    residentJson.addProperty("townRanks", String.valueOf(res.getTownRanks()));
                    residentJson.addProperty("nationRanks", String.valueOf(res.getNationRanks()));
                } else {
                    residentJson.addProperty("town", "null");
                    residentJson.addProperty("townRanks", "null");
                    residentJson.addProperty("nationRanks", "null");
                }
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
                exchange.getResponseSender().send(TUIMain.buildJson(residentJson));
            } else {
                Commons.handleError(exchange, Error.NOT_FOUND_CHILD);
            }
        };
    }

    /**
	 * Handle nation profile requests.
	 * @return {@link HttpServerExchange}
	 */
    public static HttpHandler handleNationProfile() {
        return (HttpServerExchange exchange) -> {
            PathTemplateMatch pathMatch = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
            String name = pathMatch.getParameters().get("name");

            if (name == null) {
                Commons.handleError(exchange, Error.MISSING_PARAMETER);
                return;
            }

            Nation nation = TUIMain.getApi().getNation(name);

            if (nation != null) {
                JsonObject nationJson = new JsonObject();
                nationJson.addProperty("name", name);
                nationJson.addProperty("formattedName", nation.getFormattedName());
                nationJson.addProperty("capital", nation.getCapital().getName());
                nationJson.addProperty("king", nation.getKing().getName());
                nationJson.addProperty("towns", String.valueOf(nation.getTowns()));

                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
                exchange.getResponseSender().send(TUIMain.buildJson(nationJson));
            } else {
                Commons.handleError(exchange, Error.NOT_FOUND_CHILD);
            }
        };
    }

    /**
	 * Handle town profile requests.
	 * @return {@link HttpServerExchange}
	 */
    public static HttpHandler handleTownProfile() {
        return (HttpServerExchange exchange) -> {
            PathTemplateMatch pathMatch = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
            String name = pathMatch.getParameters().get("name");

            if (name == null) {
                Commons.handleError(exchange, Error.MISSING_PARAMETER);
                return;
            }

            Town town = TUIMain.getApi().getTown(name);

            if (town != null) {
            	try {
            		JsonObject nationJson = new JsonObject();
            		
            		nationJson.addProperty("name", name);
            		nationJson.addProperty("formattedName", town.getFormattedName());
            		if (town.hasNation())
            			nationJson.addProperty("nation", town.getNation().getName());
            		else
            			nationJson.addProperty("nation", "null");
            		nationJson.addProperty("mayor", town.getMayor().getName());
            		nationJson.addProperty("assistants", String.valueOf(town.getRank("assistant")));

            		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
            		exchange.getResponseSender().send(TUIMain.buildJson(nationJson));
            	} catch (NullPointerException npe) {
            		Commons.handleError(exchange, Error.INTERNAL_NPE);
            	}
            } else {
                Commons.handleError(exchange, Error.NOT_FOUND_CHILD);
            }
        };
    }
}
