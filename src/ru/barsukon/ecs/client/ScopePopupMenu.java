package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class ScopePopupMenu {

    private MenuBar m;
    private MenuItem removeScopeItem;
    private CheckboxMenuItem maxScaleItem;
    private MenuItem stackItem;
    private MenuItem unstackItem;
    private MenuItem combineItem;
    private MenuItem removePlotItem;
    private MenuItem resetItem;
    private MenuItem propertiesItem;
    private MenuItem dockItem;
    private MenuItem undockItem;

    ScopePopupMenu() {
        m = new MenuBar(true);
        m.getElement().addClassName("menuBar");
        m.addItem(removeScopeItem = new CheckboxAlignedMenuItem(Locale.LS("Remove Scope"), new MyCommand("scopepop", "remove")));
        m.addItem(dockItem = new CheckboxAlignedMenuItem(Locale.LS("Dock Scope"), new MyCommand("scopepop", "dock")));
        m.addItem(undockItem = new CheckboxAlignedMenuItem(Locale.LS("Undock Scope"), new MyCommand("scopepop", "undock")));
        m.addItem(maxScaleItem = new CheckboxMenuItem(Locale.LS("Max Scale"), new MyCommand("scopepop", "maxscale")));
        m.addItem(stackItem = new CheckboxAlignedMenuItem(Locale.LS("Stack"), new MyCommand("scopepop", "stack")));
        m.addItem(unstackItem = new CheckboxAlignedMenuItem(Locale.LS("Unstack"), new MyCommand("scopepop", "unstack")));
        m.addItem(combineItem = new CheckboxAlignedMenuItem(Locale.LS("Combine"), new MyCommand("scopepop", "combine")));
        m.addItem(removePlotItem = new CheckboxAlignedMenuItem(Locale.LS("Remove Plot"), new MyCommand("scopepop", "removeplot")));
        m.addItem(resetItem = new CheckboxAlignedMenuItem(Locale.LS("Reset"), new MyCommand("scopepop", "reset")));
        m.addItem(propertiesItem = new CheckboxAlignedMenuItem(Locale.LS("Properties..."), new MyCommand("scopepop", "properties")));
    }

    void doScopePopupChecks(boolean floating, boolean canstack, boolean cancombine, boolean canunstack, Scope s) {
        maxScaleItem.setState(s.maxScale);
        stackItem.setVisible(!floating);
        stackItem.setEnabled(canstack);
        unstackItem.setVisible(!floating);
        unstackItem.setEnabled(canunstack);
        combineItem.setVisible(!floating);
        combineItem.setEnabled(cancombine);
        dockItem.setVisible(floating);
        undockItem.setVisible(!floating);
    }

    public MenuBar getMenuBar() {
        return m;
    }
}
