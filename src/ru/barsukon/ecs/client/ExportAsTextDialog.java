package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ExportAsTextDialog extends Dialog {

    VerticalPanel vp;
    CirSim sim;
    TextArea textArea;

    public ExportAsTextDialog(CirSim asim, String s) {
        super();
        closeOnEnter = false;
        sim = asim;
        //	RichTextArea tb;
        TextArea ta;
        Button okButton, importButton, copyButton;
        Label la2;
        SafeHtml html;
        vp = new VerticalPanel();
        setWidget(vp);
        setText(Locale.LS("Export as Text"));
        vp.add(new Label(Locale.LS("Text file for this circuit is...")));
        //		vp.add(tb = new RichTextArea());
        //		html=SafeHtmlUtils.fromString(s);
        //		html=SafeHtmlUtils.fromTrustedString(html.asString().replace("\n", "<BR>"));
        //		tb.setHTML(html);
        vp.add(ta = new TextArea());
        ta.setWidth("400px");
        ta.setHeight("300px");
        ta.setText(s);
        textArea = ta;
        //	vp.add(la2 = new Label(sim.LS("To save this file select it all (eg click in text and type control-A) and copy to your clipboard (eg control-C) before pasting to an empty text file (eg on Windows Notepad) and saving as a new file."), true));
        //	la2.setWidth("300px");
        HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth("100%");
        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        hp.setStyleName("topSpace");
        vp.add(hp);
        hp.add(okButton = new Button(Locale.LS("OK")));
        hp.add(copyButton = new Button(Locale.LS("Copy to Clipboard")));
        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        hp.add(importButton = new Button(Locale.LS("Re-Import")));
        okButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    closeDialog();
                }
            }
        );
        importButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    String s;
                    sim.pushUndo();
                    closeDialog();
                    //				s=textBox.getHTML();
                    //				s=s.replace("<br>", "\r");
                    s = textArea.getText();
                    if (s != null) {
                        sim.readCircuit(s);
                        sim.allowSave(false);
                    }
                }
            }
        );
        copyButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    textArea.setFocus(true);
                    textArea.selectAll();
                    copyToClipboard();
                    textArea.setSelectionRange(0, 0);
                }
            }
        );
        this.center();
    }

    private static native boolean copyToClipboard() /*-{
	    return $doc.execCommand('copy');
	}-*/;
}
