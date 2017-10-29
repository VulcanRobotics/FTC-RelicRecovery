package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by Ron on 10/29/2017.
 */

public class VladimirMecanum implements Driveable {
    DcMotorEx frontLeftDrive, frontRightDrive, rearLeftDrive, rearRightDrive;

    // frontLeft, frontRight, rearLeft, rearRight
    double[] wheelSpeeds,
             wheelMaxVelocities = {0.0,                 // frontLeft max in RADIANS
                                    0.0,                // frontRight max in RADIANS
                                    0.0,                // rearLeft max in RADIANS
                                    0.0};               // rearRight max in RADIANS

    public VladimirMecanum(HardwareMap hwMap) {
        frontLeftDrive = hwMap.get(DcMotorEx.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotorEx.class, "frontRightDrive");
        rearLeftDrive = hwMap.get(DcMotorEx.class, "rearLeftDrive");
        rearRightDrive = hwMap.get(DcMotorEx.class, "rearRightDrive");

        // use PID velocity control
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // set motor directions
        rearLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        rearRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);

        wheelSpeeds = new double[4];
    }

    public double[] maxWheelSpeeds() {
        frontLeftDrive.setPower(1.0);
        frontRightDrive.setPower(1.0);
        rearLeftDrive.setPower(1.0);
        rearRightDrive.setPower(1.0);

        wheelMaxVelocities[0] = frontLeftDrive.getVelocity(AngleUnit.RADIANS);
        wheelMaxVelocities[1] = frontRightDrive.getVelocity(AngleUnit.RADIANS);
        wheelMaxVelocities[2] = rearLeftDrive.getVelocity(AngleUnit.RADIANS);
        wheelMaxVelocities[3] = rearRightDrive.getVelocity(AngleUnit.RADIANS);

        return wheelMaxVelocities;
    }


    @Override
    public void mecanumDrive(double yIn, double xIn, double rotIn) {
        // cartesian mecanum drive
        // these equations yield a % power value in range (-1.0, 1.0)
        wheelSpeeds[0] = xIn + yIn + rotIn;     // frontLeft
        wheelSpeeds[1] = -xIn + yIn - rotIn;    // frontRight
        wheelSpeeds[2] = -xIn + yIn + rotIn;    // rearLeft
        wheelSpeeds[3] = xIn + yIn - rotIn;     // rearRight

        // normalize all speeds if any power exceeds 100% in either forward or reverse
        double maxPower = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (temp > maxPower) maxPower = temp;
        }
        if (maxPower > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] /= maxPower;
            }
        }

        // set motor powers
        for (int i = 0; i < wheelSpeeds.length; i++) {
            wheelSpeeds[i] *= wheelMaxVelocities[i];
        }

        frontLeftDrive.setVelocity(wheelSpeeds[0], AngleUnit.RADIANS);
        frontRightDrive.setVelocity(wheelSpeeds[1], AngleUnit.RADIANS);
        rearLeftDrive.setVelocity(wheelSpeeds[2], AngleUnit.RADIANS);
        rearRightDrive.setVelocity(wheelSpeeds[3], AngleUnit.RADIANS);
    }

    @Override
    public void omniDrive(double yIn, double xIn) {
        // do nothing, since this class only provides mecanumDrive
    }

}
