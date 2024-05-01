package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImportFromTextDialog extends Dialog {

    VerticalPanel vp;
    HorizontalPanel hp;
    CirSim sim;
    // RichTextArea textBox;
    TextArea textArea;

    public ImportFromTextDialog(CirSim asim) {
        super();
        sim = asim;
        closeOnEnter = false;
        Button okButton, cancelButton;
        final Checkbox subCheck;
        vp = new VerticalPanel();
        setWidget(vp);
        setText(Locale.LS("Import from Text"));
        vp.add(new Label(Locale.LS("Paste the text file for your circuit here...")));
        //		vp.add(textBox = new RichTextArea());
        vp.add(textArea = new TextArea());
        textArea.setWidth("300px");
        textArea.setHeight("200px");
        vp.add(subCheck = new Checkbox(Locale.LS("Load Subcircuits Only")));
        hp = new HorizontalPanel();
        vp.add(hp);
        hp.add(okButton = new Button(Locale.LS("OK")));
        okButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    String s;
                    sim.pushUndo();
                    closeDialog();
                    //				s=textBox.getHTML();
                    //				s=s.replace("<br>", "\r");
                    s = textArea.getText();
                    sim.importCircuitFromText(s, subCheck.getState());
                }
            }
        );
        hp.add(cancelButton = new Button(Locale.LS("Cancel")));
        cancelButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    closeDialog();
                }
            }
        );
        this.center();
        show();
    }
}
