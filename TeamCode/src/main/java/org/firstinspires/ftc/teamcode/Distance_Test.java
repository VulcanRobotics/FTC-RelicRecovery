package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.robocol.PeerDiscovery;

import org.sch.ftc4914.VladimirOmni;

/**
 * Created by ischapiro on 11/15/17.
 */

@Autonomous (name="Distance_Test")
public class Distance_Test extends OpMode{
    VladimirOmni robotDrive;
    private int currentDistanceAttempt = 0;
    private int stepNumber = 12;
    private int loopCounter = 0;

    @Override
    public void init() {
        robotDrive = new VladimirOmni(hardwareMap);
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        switch (stepNumber){
            case 12:
                currentDistanceAttempt = 12;
                robotDrive.distanceDrive(0.5, 12, 12);
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
                currentDistanceAttempt = 24;
                robotDrive.distanceDrive(0.5, 24, 24);
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
                currentDistanceAttempt = 36;
                robotDrive.distanceDrive(0.5, 36, 36);
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
                    stepNumber = 50;
                }
                break;
            case 50:
                robotDrive.omniDrive(0,0);
                break;
            default:
                break;
        }
        if(!robotDrive.isBusy()){
            robotDrive.omniDrive(0,0);
        }
        telemetry.addData("rightTargetDistance: ", robotDrive.getRightTarget());
        telemetry.addData("rightDistance: ", robotDrive.getRightPos());
        telemetry.addData("leftTargetDistance: ", robotDrive.getLeftTarget());
        telemetry.addData("leftDistance: ", robotDrive.getLeftPos());
        telemetry.addData("number of calls: ", robotDrive.numCalls);
        telemetry.addData("Current Distance Drive Attempt: ", currentDistanceAttempt);
    }


}
