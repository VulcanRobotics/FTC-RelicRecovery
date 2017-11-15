package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.sch.ftc4914.VladimirEye;

/**
 * Created by Ron on 11/11/2017.
 */
@TeleOp(name = "VuforiaAndOCV")

public class VuforiaAndOCV extends OpMode {
    private VladimirEye eye;

    @Override
    public void init() {
        telemetry.addData("Status:","init() Starting");
        eye = new VladimirEye(hardwareMap, VuforiaLocalizer.CameraDirection.FRONT);
    }

    @Override
    public void start() {
        eye.startLooking();
    }

    @Override
    public void loop() {
        telemetry.addData("VuMark: ", eye.getPictograph());
    }
}
