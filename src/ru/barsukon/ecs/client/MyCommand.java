package ru.barsukon.ecs.client;

import com.google.gwt.user.client.Command;

public class MyCommand implements Command {

    private final String menuName;
    private final String itemName;

    public MyCommand(String name, String item) {
        menuName = name;
        itemName = item;
    }

    public void execute() {
        ElectronicCircuitSimulator.mysim.menuPerformed(menuName, itemName);
    }
}
