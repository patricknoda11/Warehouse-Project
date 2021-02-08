package model;

import java.awt.*;

public class Rocket {
    public static final int SPEED = 2;
    public static final int VERTICAL_POSITION = 0;
    /**
     * Some Constants for rocket:
     * rocket image:
     * - height
     * - color
     * - width
     * rocket Vertical Position (y-coordinate)
     */

    // MODIFIES: this
    // EFFECTS: instantiates rocket object
    //          sets horizontal position to parameter value
    //          sets direction to going right
    //
    public Rocket(int horizontalPosition) {
        // stub
    }

    // getters
    public int getHorizontalPosition() {
        return 0; // stub
    }
    public boolean getIsGoingRight() {
        return false; // stub
    }

    // REQUIRES: horizontal position must be within the boundary of the game
    // MODIFIES: this
    // EFFECTS: sets this rocket's horizontal position to parameter value
    public void setHorizontalPosition(int horizontalPosition) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this rocket's direction to parameter value
    public void setIsMovingRight(boolean isMovingRight) {
        // stub
    }

}
