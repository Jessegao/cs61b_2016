/**
 * Created by jgao on 4/28/2016.
 */
public class SawToothGenerator implements Generator {
    private int state;
    private int period;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        double returnValue = normalize(state);
        state = (state + 1) % period;
        return returnValue;
    }

    private double normalize(int state) {
        return - 1.0 + (double) state / (double) period;
    }
}
