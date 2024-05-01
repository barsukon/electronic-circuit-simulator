package ru.barsukon.ecs.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FileUpload;

public class LoadFile extends FileUpload implements ChangeHandler {

    static CirSim sim;

    public static final native boolean isSupported() /*-{
			return !!($wnd.File && $wnd.FileReader);
		 }-*/;

    public static void doLoadCallback(String s, String t) {
        sim.pushUndo();
        sim.readCircuit(s);
        sim.createNewLoadFile();
        sim.setCircuitTitle(t);
        ExportAsLocalFileDialog.setLastFileName(t);
        sim.unsavedChanges = false;
    }

    LoadFile(CirSim s) {
        super();
        sim = s;
        this.setName("Import");
        this.getElement().setId("LoadFileElement");
        this.addChangeHandler(this);
        this.addStyleName("offScreen");
    }

    public void onChange(ChangeEvent e) {
        doLoad();
    }

    public final native void click() /*-{
		$doc.getElementById("LoadFileElement").click();
	 }-*/;

    public static final native void doLoad() /*-{
			var oFiles = $doc.getElementById("LoadFileElement").files,
    		nFiles = oFiles.length;
    		if (nFiles>=1) {
    		    if (oFiles[0].size >= 128000)
    		    	alert("File too large!");
    		    else {
        		var reader = new FileReader();
    			reader.onload = function(e) {
      				var text = reader.result;
      				@ru.barsukon.ecs.client.LoadFile::doLoadCallback(Ljava/lang/String;Ljava/lang/String;)(text, oFiles[0].name);
        		};

    			reader.readAsText(oFiles[0]);
    		    }
    		}
		 }-*/;
}
