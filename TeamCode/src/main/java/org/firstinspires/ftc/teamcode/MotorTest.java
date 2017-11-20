package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sun.tools.javac.code.Symbol;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
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
    double Velocity =0;

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
            Velocity = gamepad1.left_stick_y;
            omniDrive.omniDrive(Velocity,0);
            telemetry.addData("Left", "Vel: " + omniDrive.getLeftVel() + " Pos: " + omniDrive.getLeftPos());
            telemetry.addData("Right", "Vel: " + omniDrive.getRightVel() + " Pos: " + omniDrive.getRightPos());

        }
    }
}
