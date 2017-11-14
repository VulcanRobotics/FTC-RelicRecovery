package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ron on 10/29/2017.
 */

/* elbow encoder readings:
    - positive elbow command increases encoder, and rotates arm towards front of robot
    - negative elbow command decreases encoder, and rotates arm towards rear of robot
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
        if (pwr >= .8) pwr = .8;
        if (pwr <= -.8) pwr = -.8;
        elbow.setPower(pwr);
    }

    public int getPosition() {
        return elbow.getCurrentPosition();
    }

    public void openGripper() {
        topGripper.setPosition(.45);
    }

    public void closeGripper() {
        topGripper.setPosition(.8);
    }
}
