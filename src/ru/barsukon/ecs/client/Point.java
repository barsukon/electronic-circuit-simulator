package ru.barsukon.ecs.client;

public class Point {

    public int x;
    public int y;

    public Point(int i, int j) {
        x = i;
        y = j;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    public Point() {
        x = 0;
        y = 0;
    }

    public void setLocation(Point p) {
        x = p.x;
        y = p.y;
    }

    public String toString() {
        return "Point(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (this.x == that.x && this.y == that.y);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (41 * (41 + x) + y);
    }
}
