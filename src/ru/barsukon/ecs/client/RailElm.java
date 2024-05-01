package ru.barsukon.ecs.client;

class RailElm extends VoltageElm {

    public RailElm(int xx, int yy) {
        super(xx, yy, WF_DC);
    }

    RailElm(int xx, int yy, int wf) {
        super(xx, yy, wf);
    }

    public RailElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
    }

    final int FLAG_CLOCK = 1;

    int getDumpType() {
        return 'R';
    }

    int getPostCount() {
        return 1;
    }

    void setPoints() {
        super.setPoints();
        lead1 = interpPoint(point1, point2, 1 - circleSize / dn);
    }

    String getRailText() {
        return null;
    }

    void draw(Graphics g) {
        String rt = getRailText();
        double w = rt == null ? circleSize : g.context.measureText(rt).getWidth() / 2;
        if (w > dn * .8) w = dn * .8;
        lead1 = interpPoint(point1, point2, 1 - w / dn);
        setBbox(point1, point2, circleSize);
        setVoltageColor(g, volts[0]);
        drawThickLine(g, point1, lead1);
        drawRail(g);
        drawPosts(g);
        curcount = updateDotCount(-current, curcount);
        if (sim.dragElm != this) drawDots(g, point1, lead1, curcount);
    }

    void drawRail(Graphics g) {
        if (waveform == WF_SQUARE && (flags & FLAG_CLOCK) != 0) drawRailText(g, "CLK");
        else if (waveform == WF_DC || waveform == WF_VAR) {
            g.setColor(needsHighlight() ? selectColor : whiteColor);
            setPowerColor(g, false);
            double v = getVoltage();
            String s;
            if (Math.abs(v) < 1) s = showFormat.format(v) + " V";
            else s = getShortUnitText(v, "V");
            if (getVoltage() > 0) s = "+" + s;
            drawLabeledNode(g, s, point1, lead1);
        } else {
            drawWaveform(g, point2);
        }
    }

    void drawRailText(Graphics g, String s) {
        g.setColor(needsHighlight() ? selectColor : whiteColor);
        setPowerColor(g, false);
        drawLabeledNode(g, s, point1, lead1);
    }

    double getVoltageDiff() {
        return volts[0];
    }

    void stamp() {
        if (waveform == WF_DC) sim.stampVoltageSource(0, nodes[0], voltSource, getVoltage());
        else sim.stampVoltageSource(0, nodes[0], voltSource);
    }

    void doStep() {
        if (waveform != WF_DC) sim.updateVoltageSource(0, nodes[0], voltSource, getVoltage());
    }

    boolean hasGroundConnection(int n1) {
        return true;
    }

    int getShortcut() {
        return 'V';
    }
    //    void drawHandles(Graphics g, Color c) {
    //    	g.setColor(c);
    //		g.fillRect(x-3, y-3, 7, 7);
    //    }

}
