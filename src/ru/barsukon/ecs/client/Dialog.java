package ru.barsukon.ecs.client;

import com.google.gwt.user.client.ui.DialogBox;

class Dialog extends DialogBox {

    boolean closeOnEnter;

    Dialog() {
        closeOnEnter = true;
    }

    public void closeDialog() {
        hide();
        if (CirSim.dialogShowing == this) CirSim.dialogShowing = null;
    }

    public void enterPressed() {
        if (closeOnEnter) {
            apply();
            closeDialog();
        }
    }

    void apply() {}
}
