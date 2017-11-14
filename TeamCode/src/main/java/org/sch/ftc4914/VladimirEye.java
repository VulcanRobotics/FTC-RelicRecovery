package org.sch.ftc4914;

import android.support.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.TrackableResult;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Ron on 11/13/2017.
 */

/* This class provides vision functionality:
    - Vuforia VuMark detection for pictograph
    - OpenCV color blob detection for jewel (potential future addition)
*/

public class VladimirEye {
    private VuforiaLocalizer vuforia;
    private HardwareMap hwMap;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicPictograph;

    public VladimirEye(HardwareMap hw) {
        hwMap = hw;
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZl2c9X/////AAAAGbmMYWHiOErNgvxJkJoCOPVIXkX6NaLEPKi2f8UqbBQsBcb2ZpS6I3XlTgSPcZubKpQUcjByBtH6AbLGb7iqNcCwn8q4wqOJnZMmKMvnmZaCaGzF95/SOg05pk1bOoavzjOtD3AR8eeQsxWIQSVU7SwqxSWu4tXpClNY0+82DMI7Itz8RiZP/y7tJZ66LMhe5YOiKeBaTuPP/UoKjZQl9gtUWwhU0n79n47An+LywGjWkIVqaZsv28XjFt66dql+ZtmpC/uqadfWc+FYwxyypGmAvcjkpeAmQEABmjtOMCHjk0xgDZjKZ4hNK2k18rMKROwo/R7lWRU/D2DRGUyF1W1deELVLVemkAJG8AnJlHn/";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicPictograph = relicTrackables.get(0);
        relicPictograph.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
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
