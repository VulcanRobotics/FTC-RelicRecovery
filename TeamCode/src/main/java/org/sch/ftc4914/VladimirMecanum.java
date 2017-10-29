package org.sch.ftc4914;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ron on 10/29/2017.
 */

public class VladimirMecanum extends DriveBase {
    DcMotorEx frontLeft, frontRight, rearLeft, rearRight;

    public VladimirMecanum(HardwareMap hwMap) {
        super(hwMap);

    }

    @Override
    public void drive(double xIn, double yIn, double rotation) {

    }
}
