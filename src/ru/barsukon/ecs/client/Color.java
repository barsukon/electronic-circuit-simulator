package ru.barsukon.ecs.client;

public class Color {

    public static final Color white = new Color(175, 187, 219);
    public static final Color lightGray = new Color(197, 230, 237);
    public static final Color gray = new Color(204, 230, 235);
    public static final Color GRAY = new Color(204, 230, 235);
    public static final Color dark_gray = new Color(143, 173, 179);
    public static final Color darkGray = new Color(143, 173, 179);
    public static final Color black = new Color(26, 26, 59);
    public static final Color red = new Color(173, 50, 65);
    public static final Color pink = new Color(255, 175, 175);
    public static final Color orange = new Color(255, 200, 0);
    public static final Color yellow = new Color(207, 245, 118);
    public static final Color green = new Color(76, 212, 95);
    public static final Color magenta = new Color(255, 0, 255);
    public static final Color cyan = new Color(93, 108, 153);
    public static final Color blue = new Color(48, 57, 156);
    public static final Color NONE = new Color("");

    private int r, g, b;

    // only for special cases, like no color, or maybe named colors
    private String colorText = null;

    public Color(String colorText) {
        this.colorText = colorText;
        if (colorText.startsWith("#") && colorText.length() == 7) {
            String rs = colorText.substring(1, 3);
            String gs = colorText.substring(3, 5);
            String bs = colorText.substring(5, 7);
            r = Integer.parseInt(rs, 16);
            g = Integer.parseInt(gs, 16);
            b = Integer.parseInt(bs, 16);
        }
    }

    // create mixture of c1 and c2
    public Color(Color c1, Color c2, double mix) {
        double m0 = 1 - mix;
        this.r = (int) (c1.r * m0 + c2.r * mix);
        this.g = (int) (c1.g * m0 + c2.g * mix);
        this.b = (int) (c1.b * m0 + c2.b * mix);
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public String getHexValue() {
        if (colorText != null) {
            return colorText;
        }

        return "#" + pad(Integer.toHexString(r)) + pad(Integer.toHexString(g)) + pad(Integer.toHexString(b));
    }

    private String pad(String in) {
        if (in.length() == 0) {
            return "00";
        }
        if (in.length() == 1) {
            return "0" + in;
        }
        return in;
    }

    public String toString() {
        if (colorText != null) {
            return colorText;
        }
        return "red=" + r + ", green=" + g + ", blue=" + b;
    }
}
