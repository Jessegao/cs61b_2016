/**
 * Created by jgao on 4/28/2016.
 */
public class AcceleratingSawToothGenerator implements Generator{
    private int state;
    private int period;
    private double modifier;
    public AcceleratingSawToothGenerator(int period, double modifier) {
        state = 0;
        this.period = period;
        this.modifier = modifier;
    }

    public double next() {
        double returnValue = normalize(state);
        if (state == period) {
            state = (state + 1) % period;
            period = (int) (((double) period) * modifier);
        } else {
            state ++;
        }

        return returnValue;
    }

    private double normalize(int state) {
        return - 1.0 + (double) state / (double) period;
    }
}
