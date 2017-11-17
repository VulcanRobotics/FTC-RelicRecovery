package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by eshoup on 11/16/17.
 */

@TeleOp(name= "Omni Drive",group="Iterative Opmode")

public class OmniDrivePrototype extends OpMode {
    private DcMotor rightDrive;
    private DcMotor leftDrive;
    private DcMotor elevator;
    private Servo gripper;



    @Override
    public void init() {
        rightDrive =hardwareMap.get(DcMotor.class,"rightDrive");
        leftDrive =hardwareMap.get(DcMotor.class,"leftDrive");
        elevator =hardwareMap.get(DcMotor.class,"elevator");
        gripper =hardwareMap.get(Servo.class,"gripper");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
    }


    @Override
    public void loop() {
        double yIn=gamepad1.left_stick_y;
        double xIn=gamepad1.right_stick_x;
        double leftVelocity    = Range.clip(yIn + xIn, -1.0, 1.0);
        double rightVelocity   = Range.clip(yIn - xIn, -1.0, 1.0);
        if(gamepad2.x == true){
            gripper.setPosition(0.5);
            //bottomGripper.setPosition(0);
        }if(gamepad2.b == true) {
            gripper.setPosition(0);
            //bottomGripper.setPosition(45);
        }
        double armCommand = Math.pow(gamepad2.left_stick_y,3);
        leftDrive.setPower(leftVelocity);
        rightDrive.setPower(rightVelocity);
        elevator.setPower(armCommand);
    }
}