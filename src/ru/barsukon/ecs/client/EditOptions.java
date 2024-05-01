package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

class EditOptions implements Editable {

    CirSim sim;

    public EditOptions(CirSim s) {
        sim = s;
    }

    public EditInfo getEditInfo(int n) {
        if (n == 0) return new EditInfo("Time step size (s)", sim.maxTimeStep, 0, 0);
        if (n == 1) return new EditInfo("Range for voltage color (V)", CircuitElm.voltageRange, 0, 0);
        if (n == 2) {
            EditInfo ei = new EditInfo("Change Language", 0, -1, -1);
            ei.choice = new Choice();
            ei.choice.add("(no change)");
            ei.choice.add("English");
            ei.choice.add("\u0420\u0443\u0441\u0441\u043a\u0438\u0439"); // Russian
            return ei;
        }

        if (n == 3) return new EditInfo("Positive Color", CircuitElm.positiveColor.getHexValue());
        if (n == 4) return new EditInfo("Negative Color", CircuitElm.negativeColor.getHexValue());
        if (n == 5) return new EditInfo("Neutral Color", CircuitElm.neutralColor.getHexValue());
        if (n == 6) return new EditInfo("Selection Color", CircuitElm.selectColor.getHexValue());
        if (n == 7) return new EditInfo("Current Color", CircuitElm.currentColor.getHexValue());
        if (n == 8) return new EditInfo("# of Decimal Digits (short format)", CircuitElm.shortDecimalDigits);
        if (n == 9) return new EditInfo("# of Decimal Digits (long format)", CircuitElm.decimalDigits);
        if (n == 10) {
            EditInfo ei = new EditInfo("", 0, -1, -1);
            ei.checkbox = new Checkbox("Developer Mode", sim.developerMode);
            return ei;
        }
        if (n == 11) return new EditInfo("Minimum Target Frame Rate", sim.minFrameRate);
        if (n == 12) {
            EditInfo ei = new EditInfo("", 0, -1, -1);
            ei.checkbox = new Checkbox("Auto-Adjust Timestep", sim.adjustTimeStep);
            return ei;
        }
        if (n == 13 && sim.adjustTimeStep) return new EditInfo("Minimum time step size (s)", sim.minTimeStep, 0, 0);

        return null;
    }

    public void setEditValue(int n, EditInfo ei) {
        if (n == 0 && ei.value > 0) {
            sim.maxTimeStep = ei.value;
        }
        if (n == 1 && ei.value > 0) CircuitElm.voltageRange = ei.value;
        if (n == 2) {
            int lang = ei.choice.getSelectedIndex();
            if (lang == 0) return;
            String langString = null;
            switch (lang) {
                case 1:
                    langString = "en";
                    break;
                case 2:
                    langString = "ru";
                    break;
            }
            if (langString == null) return;
            Storage stor = Storage.getLocalStorageIfSupported();
            if (stor == null) {
                Window.alert(Locale.LS("Can't set language"));
                return;
            }
            stor.setItem("language", langString);
            if (Window.confirm(Locale.LS("Must restart to set language.  Restart now?"))) Window.Location.reload();
        }
        if (n == 3) {
            CircuitElm.positiveColor = setColor("positiveColor", ei, Color.green);
            CircuitElm.setColorScale();
        }
        if (n == 4) {
            CircuitElm.negativeColor = setColor("negativeColor", ei, Color.red);
            CircuitElm.setColorScale();
        }
        if (n == 5) {
            CircuitElm.neutralColor = setColor("neutralColor", ei, Color.gray);
            CircuitElm.setColorScale();
        }
        if (n == 6) CircuitElm.selectColor = setColor("selectColor", ei, Color.cyan);
        if (n == 7) CircuitElm.currentColor = setColor("currentColor", ei, Color.yellow);
        if (n == 8) CircuitElm.setDecimalDigits((int) ei.value, true, true);
        if (n == 9) CircuitElm.setDecimalDigits((int) ei.value, false, true);
        if (n == 10) sim.developerMode = ei.checkbox.getState();
        if (n == 11 && ei.value > 0) sim.minFrameRate = ei.value;
        if (n == 12) {
            sim.adjustTimeStep = ei.checkbox.getState();
            ei.newDialog = true;
        }
        if (n == 13 && ei.value > 0) sim.minTimeStep = ei.value;
    }

    Color setColor(String name, EditInfo ei, Color def) {
        String val = ei.textf.getText();
        if (val.length() == 0) val = def.getHexValue();
        Storage stor = Storage.getLocalStorageIfSupported();
        if (stor != null) stor.setItem(name, val);
        return new Color(val);
    }
}
