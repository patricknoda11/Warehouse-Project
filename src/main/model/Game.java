package model;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.List;

public class Game {
    public static final int SCREEN_WIDTH = 200;
    public static final int SCREEN_HEIGHT = 500;


    // MODIFIES: this
    // EFFECTS: initiates game by creating new list of meteorites
    //          instantiates new rocket and sets its position in middle of screen
    //          sets isGameOver to false
    public Game() {
        // stub
    }

    // getters
    public boolean getIsGameOver() {
        return false; // stub
    }

    public Rocket getRocket() {
        return null; // stub
    }

    public List<Meteorite> getMeteorites() {
        return null; // stub
    }

    // REQUIRES: rocket must not be null
    // MODIFIES: rocket
    // EFFECTS: restricts rocket to remain within horizontal boundary
    private void constrainRocketInBoundary(Rocket rocket) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: updates the rocket and meteorites
    public void onTick() {

    }

    // REQUIRES: rocket must not be null
    // MODIFIES: rocket
    // EFFECTS: returns the next rocket by adding horizontal speed to its current horizontal position
    private Rocket nextRocket(Rocket rocket) {
        return null; // stub
    }

    // REQUIRES: meteorite must not be null
    // MODIFIES: meteorite
    // EFFECTS: returns the next meteorite by adding vertical speed to its current vertical position
    private Meteorite nextMeteorite(Meteorite meteorite) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: removes meteorites that are out of boundary
    private void removeOutOfBoundaryMeteorites() {

    }

    // EFFECTS: returns true if any of the meteorites have hit the rocket, false otherwise
    private boolean hasMeteoriteHitRocket() {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: if meteorite has hit the rocket, end the game and clear all meteorites
    private void gameOver() {

    }

}
