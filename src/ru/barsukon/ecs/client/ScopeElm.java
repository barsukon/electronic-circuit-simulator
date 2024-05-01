package ru.barsukon.ecs.client;

class ScopeElm extends CircuitElm {

    Scope elmScope;

    public ScopeElm(int xx, int yy) {
        super(xx, yy);
        noDiagonal = false;
        x2 = x + 128;
        y2 = y + 64;
        elmScope = new Scope(sim);
        setPoints();
    }

    public void setScopeElm(CircuitElm e) {
        elmScope.setElm(e);
        elmScope.resetGraph();
    }

    public ScopeElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
        super(xa, ya, xb, yb, f);
        noDiagonal = false;
        String sStr = st.nextToken();
        StringTokenizer sst = new StringTokenizer(sStr, "_");
        elmScope = new Scope(sim);
        elmScope.undump(sst);
        setPoints();
        elmScope.resetGraph();
    }

    public void setScopeRect() {
        int i1 = sim.transformX(min(x, x2));
        int i2 = sim.transformX(max(x, x2));
        int j1 = sim.transformY(min(y, y2));
        int j2 = sim.transformY(max(y, y2));
        Rectangle r = new Rectangle(i1, j1, i2 - i1, j2 - j1);
        if (!r.equals(elmScope.rect)) elmScope.setRect(r);
    }

    public void setPoints() {
        super.setPoints();
        setScopeRect();
    }

    public void setElmScope(Scope s) {
        elmScope = s;
    }

    public void stepScope() {
        elmScope.timeStep();
    }

    public void reset() {
        super.reset();
        elmScope.resetGraph(true);
    }

    public void clearElmScope() {
        elmScope = null;
    }

    boolean canViewInScope() {
        return false;
    }

    int getDumpType() {
        return 403;
    }

    public String dump() {
        String dumpStr = super.dump();
        String elmDump = elmScope.dump();
        if (elmDump == null) return null;
        String sStr = elmDump.replace(' ', '_');
        sStr = sStr.replaceFirst("o_", ""); // remove unused prefix for embedded Scope
        return dumpStr + " " + sStr;
    }

    void draw(Graphics g) {
        g.setColor(needsHighlight() ? selectColor : whiteColor);
        g.context.save();
        g.context.scale(1 / sim.transform[0], 1 / sim.transform[3]);
        g.context.translate(-sim.transform[4], -sim.transform[5]);

        setScopeRect();
        elmScope.position = -1;
        elmScope.draw(g);
        g.context.restore();
        setBbox(point1, point2, 0);
        drawPosts(g);
    }

    int getPostCount() {
        return 0;
    }

    int getNumHandles() {
        return 2;
    }

    void selectScope(int mx, int my) {
        elmScope.selectScope(mx, my);
    }
}
