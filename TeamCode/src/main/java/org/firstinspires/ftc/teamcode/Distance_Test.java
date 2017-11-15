package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.sch.ftc4914.VladimirOmni;

/**
 * Created by ischapiro on 11/15/17.
 */

@Autonomous (name="Distance_Test")
public class Distance_Test extends OpMode{
    VladimirOmni robotDrive;

    @Override
    public void init() {
        robotDrive = new VladimirOmni(hardwareMap);
    }

    @Override
    public void start() {
        robotDrive.distanceDrive(0.5, 24, 24);
    }

    @Override
    public void loop() {
        if(!robotDrive.isBusy()){
            robotDrive.omniDrive(0,0);
        }
        telemetry.addData("rightTargetDistance: ", robotDrive.getRightTarget());
        telemetry.addData("rightDistance: ", robotDrive.getRightPos());
        telemetry.addData("leftTargetDistance: ", robotDrive.getLeftTarget());
        telemetry.addData("leftDistance: ", robotDrive.getLeftPos());
        telemetry.addData("number of calls: ", robotDrive.numCalls);
    }


}
