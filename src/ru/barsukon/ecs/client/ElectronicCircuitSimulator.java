package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

import java.util.HashMap;

public class ElectronicCircuitSimulator implements EntryPoint {

    public static final String versionString = "0.1.0";

    static CirSim mysim;

    // This is the program entrypoint!
    // Called by gtw automagically (see ecs.gwt.xml)
    public void onModuleLoad() {
        // loadLocale() launches the sim after determining the language (see below)
        loadLocale();
    }

    native String language() /*-{
        if (navigator.languages) {
            if (navigator.languages.length > 0) {
                return navigator.languages[0];
            } else {
                // In Electron, navigator.languages returns an empty array
                return "en-US";
            }
        } else {
            return (navigator.language || navigator.userLanguage);  
        }
    }-*/;

    void loadLocale() {
        String url;
        QueryParameters qp = new QueryParameters();
        String lang = qp.getValue("lang");
        if (lang == null) {
            Storage stor = Storage.getLocalStorageIfSupported();
            if (stor != null) lang = stor.getItem("language");
            if (lang == null) lang = language();
        }

        GWT.log("got language " + lang);

        lang = lang.replaceFirst("-.*", "");

        if (lang.startsWith("en")) {
            // no need to load locale file for English
            HashMap<String, String> localizationMap = new HashMap<String, String>();
            loadSimulator(localizationMap);
            return;
        }

        url = GWT.getModuleBaseURL() + "locale_" + lang + ".txt";
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
        try {
            requestBuilder.sendRequest(
                null,
                new RequestCallback() {
                    public void onError(Request request, Throwable exception) {
                        GWT.log("File Error Response", exception);
                    }

                    public void onResponseReceived(Request request, Response response) {
                        HashMap<String, String> localizationMap;
                        if (response.getStatusCode() == Response.SC_OK) {
                            String text = response.getText();
                            localizationMap = processLocale(text);
                        } else {
                            GWT.log("Bad file server response: " + response.getStatusText());
                            // if there was an error in retrieving the
                            // language, default to English (empty map)
                            localizationMap = new HashMap<String, String>();
                        }
                        loadSimulator(localizationMap);
                    }
                }
            );
        } catch (RequestException e) {
            GWT.log("failed file reading", e);
        }
    }

    HashMap<String, String> processLocale(String data) {
        HashMap<String, String> localizationMap = new HashMap<String, String>();
        String lines[] = data.split("\r?\n");
        for (int i = 0; i != lines.length; i++) {
            String line = lines[i];
            if (line.length() == 0) continue;
            if (line.charAt(0) != '"') {
                CirSim.console("ignoring line in string catalog: " + line);
                continue;
            }
            int q2 = line.indexOf('"', 1);
            if ((q2 < 0) || (line.charAt(q2 + 1) != '=') || (line.charAt(q2 + 2) != '"') || (line.charAt(line.length() - 1) != '"')) {
                CirSim.console("ignoring line in string catalog: " + line);
                continue;
            }
            String str1 = line.substring(1, q2);
            String str2 = line.substring(q2 + 3, line.length() - 1);
            localizationMap.put(str1, str2);
        }
        return localizationMap;
    }

    public void loadSimulator(HashMap<String, String> localizationMap) {
        Locale.localizationMap = localizationMap;
        mysim = new CirSim();
        mysim.init();

        Window.addResizeHandler(
            new ResizeHandler() {
                public void onResize(ResizeEvent event) {
                    mysim.setCanvasSize();
                    mysim.setiFrameHeight();
                }
            }
        );

        mysim.updateCircuit();
    }
}
