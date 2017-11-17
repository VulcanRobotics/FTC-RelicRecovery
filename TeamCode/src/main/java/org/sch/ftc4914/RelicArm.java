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

    public  RelicArm(HardwareMap hwMap){
        relicArm = hwMap.get(DcMotorEx.class, "relicArm");
    }

    public void pushPullArms(double pwr) {
        if (pwr >= .8) pwr = .8;
        if (pwr <= -.8) pwr = -.8;

        if (pwr == 0){
            relicArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            relicArm.setTargetPosition(getPosition());
        }
    }


    public int getPosition(){
        return relicArm.getCurrentPosition();
    }




    /*public void moveArm(double pwr) {
        if (pwr >= .8) pwr = .8;
        if (pwr <= -.8) pwr = -.8;

        if (pwr == 0 && !holdArm){
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setTargetPosition(getPosition());
            holdArm = true;
        }else if(pwr != 0){
            holdArm = false;
            elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elbow.setPower(pwr);
        }
    }

    (public int getPosition() {
        return elbow.getCurrentPosition();
    }

    public void openGripper() {
        leftGripper.setPosition(0.3);
        rightGripper.setPosition(0.7);
    }

    public void closeGripper() {
        leftGripper.setPosition(1);
        rightGripper.setPosition(0);
    }*/
}
