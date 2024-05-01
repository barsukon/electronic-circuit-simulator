package ru.barsukon.ecs.client;

class CustomCompositeChipElm extends ChipElm {

    String label;

    public CustomCompositeChipElm(int xx, int yy) {
        super(xx, yy);
        setSize(2);
    }

    boolean needsBits() {
        return false;
    }

    void setupPins() {}

    int getVoltageSourceCount() {
        return 0;
    }

    void setPins(Pin p[]) {
        pins = p;
    }

    void allocPins(int n) {
        pins = new Pin[n];
    }

    void setPin(int n, int p, int s, String t) {
        pins[n] = new Pin(p, s, t);
        pins[n].fixName();
    }

    void setLabel(String text) {
        label = text;
    }

    void drawLabel(Graphics g, int x, int y) {
        if (label == null) return;
        g.save();
        g.context.setTextBaseline("middle");
        g.context.setTextAlign("center");
        g.drawString(label, x, y);
        g.restore();
    }

    int getPostCount() {
        return pins == null ? 1 : pins.length;
    }
}
