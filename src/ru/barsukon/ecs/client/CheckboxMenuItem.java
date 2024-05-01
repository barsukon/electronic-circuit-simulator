package ru.barsukon.ecs.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;

public class CheckboxMenuItem extends MenuItem implements Command {

    private boolean on = false;
    private String name = "";
    private String shortcut = "";
    private Command extcmd = null;
    static String checkBoxHtml = "<div style=\"display:inline-block;width:15px;\">";

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public CheckboxMenuItem(String s) {
        super(s, (Command) null);
        super.setScheduledCommand(this);
        name = s;
        setState(false);
    }

    public CheckboxMenuItem(String s, Command cmd) {
        super(s, (Command) null);
        super.setScheduledCommand(this);
        extcmd = cmd;
        name = s;
        setState(false);
    }

    public CheckboxMenuItem(String s, String c, Command cmd) {
        this(s, cmd);
        shortcut = c;
    }

    public CheckboxMenuItem(String s, String c) {
        this(s);
        shortcut = c;
    }

    public void setShortcut(String s) {
        shortcut = s;
    }

    public void execute() {
        setState(!on);
        if (extcmd != null) {
            extcmd.execute();
            CircuitElm.sim.repaint();
        }
    }

    public void setTitle(String s) {
        name = s;
    }

    public void setState(boolean newstate) {
        on = newstate;
        String s;
        if (on) //       	super.setHTML("&#10004;&nbsp;"+name);
        s = checkBoxHtml + "&#10004;</div>" + name;
        else //        	super.setHTML("&emsp;&nbsp;"+name);
        s = checkBoxHtml + "&nbsp;</div>" + name;
        if (shortcut != "") if (shortcut.length() == 1) {
            s = s + "<div style=\"display:inline-block;width:20px;right:10px;text-align:center;position:absolute;\">" + shortcut + "</div>";
        } else {
            // add some space so menu text doesn't overlap shortcut
            s = s + "<span style=\"display:inline-block; width: 60px;\"></span>";
            s = s + "<div style=\"display:inline-block;right:10px;text-align:right;position:absolute;\">" + shortcut + "</div>";
        }
        setHTML(s);
    }

    public boolean getState() {
        return on;
    }
}
