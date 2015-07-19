package Tasks;

public class PaperAndPaintEasy {
    public long computeArea(int width, int height, int xfold, int cnt, int x1, int y1, int x2, int y2) {
        long dw;
        if(xfold <= width / 2) {
            dw = xfold;
        } else {
            xfold = width - xfold;
            dw = xfold;
        }
        long area = 0;
        if(x1 < dw) area += Math.abs(y2 - y1) * (dw - x1) * 2;
        else area -= (y2 - y1) * (x1 - dw);
        if(x2 > dw) area += Math.abs(y2 - y1) * (x2 - dw);
        else area -= (y2 - y1) * (dw - x2) * 2;
        long ans = area * (cnt + 1);
        return (long)width * (long) height - ans;
    }
}
