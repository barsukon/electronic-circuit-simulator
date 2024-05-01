package ru.barsukon.ecs.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AboutBox extends PopupPanel {

    VerticalPanel vp;
    Button okButton;

    AboutBox(String version) {
        super();
        // Add versionString variable to SessionStorage for iFrame in AboutBox
        Storage sstor = Storage.getSessionStorageIfSupported();
        sstor.setItem("versionString", version);

        vp = new VerticalPanel();
        setWidget(vp);
        vp.setWidth("400px");
        vp.add(new HTML("<iframe src=\"about.html\" width=\"400\" height=\"430\" scrolling=\"auto\" frameborder=\"0\"></iframe><br>"));

        vp.add(okButton = new Button("OK"));
        okButton.addClickHandler(
            new ClickHandler() {
                public void onClick(ClickEvent event) {
                    close();
                }
            }
        );
        center();
        show();
    }

    public void close() {
        hide();
    }
}
