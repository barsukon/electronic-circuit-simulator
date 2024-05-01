package ru.barsukon.ecs.client;

import ru.barsukon.ecs.client.util.Locale;

class OutputElm extends CircuitElm {

    final int FLAG_VALUE = 1;
    final int FLAG_FIXED = 2;
    int scale;

    public OutputElm(int xx, int yy) {
        super(xx, yy);
        scale = SCALE_AUTO;
    }

    public OutputElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
        super(xa, ya, xb, yb, f);
        scale = SCALE_AUTO;
        try {
            scale = Integer.parseInt(st.nextToken());
        } catch (Exception e) {
        }
    }

    String dump() {
        return super.dump() + " " + scale;
    }

    int getDumpType() {
        return 'O';
    }

    int getPostCount() {
        return 1;
    }

    void setPoints() {
        super.setPoints();
        lead1 = new Point();
    }

    void draw(Graphics g) {
        g.save();
        boolean selected = needsHighlight();
        Font f = new Font("SansSerif", selected ? Font.BOLD : 0, 14);
        g.setFont(f);
        g.setColor(selected ? selectColor : whiteColor);
        String s = showVoltage() ? getUnitTextWithScale(volts[0], "V", scale, isFixed()) : Locale.LS("out");
        // FontMetrics fm = g.getFontMetrics();

        if (this == sim.plotXElm) s = "X";
        if (this == sim.plotYElm) s = "Y";

        interpPoint(point1, point2, lead1, 1 - ((int) g.context.measureText(s).getWidth() / 2 + 8) / dn);
        setBbox(point1, lead1, 0);
        drawCenteredText(g, s, x2, y2, true);
        setVoltageColor(g, volts[0]);

        if (selected) g.setColor(selectColor);
        
        drawThickLine(g, point1, lead1);
        drawPosts(g);
        g.restore();
    }

    double getVoltageDiff() {
        return volts[0];
    }

    void getInfo(String arr[]) {
        arr[0] = "output";
        arr[1] = "V = " + getVoltageText(volts[0]);
    }

    public EditInfo getEditInfo(int n) {
        if (n == 0)
            return EditInfo.createCheckbox("Show Voltage", showVoltage());
        if (!showVoltage())
            return null;
        if (n == 1) {
            EditInfo ei = new EditInfo("Scale", 0);
            ei.choice = new Choice();
            ei.choice.add("Auto");
            ei.choice.add("V");
            ei.choice.add("mV");
            ei.choice.add(Locale.muString + "V");
            ei.choice.select(scale);
            return ei;
        }
        if (scale == SCALE_AUTO)
            return null;
        if (n == 2)
            return EditInfo.createCheckbox("Fixed Precision", isFixed());
        return null;
    }

    boolean isFixed() {
        return (flags & FLAG_FIXED) != 0;
    }

    boolean showVoltage() {
        return (flags & FLAG_VALUE) != 0;
    }

    public void setEditValue(int n, EditInfo ei) {
        if (n == 0) {
            flags = ei.changeFlag(flags, FLAG_VALUE);
            ei.newDialog = true;
        }
        if (n == 1) {
            scale = ei.choice.getSelectedIndex();
            ei.newDialog = true;
        }
        if (n == 2)
            flags = ei.changeFlag(flags, FLAG_FIXED);
    }
}
