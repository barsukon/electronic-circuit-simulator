package ru.barsukon.ecs.client;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;

public class CheckboxAlignedMenuItem extends MenuItem {

    public CheckboxAlignedMenuItem(String s, Command cmd) {
        super(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml + "&nbsp;</div>" + s), cmd);
    }
}
