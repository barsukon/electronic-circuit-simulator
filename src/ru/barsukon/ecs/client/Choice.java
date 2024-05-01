package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.user.client.ui.ListBox;

public class Choice extends ListBox {

    Choice() {
        super();
    }

    public void add(String s) {
        this.addItem(Locale.LS(s));
    }

    public void select(int i) {
        this.setSelectedIndex(i);
    }
}
