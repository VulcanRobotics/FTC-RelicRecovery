package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


/**
 * Created by Ron on 10/29/2017.
 */

public class VladimirOmni implements Driveable {
    private DcMotorEx leftDriveOne, leftDriveTwo, rightDriveOne, rightDriveTwo;
    private double leftDriveMax, rightDriveMax;
    private double[] maxWheelSpeeds = new double[2];

    public VladimirOmni(HardwareMap hwMap) {
        leftDriveOne = hwMap.get(DcMotorEx.class, "leftDrive1");
        leftDriveTwo = hwMap.get(DcMotorEx.class, "leftDrive2");
        rightDriveOne = hwMap.get(DcMotorEx.class, "rightDrive1");
        rightDriveTwo = hwMap.get(DcMotorEx.class, "rightDrive2");

        // use PID velocity control on the 1st motor on each side; we'll just use
        // the power setting to have the 2nd motor follow the 1st
        leftDriveOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftDriveOne.setDirection(DcMotor.Direction.FORWARD);
        leftDriveTwo.setDirection(DcMotor.Direction.FORWARD);
        rightDriveOne.setDirection(DcMotor.Direction.REVERSE);
        rightDriveTwo.setDirection(DcMotor.Direction.REVERSE);
    }

    public double[] maxWheelSpeeds() {
        leftDriveOne.setPower(1.0);
        leftDriveTwo.setPower(1.0);
        rightDriveOne.setPower(1.0);
        rightDriveTwo.setPower(1.0);

        maxWheelSpeeds[0] = leftDriveOne.getVelocity(AngleUnit.RADIANS);
        maxWheelSpeeds[1] = rightDriveOne.getVelocity(AngleUnit.RADIANS);
        return maxWheelSpeeds;
    }

    @Override
    public void omniDrive(double yIn, double xIn) {
        double leftVelocity    = Range.clip(yIn + xIn, -1.0, 1.0) * leftDriveMax;
        double rightVelocity   = Range.clip(yIn - xIn, -1.0, 1.0) * rightDriveMax;
        leftDriveOne.setVelocity(leftVelocity, AngleUnit.RADIANS);
        rightDriveOne.setVelocity(rightVelocity, AngleUnit.RADIANS);
        leftDriveTwo.setPower(leftDriveOne.getPower());
        rightDriveTwo.setPower(rightDriveOne.getPower());
    }

    // do nothing, since this class provides Omni (differential) drive
    @Override
    public void mecanumDrive(double yIn, double xIn, double rotIn) {

    }
}
