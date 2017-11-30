package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.OmniDrive;

/**
 * Created by Zach on 11/17/2017.
 */

/* This program is designed to get the relic into the second zone.
   The motor has to rotate in the positive direction in order to pull the string in, which
 */

public class RelicArm {
    DcMotorEx relicArm;
    Servo relicWrist;

    public RelicArm(HardwareMap hwMap){
        relicArm = hwMap.get(DcMotorEx.class, "relicArm");
        relicWrist = hwMap.get(Servo.class, "relicWrist");
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
    public void pushOutArm(){
        relicArm.setTargetPosition(-1); //This pushes out the arm
    }

    public void pullOutArm(){
        relicArm.setTargetPosition(-1); //This pulls in the arm
        //unfortunately there is not a current program that is able pull in. This may be with a diff. command,
        //or it may just happen with rubber bands.
    }

    public void openWrist(){
        relicWrist.setPosition(1);
    }

    public void closeWrist(){
        relicWrist.setPosition(-1);
    }



/*
 package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


    public class FourBarArm {
        DcMotorEx elbow;
        Servo leftGripper;
        Servo rightGripper;
        boolean holdArm;

        public FourBarArm(HardwareMap hwMap) {
            elbow = hwMap.get(DcMotorEx.class, "elbow");
            leftGripper = hwMap.get(Servo.class, "leftGripper");
            rightGripper = hwMap.get(Servo.class, "rightGripper");
            elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        public void moveArm(double pwr) {
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

        public int getPosition() {
            return elbow.getCurrentPosition();
        }

        public void openGripper() {
            leftGripper.setPosition(0.43);
            rightGripper.setPosition(0.6);
        }

        public void closeGripper() {
            leftGripper.setPosition(1);
            rightGripper.setPosition(0);
        }

        public void lockedOpenGrippers(){
            leftGripper.setPosition(0.43);
            rightGripper.setPosition(0.5);
        }

        public void lockedClosedGrippers(){
            leftGripper.setPosition(1);
            rightGripper.setPosition(0.5);
        }
    }
*/
}
