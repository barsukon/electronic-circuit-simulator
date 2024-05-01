package ru.barsukon.ecs.client;

import com.google.gwt.http.client.URL;
import java.util.HashMap;

public class QueryParameters {

    private HashMap<String, String> map = new HashMap<String, String>();

    public QueryParameters() {
        String search = getQueryString();
        if ((search != null) && (search.length() > 0)) {
            String[] nameValues = search.substring(1).split("&");
            for (int i = 0; i < nameValues.length; i++) {
                String[] pair = nameValues[i].split("=");

                map.put(pair[0], URL.decode(pair[1]));
            }
        }
    }

    public String getValue(String key) {
        return (String) map.get(key);
    }

    public boolean getBooleanValue(String key, boolean def) {
        String val = getValue(key);
        if (val == null) return def;
        else return (val == "1" || val.equalsIgnoreCase("true"));
    }

    private native String getQueryString() /*-{
          return $wnd.location.search;
    }-*/;
}
