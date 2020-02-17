public class HSPColor {

    public static int HSPtoRGB(float hue, float saturation, float percievedLuminance) {
        float r = 0;
        float g = 0;
        float b = 0;
        float h = hue * 6.0f;
        float s = saturation;
        float p = percievedLuminance;
        switch((int) h) {
            case 0:
                r = 1;
                g = (h - 0);
                b = 0;
                break;
            case 1:
                r = 1 - (h - 1);
                g = 1;
                b = 0;
                break;
            case 2:
                r = 0;
                g = 1;
                b = (h - 2);
                break;
            case 3:
                r = 0;
                g = 1 - (h - 3);
                b = 1;
                break;
            case 4:
                r = (h - 4);
                g = 0;
                b = 1;
                break;
            case 5:
                r = 1;
                g = 0;
                b = 1 - (h - 5);
                break;
            default:
                r = 0;
                g = 0;
                b = 0;
        }
        r = (1 - s + s * r);
        g = (1 - s + s * g);
        b = (1 - s + s * b);
        float q = (float) Math.sqrt((p * p) / (0.3f * r * r + 0.6f * g * g + 0.1f * b * b));
        r *= q;
        g *= q;
        b *= q;
        int aBits = 0xff;
        int rBits = (int) (r * 255.0f + 0.5f);
        int gBits = (int) (g * 255.0f + 0.5f);
        int bBits = (int) (b * 255.0f + 0.5f);
        int rgb = aBits << 24 | rBits << 16 | gBits << 8 | bBits;
        if(rBits > 255 || gBits > 255 || bBits > 255) {
            int pBits = (int) (percievedLuminance * 255.0d + 0.5d);
            rgb = aBits << 24 | pBits << 16 | pBits << 8 | pBits;
        }
        return rgb;
    }

    public static float[] RGBtoHSP(int red, int green, int blue, float[] hsp) {
        float r = ((float) red) / 255.0f;
        float g = ((float) green) / 255.0f;
        float b = ((float) blue) / 255.0f;
        hsp = hsp == null ? new float[3] : hsp;
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
}
