package org.sch.ftc4914;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ron on 10/29/2017.
 */

public class ColorSensorLeg {
    Servo knee;
    ColorSensor colorSensor;

    public ColorSensorLeg(HardwareMap hwMap) {
        knee = hwMap.get(Servo.class, "knee");
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color_distance");
    }

    public boolean blueJewelDetected() {
        return false;
    }

    public boolean redJewelDetected() {
        return false;
    }

    public int getRed() {
        return colorSensor.red();
    }

    public int getGreen() {
        return colorSensor.green();
    }

    public int getBlue() {
        return colorSensor.blue();
    }

    public void extendLeg() {
        knee.setPosition(1.0);
    }

    public void retractLeg() {
        knee.setPosition(0.0);
    }
}
