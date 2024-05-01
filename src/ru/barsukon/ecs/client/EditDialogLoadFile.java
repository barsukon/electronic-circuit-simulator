package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FileUpload;

/*
 * An abstract class for ECS which allows components to prompt for files from the user.
 */
public abstract class EditDialogLoadFile extends FileUpload implements ChangeHandler {

    public static final boolean isSupported() {
        return LoadFile.isSupported();
    }

    public static void doErrorCallback(String msg) {
        Window.alert(Locale.LS(msg));
    }

    EditDialogLoadFile() {
        super();
        this.setName(Locale.LS("Load File"));
        this.getElement().setId("EditDialogLoadFileElement");
        this.addChangeHandler(this);
        this.addStyleName("offScreen");
        this.setPixelSize(0, 0);
    }

    public void onChange(ChangeEvent e) {
        handle();
    }

    public final native void open() /*-{
		$doc.getElementById("EditDialogLoadFileElement").click();
	}-*/;

    public abstract void handle();
}
