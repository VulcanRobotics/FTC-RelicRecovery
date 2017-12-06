package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.sch.ftc4914.VladimirOmni;

/**
 * Created by ischapiro on 11/15/17.
 */

@Autonomous (name="Turning_Test")
public class Turning_Test extends OpMode{
    private  VladimirOmni robotDrive;
    private int stepNumber = 12;
    private int loopCounter = 0;

    @Override
    public void init() {
        robotDrive = new VladimirOmni(hardwareMap);
    }

    /*
 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
 */
    @Override
    public void init_loop() {
        telemetry.addData("Wheels", "LtPos: " + robotDrive.getLeftPos() + " RtPos: " + robotDrive.getRightPos());
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        switch (stepNumber){
            case 12:
                robotDrive.rightAngleTurn(1);
                stepNumber += 1;
                break;
            case 13:
                if(!robotDrive.isBusy()){
                    robotDrive.omniDrive(0,0);
                    stepNumber += 1;
                }
                break;
            case 14:
                if (++loopCounter >= 20) {
                    loopCounter = 0;
                    stepNumber = 24;
                }
                break;
            case 24:
                robotDrive.rightAngleTurn(1);
                stepNumber += 1;
                break;
            case 25:
                if(!robotDrive.isBusy()){
                    robotDrive.omniDrive(0,0);
                    stepNumber += 1;
                }
                break;
            case 26:
                if (++loopCounter >= 20) {
                    loopCounter = 0;
                    stepNumber = 36;
                }
                break;
            case 36:
                robotDrive.rightAngleTurn(1);
                stepNumber += 1;
                break;
            case 37:
                if(!robotDrive.isBusy()){
                    robotDrive.omniDrive(0,0);
                    stepNumber += 1;
                }
                break;
            case 38:
                if (++loopCounter >= 20) {
                    loopCounter = 0;
                    stepNumber = 48;
                }
                break;
            case 48:
                robotDrive.rightAngleTurn(1);
                stepNumber += 1;
                break;
            case 49:
                if(!robotDrive.isBusy()){
                    robotDrive.omniDrive(0,0);
                    stepNumber += 1;
                }
                break;
            case 50:
                if (++loopCounter >= 20) {
                    loopCounter = 0;
                    stepNumber = 100;
                }
                break;
            case 100:
                robotDrive.omniDrive(0,0);
                break;
            default:
                break;
        }
        if(!robotDrive.isBusy()){
            robotDrive.omniDrive(0,0);
        }
        telemetry.addData("Left","Target: " + robotDrive.getLeftTarget() + " Actual: " + robotDrive.getLeftPos());
        telemetry.addData("Right","Target: " + robotDrive.getRightTarget() + " Actual: " + robotDrive.getRightPos());
        telemetry.addData("number of calls: ", robotDrive.numCalls);
        telemetry.addData("StepNumber: ", stepNumber);
    }


}
