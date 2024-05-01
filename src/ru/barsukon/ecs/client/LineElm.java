package ru.barsukon.ecs.client;

class LineElm extends GraphicElm {

    public LineElm(int xx, int yy) {
        super(xx, yy);
        x2 = xx;
        y2 = yy;
        setBbox(x, y, x2, y2);
    }

    public LineElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
        super(xa, ya, xb, yb, f);
        x2 = xb;
        y2 = yb;
        setBbox(x, y, x2, y2);
    }

    String dump() {
        return super.dump();
    }

    int getDumpType() {
        return 423;
    }

    void drag(int xx, int yy) {
        x2 = xx;
        y2 = yy;
    }

    boolean creationFailed() {
        return Math.hypot(x - x2, y - y2) < 16;
    }

    void draw(Graphics g) {
        //g.setColor(needsHighlight() ? selectColor : lightGrayColor);
        g.setColor(needsHighlight() ? selectColor : Color.GRAY);
        setBbox(x, y, x2, y2);
        g.drawLine(x, y, x2, y2);
    }

    public EditInfo getEditInfo(int n) {
        return null;
    }

    public void setEditValue(int n, EditInfo ei) {}

    void getInfo(String arr[]) {}

    @Override
    int getShortcut() {
        return 0;
    }
}
