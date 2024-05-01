package ru.barsukon.ecs.client;

import com.google.gwt.storage.client.Storage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

// model for subcircuits

class ExtListEntry {

    ExtListEntry(String s, int n) {
        name = s;
        node = n;
        side = ChipElm.SIDE_W;
    }

    ExtListEntry(String s, int n, int p, int sd) {
        name = s;
        node = n;
        pos = p;
        side = sd;
    }

    String name;
    int node, pos, side;
}

public class CustomCompositeModel implements Comparable<CustomCompositeModel> {

    static HashMap<String, CustomCompositeModel> modelMap;

    int flags, sizeX, sizeY;
    String name;
    String nodeList;
    Vector<ExtListEntry> extList;
    String elmDump;
    String modelCircuit;
    boolean dumped;
    boolean builtin;
    static int sequenceNumber;
    static final int FLAG_SHOW_LABEL = 1;

    void setName(String n) {
        modelMap.remove(name);
        name = n;
        modelMap.put(name, this);
        sequenceNumber++;
    }

    static void initModelMap() {
        modelMap = new HashMap<String, CustomCompositeModel>();

        // create default stub model
        Vector<ExtListEntry> extList = new Vector<ExtListEntry>();
        extList.add(new ExtListEntry("gnd", 1));
        CustomCompositeModel d = createModel("default", "0 0", "GroundElm 1", extList);
        d.sizeX = d.sizeY = 1;
        modelMap.put(d.name, d);
        sequenceNumber = 1;

        // get models from local storage
        Storage stor = Storage.getLocalStorageIfSupported();
        if (stor != null) {
            int len = stor.getLength();
            int i;
            for (i = 0; i != len; i++) {
                String key = stor.key(i);
                if (!key.startsWith("subcircuit:")) continue;
                String data = stor.getItem(key);
                String firstLine = data;
                int lineLen = data.indexOf('\n');
                if (lineLen != -1) firstLine = data.substring(0, lineLen);
                StringTokenizer st = new StringTokenizer(firstLine, " ");
                if (st.nextToken() == ".") {
                    CustomCompositeModel model = undumpModel(st);
                    if (lineLen != -1) model.modelCircuit = data.substring(lineLen + 1);
                }
            }
        }
    }

    static CustomCompositeModel getModelWithName(String name) {
        if (modelMap == null) initModelMap();
        CustomCompositeModel lm = modelMap.get(name);
        return lm;
    }

    static CustomCompositeModel createModel(String name, String elmDump, String nodeList, Vector<ExtListEntry> extList) {
        CustomCompositeModel lm = new CustomCompositeModel();
        lm.name = name;
        lm.elmDump = elmDump;
        lm.nodeList = nodeList;
        lm.extList = extList;
        modelMap.put(name, lm);
        sequenceNumber++;
        return lm;
    }

    static void clearDumpedFlags() {
        if (modelMap == null) return;
        Iterator it = modelMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CustomCompositeModel> pair = (Map.Entry) it.next();
            pair.getValue().dumped = false;
        }
    }

    static Vector<CustomCompositeModel> getModelList() {
        Vector<CustomCompositeModel> vector = new Vector<CustomCompositeModel>();
        Iterator it = modelMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CustomCompositeModel> pair = (Map.Entry) it.next();
            CustomCompositeModel dm = pair.getValue();
            if (dm.builtin) continue;
            vector.add(dm);
        }
        Collections.sort(vector);
        return vector;
    }

    public int compareTo(CustomCompositeModel dm) {
        return name.compareTo(dm.name);
    }

    CustomCompositeModel() {}

    static CustomCompositeModel undumpModel(StringTokenizer st) {
        String name = CustomLogicModel.unescape(st.nextToken());
        CustomCompositeModel model = getModelWithName(name);
        if (model == null) {
            model = new CustomCompositeModel();
            model.name = name;
            modelMap.put(name, model);
            sequenceNumber++;
        } else if (model.modelCircuit != null) {
            // if model has an associated model circuit, don't overwrite it.  keep the old one.
            CirSim.console("ignoring model " + name + ", using stored version instead");
            return model;
        }
        model.undump(st);
        return model;
    }

    void undump(StringTokenizer st) {
        flags = Integer.parseInt(st.nextToken());
        sizeX = Integer.parseInt(st.nextToken());
        sizeY = Integer.parseInt(st.nextToken());
        int extCount = Integer.parseInt(st.nextToken());
        int i;
        extList = new Vector<ExtListEntry>();
        for (i = 0; i != extCount; i++) {
            String s = CustomLogicModel.unescape(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int sd = Integer.parseInt(st.nextToken());
            extList.add(new ExtListEntry(s, n, p, sd));
        }
        nodeList = CustomLogicModel.unescape(st.nextToken());
        elmDump = CustomLogicModel.unescape(st.nextToken());
    }

    boolean isSaved() {
        if (name == null) return false;
        Storage stor = Storage.getLocalStorageIfSupported();
        if (stor == null) return false;
        return stor.getItem("subcircuit:" + name) != null;
    }

    void setSaved(boolean sv) {
        Storage stor = Storage.getLocalStorageIfSupported();
        if (stor == null) return;
        if (sv) {
            String cir = (modelCircuit == null) ? "" : modelCircuit;
            stor.setItem("subcircuit:" + name, dump() + "\n" + cir);
        } else stor.removeItem("subcircuit:" + name);
    }

    String arrayToList(String arr[]) {
        if (arr == null) return "";
        if (arr.length == 0) return "";
        String x = arr[0];
        int i;
        for (i = 1; i < arr.length; i++) x += "," + arr[i];
        return x;
    }

    boolean showLabel() {
        return (flags & FLAG_SHOW_LABEL) != 0;
    }

    void setShowLabel(boolean sl) {
        flags = (sl) ? (flags | FLAG_SHOW_LABEL) : (flags & ~FLAG_SHOW_LABEL);
    }

    String[] listToArray(String arr) {
        return arr.split(",");
    }

    String dump() {
        if (builtin) return "";
        dumped = true;
        String str = ". " + CustomLogicModel.escape(name) + " " + flags + " " + sizeX + " " + sizeY + " " + extList.size() + " ";
        int i;
        for (i = 0; i != extList.size(); i++) {
            ExtListEntry ent = extList.get(i);
            if (i > 0) str += " ";
            str += CustomLogicModel.escape(ent.name) + " " + ent.node + " " + ent.pos + " " + ent.side;
        }
        str += " " + CustomLogicModel.escape(nodeList) + " " + CustomLogicModel.escape(elmDump);
        return str;
    }

    boolean canLoadModelCircuit() {
        return modelCircuit != null && modelCircuit.length() > 0;
    }
}
