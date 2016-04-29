/**
 * Created by jgao on 4/29/2016.
 */
public class StrangeBitwiseGenerator implements Generator {
    private int state;
    private int period;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = state + 1;
        int weirdState = state & (state >> 3) & (state >> 8) % period;
        double returnValue = normalize(weirdState);
        return returnValue;
    }

    private double normalize(int state) {
        return - 1.0 + (double) (state % period) / (double) period;
    }
}
