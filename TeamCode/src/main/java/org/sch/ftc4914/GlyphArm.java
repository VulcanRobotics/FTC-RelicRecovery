package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ron on 10/29/2017.
 */

public class GlyphArm {
    DcMotorEx elbow;
    Servo topGripper;

    public GlyphArm(HardwareMap hwMap) {
        elbow = hwMap.get(DcMotorEx.class, "elbow");
        topGripper = hwMap.get(Servo.class, "topGripper");
        elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void moveArm(double pwr) {
        elbow.setPower(pwr);
    }

    public int getPosition() {
        return elbow.getCurrentPosition();
    }

    public void openGripper() {
        topGripper.setPosition(.5);
    }

    public void closeGripper() {
        topGripper.setPosition(1.0);
    }
}
