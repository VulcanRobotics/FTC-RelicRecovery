package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.sch.ftc4914.VladimirEye;
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by Ron on 11/13/2017.
 */

/* This class provides vision functionality:
    - Vuforia VuMark detection for pictograph
    - OpenCV color blob detection for jewel (potential future addition)
*/
@Autonomous(name="Vladimir Eye Testing")
public class VladimirEyeTester extends OpMode{
    private VladimirOmni robotDrive;
    private VladimirEye robotEye;
    private ElapsedTime runtime = new ElapsedTime();

    private int loopCounter;

    @Override
    public void init() {
        robotEye = new VladimirEye(hardwareMap, VuforiaLocalizer.CameraDirection.BACK);
        telemetry.addData("Status: ", "Initialized");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        robotEye.startLooking();
        telemetry.addData("Status: ", "Looking....");
    }

    @Override
    public void loop() {
        telemetry.addData("Pictograph: ", robotEye.getPictograph());
    }


}
