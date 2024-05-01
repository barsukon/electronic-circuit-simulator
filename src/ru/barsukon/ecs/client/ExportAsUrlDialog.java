package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ExportAsUrlDialog extends Dialog {

    VerticalPanel vp;
    Button shortButton;
    static TextArea textArea;
    String requrl;

    native String compress(String dump) /*-{
	    return $wnd.LZString.compressToEncodedURIComponent(dump);
	}-*/;

    public ExportAsUrlDialog(String dump) {
        super();
        closeOnEnter = false;
        String start[] = Location.getHref().split("\\?");
        String query = "?ctz=" + compress(dump);
        dump = start[0] + query;
        requrl = URL.encodeQueryString(query);
        Button okButton, copyButton;

        Label la1, la2;
        vp = new VerticalPanel();
        setWidget(vp);
        setText(Locale.LS("Export as URL"));
        vp.add(new Label(Locale.LS("URL for this circuit is...")));
        if (dump.length() > 2000) {
            vp.add(la1 = new Label(Locale.LS("Warning: this URL is longer than 2000 characters and may not work in some browsers."), true));
            la1.setWidth("300px");
        }
        vp.add(textArea = new TextArea());
        textArea.setWidth("400px");
        textArea.setHeight("300px");
        textArea.setText(dump);
        //		tb.setMaxLength(s.length());
        //		tb.setVisibleLength(s.length());
        //		vp.add(la2 = new Label(CirSim.LS("To save this URL select it all (eg click in text and type control-A) and copy to your clipboard (eg control-C) before pasting to a suitable place."), true));
        //		la2.setWidth("300px");

        HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth("100%");
        hp.setStyleName("topSpace");
        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        hp.add(okButton = new Button(Locale.LS("OK")));
        hp.add(copyButton = new Button(Locale.LS("Copy to Clipboard")));
        vp.add(hp);
        okButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    closeDialog();
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
