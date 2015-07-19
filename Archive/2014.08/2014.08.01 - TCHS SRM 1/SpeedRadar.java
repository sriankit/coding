package Tasks;

public class SpeedRadar {
    public double averageSpeed(int minLimit, int maxLimit, int[] readings) {
        double sum = 0;
        int outlier = 0;
        for(int reading : readings) {
            if(minLimit > reading || maxLimit < reading) outlier ++;
            else sum += reading;
        }
        if(outlier > .1 * readings.length) return 0.0;
        return sum / (readings.length - outlier);
    }
}
