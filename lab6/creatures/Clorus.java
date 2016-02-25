package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/**
 * Created by cs61b-bog on 2/25/16.
 */
public class Clorus extends Creature {
    private int r;
    private int b;
    private int g;
    private static final double moveEnergy = 0.03;
    private static final double stayEnergy = 0.01;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r, g, b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        energy -= moveEnergy;
    }

    public void stay() {
        energy -= stayEnergy;
    }

    public Clorus replicate() {
        energy = energy/2.0;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Action act;
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if(empties.size() == 0) {
            act = new Action(Action.ActionType.STAY);
        } else if(getNeighborsOfType(neighbors, "plip").size() > 0) {
            Direction attackDir = HugLifeUtils.randomEntry(getNeighborsOfType(neighbors, "plip"));
            act = new Action(Action.ActionType.ATTACK, attackDir);
        } else if(energy >= 1.0){
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            act = new Action(Action.ActionType.REPLICATE, moveDir);
        } else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            act = new Action(Action.ActionType.MOVE, moveDir);
        }
        return act;
    }
}
