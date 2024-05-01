package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.user.client.ui.CheckBox;

class Checkbox extends CheckBox {

    public Checkbox(String s) {
        super(Locale.LS(s));
    }

    public Checkbox(String s, boolean b) {
        super(Locale.LS(s));
        this.setValue(b);
    }

    public boolean getState() {
        return this.getValue();
    }

    public void setState(boolean s) {
        this.setValue(s);
    }
}
