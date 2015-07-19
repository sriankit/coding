package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Cylinder {
    final double pi = 2.0 * Math.acos(0.0);

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            double w = in.readInt();
            double h = in.readInt();
            double v = 0;
            double r = Math.min(h / 3.0, w / (pi * 2.0));
            double a = h - 2 * r;
            v = Math.max(v, pi * r * r * a);
            a = w;
            double aa;
            if (h >= w * (pi + 2)) {
                r = w / 2.0;
            } else if (h <= w * (pi + 1) / 2.0) {
                r = h / (pi + 1) / 2.0;
            } else {
                r = h * (pi + 1);
                r += w;
                aa = ((2 * h * w * (pi + 1)) - (w * w * pi * (pi + 2)));
                if (aa >= 0) {
                    aa = Math.sqrt(aa);
                    r -= aa;
                    r /= (2 * (pi + 1) * (pi + 1));
                } else
                    r = 0;
                if (w > 4 * r || w < 2 * r) {
                    r = 0;
                }
            }
            v = Math.max(v, pi * r * r * w);
            double temp = w;
            w = h;
            h = temp;
            r = Math.min(h / 3.0, w / (pi * 2.0));
            a = h - 2 * r;
            v = Math.max(v, pi * r * r * a);
            a = w;
            if (h >= w * (pi + 2)) {
                r = w / 2.0;
            } else if (h <= w * (pi + 1) / 2.0) {
                r = h / ((pi + 1) * 2.0);
            } else {
                r = h * (pi + 1);
                r += w;
                aa = ((2 * h * w * (pi + 1)) - (w * w * pi * (pi + 2)));
                if (aa >= 0) {
                    aa = Math.sqrt(aa);
                    r -= aa;
                    r /= (2 * (pi + 1) * (pi + 1));
                } else
                    r = 0;
                if (w > 4 * r || w < 2 * r) {
                    r = 0;
                }
            }
            if (v < pi * r * r * a)
                v = Math.max(v, pi * r * r * a);
            out.printLine(v);
        }
    }
}
