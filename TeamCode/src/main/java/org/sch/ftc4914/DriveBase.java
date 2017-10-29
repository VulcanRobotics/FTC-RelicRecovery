package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ron on 10/27/2017.
 */

public abstract class DriveBase {
    private HardwareMap hwMap;

    public DriveBase(HardwareMap hwMap) {
        this.hwMap = hwMap;
    }

    // two-parameter drive method, to implement tank/arcade drive on a differential drive base
    // yIn represents the forward/reverse translation, xIn represents the turn
    //      on a gamepad drive, yIn is the right stick yAxis, xIn is the left stick xAxis
    public void drive(double yIn, double xIn) {

    }

    // three-parameter drive method, to implement cartesian drive on a mecanum drive base
    public void drive(double xIn, double yIn, double rotation) {

    }
}
