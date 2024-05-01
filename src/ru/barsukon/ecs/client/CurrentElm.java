package ru.barsukon.ecs.client;

class CurrentElm extends CircuitElm {

    double currentValue;
    boolean broken;

    public CurrentElm(int xx, int yy) {
        super(xx, yy);
        currentValue = .01;
    }

    public CurrentElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
        super(xa, ya, xb, yb, f);
        try {
            currentValue = new Double(st.nextToken()).doubleValue();
        } catch (Exception e) {
            currentValue = .01;
        }
    }

    String dump() {
        return super.dump() + " " + currentValue;
    }

    int getDumpType() {
        return 'i';
    }

    Polygon arrow;
    Point ashaft1, ashaft2, center;

    void setPoints() {
        super.setPoints();
        calcLeads(26);
        ashaft1 = interpPoint(lead1, lead2, .25);
        ashaft2 = interpPoint(lead1, lead2, .6);
        center = interpPoint(lead1, lead2, .5);
        Point p2 = interpPoint(lead1, lead2, .75);
        arrow = calcArrow(center, p2, 4, 4);
    }

    void draw(Graphics g) {
        int cr = 12;
        draw2Leads(g);
        setVoltageColor(g, (volts[0] + volts[1]) / 2);
        setPowerColor(g, false);

        drawThickCircle(g, center.x, center.y, cr);
        drawThickLine(g, ashaft1, ashaft2);

        g.fillPolygon(arrow);
        setBbox(point1, point2, cr);
        doDots(g);
        if (sim.showValuesCheckItem.getState() && current != 0) {
            String s = getShortUnitText(current, "A");
            if (dx == 0 || dy == 0) drawValues(g, s, cr);
        }
        drawPosts(g);
    }

    // analyzeCircuit determines if current source has a path or if it's broken
    void setBroken(boolean b) {
        broken = b;
    }

    // we defer stamping current sources until we can tell if they have a current path or not
    void stamp() {
        if (broken) {
            // no current path; stamping a current source would cause a matrix error.
            sim.stampResistor(nodes[0], nodes[1], 1e8);
            current = 0;
        } else {
            // ok to stamp a current source
            sim.stampCurrentSource(nodes[0], nodes[1], currentValue);
            current = currentValue;
        }
    }

    public EditInfo getEditInfo(int n) {
        if (n == 0) return new EditInfo("Current (A)", currentValue, 0, .1);
        return null;
    }

    public void setEditValue(int n, EditInfo ei) {
        currentValue = ei.value;
    }

    void getInfo(String arr[]) {
        arr[0] = "current source";
        getBasicInfo(arr);
    }

    double getVoltageDiff() {
        return volts[1] - volts[0];
    }

    double getPower() {
        return -getVoltageDiff() * current;
    }
}
