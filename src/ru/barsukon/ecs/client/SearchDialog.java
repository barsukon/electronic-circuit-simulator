package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class SearchDialog extends Dialog {

    VerticalPanel vp;
    CirSim sim;
    TextArea textArea;
    TextBox textBox;
    Button okButton;
    ListBox listBox;

    public SearchDialog(CirSim asim) {
        super();
        sim = asim;
        Button cancelButton;
        vp = new VerticalPanel();
        setWidget(vp);
        textBox = new TextBox();
        textBox.setMaxLength(15);
        vp.add(textBox);
        textBox.addKeyUpHandler(
            new KeyUpHandler() {
                public void onKeyUp(KeyUpEvent ev) {
                    search();
                }
            }
        );

        setText(Locale.LS("Find Component"));

        listBox = new ListBox();
        listBox.setWidth("100%");
        listBox.addDoubleClickHandler(
            new DoubleClickHandler() {
                public void onDoubleClick(DoubleClickEvent ev) {
                    apply();
                }
            }
        );
        listBox.setVisibleItemCount(10);
        vp.add(listBox);
        int i;
        for (i = 0; i != asim.mainMenuItems.size(); i++) {
            CheckboxMenuItem item = sim.mainMenuItems.get(i);
            if (item.getShortcut().length() > 1) break;
            listBox.addItem(item.getName());
        }

        HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth("100%");
        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        hp.setStyleName("topSpace");
        vp.add(hp);
        hp.add(okButton = new Button(Locale.LS("OK")));
        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        hp.add(cancelButton = new Button(Locale.LS("Cancel")));
        okButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    apply();
                }
            }
        );
        cancelButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    closeDialog();
                }
            }
        );
        this.center();
        textBox.setFocus(true);
    }

    void apply() {
        String s = listBox.getSelectedItemText();

        int i;
        for (i = 0; i != sim.mainMenuItems.size(); i++) {
            CheckboxMenuItem item = sim.mainMenuItems.get(i);
            if (item.getName().equals(s)) {
                item.getScheduledCommand().execute();
                break;
            }
        }

        closeDialog();
    }

    void search() {
        String str = textBox.getText().toLowerCase();
        int i;
        listBox.clear();
        Vector<String> items = new Vector<String>();
        for (i = 0; i != sim.mainMenuItems.size(); i++) {
            CheckboxMenuItem item = sim.mainMenuItems.get(i);
            if (item.getName().toLowerCase().contains(str)) {
                if (!items.contains(item.getName())) items.add(item.getName());
            }
        }
        Collections.sort(
            items,
            new Comparator<String>() {
                public int compare(String a, String b) {
                    return a.compareTo(b);
                }
            }
        );
        for (i = 0; i != items.size(); i++) listBox.addItem(items.get(i));
        if (items.size() > 0) listBox.setItemSelected(0, true);
    }
}
