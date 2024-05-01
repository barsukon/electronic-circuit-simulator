package ru.barsukon.ecs.client.util;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import java.util.HashMap;

public class Locale {

    public static HashMap<String, String> localizationMap;

    public static String ohmString = "\u03a9";
    public static String muString = "\u03bc";

    public static String LS(String s) {
        if (s == null) return null;

        if (s.length() == 0) { // empty strings trip up the 'if (ix != s.length() - 1)' below
            return s;
        }

        String sm = localizationMap.get(s);
        if (sm != null) return sm;

        // use trailing ~ to differentiate strings that are the same in English but need
        // different translations.
        // remove these if there's no translation.
        int ix = s.indexOf('~');
        if (ix != s.length() - 1) return s;

        s = s.substring(0, ix);
        sm = localizationMap.get(s);
        if (sm != null) return sm;

        return s;
    }

    public static SafeHtml LSHTML(String s) {
        return SafeHtmlUtils.fromTrustedString(LS(s));
    }
}
