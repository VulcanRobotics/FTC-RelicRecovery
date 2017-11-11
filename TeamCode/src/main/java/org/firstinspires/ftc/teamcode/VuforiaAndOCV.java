package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Ron on 11/11/2017.
 */
@TeleOp(name = "VuforiaAndOCV")

public class VuforiaAndOCV extends OpMode {
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables pictographTrackables;
    private VuforiaTrackable pictographTemplate;
    private RelicRecoveryVuMark pictograph;

    @Override
    public void init() {
        telemetry.addData("Status:","init() Starting");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZl2c9X/////AAAAGbmMYWHiOErNgvxJkJoCOPVIXkX6NaLEPKi2f8UqbBQsBcb2ZpS6I3XlTgSPcZubKpQUcjByBtH6AbLGb7iqNcCwn8q4wqOJnZMmKMvnmZaCaGzF95/SOg05pk1bOoavzjOtD3AR8eeQsxWIQSVU7SwqxSWu4tXpClNY0+82DMI7Itz8RiZP/y7tJZ66LMhe5YOiKeBaTuPP/UoKjZQl9gtUWwhU0n79n47An+LywGjWkIVqaZsv28XjFt66dql+ZtmpC/uqadfWc+FYwxyypGmAvcjkpeAmQEABmjtOMCHjk0xgDZjKZ4hNK2k18rMKROwo/R7lWRU/D2DRGUyF1W1deELVLVemkAJG8AnJlHn/";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);

    }

    @Override
    public void loop() {

    }
}
