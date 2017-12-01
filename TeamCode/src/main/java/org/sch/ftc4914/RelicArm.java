package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Zach on 11/17/2017.
 */

/* This program is designed to get the relic into the second zone.
   The motor has to rotate in the positive direction in order to pull the string in, which
 */

public class RelicArm {
    DcMotorEx relicArm;
    Servo relicWrist;

    public RelicArm(HardwareMap hwMap) {
        relicArm = hwMap.get(DcMotorEx.class, "relicArm");
        relicWrist = hwMap.get(Servo.class, "relicWrist");
    }
    /*
    public void pushPullArms(double pwr) {
        if (pwr >= .8) pwr = .8;
        if (pwr <= -.8) pwr = -.8;

        if (pwr == 0){
            relicArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            relicArm.setTargetPosition(getPosition());
        }
    }*/


    public int getPosition() {
        return relicArm.getCurrentPosition();
    }

    public void pushOutArm() {
        relicArm.setTargetPosition(-1); //This pushes out the arm
    }

    public void pullOutArm() {
        relicArm.setTargetPosition(-1); //This pulls in the arm
        //unfortunately there is not a current program that is able pull in. This may be with a diff. command,
        //or it may just happen with rubber bands.
    }

    public void openWrist() {
        relicWrist.setPosition(1);
    }

    public void closeWrist() {
        relicWrist.setPosition(0);
    }


}