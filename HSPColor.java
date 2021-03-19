public class HSPColor {

    public static int[] HSPtoRGB(float[] hsp) {
        return HSPtoRGB(hsp[0], hsp[1], hsp[2]);
    }

    public static int[] HSPtoRGB(float hue, float saturation, float percievedLuminance) {
        float r = 0;
        float g = 0;
        float b = 0;
        float h = hue % 1 * 6.0f;
        float s = saturation;
        float p = percievedLuminance;
        switch((int) h) {
            case 0 -> {
                r = 1;
                g = (h - 0);
                b = 0;
            }
            case 1 -> {
                r = 1 - (h - 1);
                g = 1;
                b = 0;
            }
            case 2 -> {
                r = 0;
                g = 1;
                b = (h - 2);
            }
            case 3 -> {
                r = 0;
                g = 1 - (h - 3);
                b = 1;
            }
            case 4 -> {
                r = (h - 4);
                g = 0;
                b = 1;
            }
            case 5 -> {
                r = 1;
                g = 0;
                b = 1 - (h - 5);
            }
            default -> {
                r = 0;
                g = 0;
                b = 0;
            }
        }
        r = (1 - s + s * r);
        g = (1 - s + s * g);
        b = (1 - s + s * b);
        float q = (float) Math.sqrt((p * p) / (0.3f * r * r + 0.6f * g * g + 0.1f * b * b));
        if(q > 1.0) {
            r = (r + s - 1) / s;
            g = (g + s - 1) / s;
            b = (b + s - 1) / s;
            s = (float) (-(0.5 * Math.sqrt(40 * (p * p - 1)
                                           * (b * b - 2 * b
                                              + 6 * g * g - 12 * g
                                              + 3 * r * r - 6 * r
                                              + 10)
                                           + 4 * (b + 6 * g + 3 * r - 10)
                                             * (b + 6 * g + 3 * r - 10))
                           + b + 6 * g + 3 * r - 10)
                         / (b * b - 2 * b
                            + 6 * g * g - 12 * g
                            + 3 * r * r - 6 * r
                            + 10)); //*/ From Wolfram alpha
            r = (1 - s + s * r);
            g = (1 - s + s * g);
            b = (1 - s + s * b);
            q = (float) Math.sqrt((p * p) / (0.3f * r * r + 0.6f * g * g + 0.1f * b * b));
        }
        r *= q;
        g *= q;
        b *= q;
        int aInt = 0xff;
        int rInt = (int) (r * 255.0f + 0.5f);
        int gInt = (int) (g * 255.0f + 0.5f);
        int bInt = (int) (b * 255.0f + 0.5f);
        return new int[] {rInt, gInt, bInt};
    }

    public static float[] RGBtoHSP(int[] rgb) {
        return RGBtoHSP(rgb[0], rgb[1], rgb[2]);
    }

    public static float[] RGBtoHSP(int red, int green, int blue) {
        float r = ((float) red) / 255.0f;
        float g = ((float) green) / 255.0f;
        float b = ((float) blue) / 255.0f;
        float[] hsp = new float[3];
        float h = 0;
        float s = 0;
        float p = (float) Math.sqrt(0.3f * r * r + 0.6f * g * g + 0.1f * b * b);
        float high = Math.max(Math.max(r, g), b);
        float low = Math.min(Math.min(r, g), b);
        if(high != 0) {
            s = (high - low) / high;
        }
        if(high != low) {
            float hm = Math.max(Math.max(1 - r / high, 1 - g / high), 1 - b / high);
            float hr = 1 - (1 - r / high) / hm;
            float hg = 1 - (1 - g / high) / hm;
            float hb = 1 - (1 - b / high) / hm;
            float hh = Math.max(Math.max(hr, hg), hb);
            if(hg == hh) {
                h = (2 + (hb - hr)) / 6;
            } else if(hb == hh) {
                h = (4 + (hr - hg)) / 6;
            } else if(hr == hh) {
                h = (6 + (hg - hb)) / 6;
            }
            h %= 1;
        }
        hsp[0] = h;
        hsp[1] = s;
        hsp[2] = p;
        return hsp;
    }

    public static int RGBtoInt(int rInt, int gInt, int bInt) {
        int aInt = 0xff;
        rInt = rInt > 255 ? 255 : rInt;
        gInt = gInt > 255 ? 255 : gInt;
        bInt = bInt > 255 ? 255 : bInt;
        int rgbInt = aInt << 24 | rInt << 16 | gInt << 8 | bInt;
        return rgbInt;
    }
}
