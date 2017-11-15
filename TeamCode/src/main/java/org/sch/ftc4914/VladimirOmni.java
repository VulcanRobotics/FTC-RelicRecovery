package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


/**
 * Created by Ron on 10/29/2017.
 */

/*
    both sides' encoders increase with rotation towards front of robot (probably because of
    how the direction is set in the constructor
    Looks like 1080 encoder ticks per wheel rev
 */

public class VladimirOmni implements Driveable {
    private DcMotorEx leftDriveOne, leftDriveTwo, rightDriveOne, rightDriveTwo;
    private double leftDriveMax = 1.0, rightDriveMax = 1.0;
    private double[] maxWheelSpeeds = new double[2];
    private enum DriveMode {POWER, DISTANCE, VELOCITY};
    private DriveMode currentMode;
    public int numCalls = 0;

    // encoder counts per inch of travel is the number of encoder ticks per wheel rev, multiplied
    // by the circumference of the wheel.  Circumference is (pi * diameter)
    private int countsPerInch = (int)(1080.0 /  // encoder ticks per wheel rev)
            (4.0 * Math.PI));    // wheel diameter

    public VladimirOmni(HardwareMap hwMap) {
        leftDriveOne = hwMap.get(DcMotorEx.class, "leftDrive1");
        leftDriveTwo = hwMap.get(DcMotorEx.class, "leftDrive2");
        rightDriveOne = hwMap.get(DcMotorEx.class, "rightDrive1");
        rightDriveTwo = hwMap.get(DcMotorEx.class, "rightDrive2");

        // in future tru using PID velocity control on the 1st motor on each side; we'll just use
        // the power setting to have the 2nd motor follow the 1st.  For now just normally run at power
        currentMode = DriveMode.POWER;
        leftDriveOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftDriveOne.setDirection(DcMotor.Direction.FORWARD);
        leftDriveTwo.setDirection(DcMotor.Direction.FORWARD);
        rightDriveOne.setDirection(DcMotor.Direction.REVERSE);
        rightDriveTwo.setDirection(DcMotor.Direction.REVERSE);
    }

    public boolean isBusy() {
        return leftDriveOne.isBusy() || rightDriveOne.isBusy();
    }

    public void distanceDrive(double speed, int leftInches, int rightInches) {
        numCalls += 1;
        int newLeftTarget = leftDriveOne.getCurrentPosition() + (leftInches * countsPerInch),
            newRightTarget = rightDriveOne.getCurrentPosition() + (rightInches * countsPerInch);

        leftDriveOne.setTargetPosition(newLeftTarget);
        rightDriveOne.setTargetPosition(newRightTarget);
        if (currentMode != DriveMode.DISTANCE) {
            leftDriveOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDriveOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            currentMode = DriveMode.DISTANCE;
        }

        leftDriveOne.setPower(speed);
        leftDriveTwo.setPower(Math.copySign(speed,leftInches));
        rightDriveOne.setPower(speed);
        rightDriveTwo.setPower(Math.copySign(speed,rightInches));
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

    public int getLeftPos() {
        return leftDriveOne.getCurrentPosition();
    }

    public int getRightPos() {
        return rightDriveOne.getCurrentPosition();
    }

    @Override
    public void omniDrive(double yIn, double xIn) {
        xIn = Math.copySign(xIn * xIn, xIn);
        yIn = Math.copySign(yIn * yIn, yIn);
        double leftVelocity    = Range.clip(yIn + xIn, -1.0, 1.0) * leftDriveMax;
        double rightVelocity   = Range.clip(yIn - xIn, -1.0, 1.0) * rightDriveMax;
        if (currentMode != DriveMode.POWER) {
            leftDriveOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightDriveOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        leftDriveOne.setPower(leftVelocity);
        rightDriveOne.setPower(rightVelocity);
        leftDriveTwo.setPower(leftDriveOne.getPower());
        rightDriveTwo.setPower(rightDriveOne.getPower());
    }

    public int getLeftTarget(){
        return leftDriveOne.getTargetPosition();
    }

    public int getRightTarget() {
        return rightDriveOne.getTargetPosition();
    }

    // do nothing, since this class provides Omni (differential) drive
    @Override
    public void mecanumDrive(double yIn, double xIn, double rotIn) {

    }
}
