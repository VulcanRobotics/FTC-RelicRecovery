package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Created by Ron on 10/29/2017.
 */

public class VladimirOmni implements Driveable {
    private DcMotorEx leftDriveOne, leftDriveTwo, rightDriveOne, rightDriveTwo;

    public VladimirOmni(HardwareMap hwMap) {

    }

    @Override
    public void omniDrive(double yIn, double xIn) {

    }

    // do nothing, since this class provides Omni (differential) drive
    @Override
    public void mecanumDrive(double yIn, double xIn, double rotIn) {

    }
}
