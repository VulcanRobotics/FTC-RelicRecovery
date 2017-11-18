package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sun.tools.javac.code.Symbol;

import org.sch.ftc4914.ColorSensorLeg;
import org.sch.ftc4914.Driveable;
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by agoldberg on 9/26/17.
 */
@TeleOp(name = "MotorTest")
public class MotorTest extends OpMode {
    VladimirOmni omniDrive;
    ColorSensorLeg leg;
    double[] wheelSpeeds;

    @Override
    public void init() {
        leg = new ColorSensorLeg(hardwareMap);
        omniDrive = new VladimirOmni(hardwareMap);
    }

    @Override
    public void start() {
        leg.home();
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            wheelSpeeds = omniDrive.maxWheelSpeeds();
            telemetry.addData("Left", "Vel: " + wheelSpeeds[0] + " Pos: " + omniDrive.getLeftPos());
            telemetry.addData("Right", "Vel: " + wheelSpeeds[1] + " Pos: " + omniDrive.getRightPos());

        } else {
            omniDrive.omniDrive(0,0);
        }
    }
}
