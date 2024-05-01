package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class EditInfo {

    EditInfo(String n, double val, double mn, double mx) {
        name = n;
        value = val;
        dimensionless = false;
        minVal = mn;
        maxVal = mx;
    }

    EditInfo(String n, double val) {
        name = n;
        value = val;
        dimensionless = false;
    }

    EditInfo(String n, String txt) {
        name = n;
        text = txt;
    }

    static EditInfo createCheckbox(String name, boolean flag) {
        EditInfo ei = new EditInfo("", 0, -1, -1);
        ei.checkbox = new Checkbox(name, flag);
        return ei;
    }

    EditInfo setDimensionless() {
        dimensionless = true;
        return this;
    }

    EditInfo disallowSliders() {
        noSliders = true;
        return this;
    }

    int changeFlag(int flags, int bit) {
        if (checkbox.getState()) return flags | bit;
        return flags & ~bit;
    }

    int changeFlagInverted(int flags, int bit) {
        if (!checkbox.getState()) return flags | bit;
        return flags & ~bit;
    }

    String name, text;
    double value;
    TextBox textf;
    Choice choice;
    Checkbox checkbox;
    Button button;
    EditDialogLoadFile loadFile = null; //if non-null, the button will open a file dialog
    TextArea textArea;
    Widget widget;
    boolean newDialog;
    boolean dimensionless;
    boolean noSliders;
    double minVal, maxVal;

    // for slider dialog
    TextBox minBox, maxBox, labelBox;

    boolean canCreateAdjustable() {
        return choice == null && checkbox == null && button == null && textArea == null && widget == null && !noSliders;
    }

    static String makeLink(String file, String text) {
        return "<a href=\"" + file + "\" target=\"_blank\">" + Locale.LS(text) + "</a>";
    }
}
