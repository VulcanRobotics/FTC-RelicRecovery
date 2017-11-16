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

public class FourBarArm {
    DcMotorEx elbow;
    Servo leftGripper;
    Servo rightGripper;

    public FourBarArm(HardwareMap hwMap) {
        elbow = hwMap.get(DcMotorEx.class, "elbow");
        leftGripper = hwMap.get(Servo.class, "leftGripper");
        rightGripper = hwMap.get(Servo.class, "rightGripper");
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
        leftGripper.setPosition(0.3);
        rightGripper.setPosition(0.7);
    }

    public void closeGripper() {
        leftGripper.setPosition(1);
        rightGripper.setPosition(0);
    }
}
