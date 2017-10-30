package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.sch.ftc4914.Driveable;
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by agoldberg on 9/26/17.
 */
@TeleOp(name = "MotorTest")
public class MotorTest extends OpMode {
    VladimirOmni omniDrive;
    double[] wheelSpeeds;

    @Override
    public void init() {
        omniDrive = new VladimirOmni(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            wheelSpeeds = omniDrive.maxWheelSpeeds();
            telemetry.addData("Left: ", wheelSpeeds[0]);
            telemetry.addData("Right: ", wheelSpeeds[1]);
        } else {
            omniDrive.omniDrive(0,0);
        }
    }
}
