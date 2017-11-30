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
    private VuforiaLocalizer vuforia;
    private VladimirOmni robotDrive;
    private HardwareMap hwMap;
    private VladimirEye robotEye;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicPictograph;
    private ElapsedTime runtime = new ElapsedTime();
    public VladimirEyeTester(HardwareMap hw, VuforiaLocalizer.CameraDirection cameraDirection) {
        hwMap = hw;
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZl2c9X/////AAAAGbmMYWHiOErNgvxJkJoCOPVIXkX6NaLEPKi2f8UqbBQsBcb2ZpS6I3XlTgSPcZubKpQUcjByBtH6AbLGb7iqNcCwn8q4wqOJnZMmKMvnmZaCaGzF95/SOg05pk1bOoavzjOtD3AR8eeQsxWIQSVU7SwqxSWu4tXpClNY0+82DMI7Itz8RiZP/y7tJZ66LMhe5YOiKeBaTuPP/UoKjZQl9gtUWwhU0n79n47An+LywGjWkIVqaZsv28XjFt66dql+ZtmpC/uqadfWc+FYwxyypGmAvcjkpeAmQEABmjtOMCHjk0xgDZjKZ4hNK2k18rMKROwo/R7lWRU/D2DRGUyF1W1deELVLVemkAJG8AnJlHn/";
        parameters.cameraDirection = cameraDirection;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicPictograph = relicTrackables.get(0);
        relicPictograph.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
    }
    private int loopCounter;

    @Override
    public void init() {
        robotEye = new VladimirEye(hardwareMap, VuforiaLocalizer.CameraDirection.FRONT);
    }

    @Override
    public void init_loop() {
        telemetry.addData("Basic Text 1", "Basic Text 2");
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        startLooking();
        if (++loopCounter >= 20) {
            loopCounter = 0;
        }
        stopLooking();
    }

    public void startLooking() {
        relicTrackables.activate();
    }

    public void stopLooking() {
        relicTrackables.deactivate();
    }

    public RelicRecoveryVuMark getPictograph() {
        return RelicRecoveryVuMark.from(relicPictograph);
    }

}
