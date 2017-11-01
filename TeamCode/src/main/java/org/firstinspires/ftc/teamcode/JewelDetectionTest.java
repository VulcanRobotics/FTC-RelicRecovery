package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.sch.ftc4914.ColorSensorLeg;

/**
 * Created by Ron on 10/31/2017.
 */
@Autonomous(name = "Jewel Detection Test")

public class JewelDetectionTest extends OpMode {
    ColorSensorLeg leg;
    String jewelString;
    float[] hsvValues = {0F, 0F, 0F};

    @Override
    public void init() {
        jewelString = "NOTHING!";
        leg = new ColorSensorLeg(hardwareMap);
    }

    @Override
    public void start() {
        leg.extendLeg();
    }

    @Override
    public void loop() {
        if (leg.blueJewelDetected())
            jewelString = "BLUE JEWEL!";
        else if (leg.redJewelDetected())
            jewelString = "RED JEWEL!";
        else
            jewelString = "NOTHING!";

        Color.RGBToHSV(leg.getRed(), leg.getGreen(), leg.getBlue(), hsvValues);
        telemetry.addData("Jewel", jewelString);
        telemetry.addData("Colors", "Blue: " + leg.getBlue() + " Red: " + leg.getRed());
        telemetry.addData("HSV", "Hue: " + hsvValues[0] + " Sat: " + hsvValues[1] + " Val: " + hsvValues[2]);
    }

    @Override
    public void stop() {
        leg.retractLeg();
    }
}
