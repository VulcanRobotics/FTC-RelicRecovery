package org.sch.ftc4914;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.configuration.ServoConfiguration;

/**
 * Created by Ron on 10/29/2017.
 */

public class ColorSensorLeg {
    Servo knee;
    ColorSensor colorSensor;

    public ColorSensorLeg(HardwareMap hwMap) {
        knee = hwMap.get(Servo.class, "knee");
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color_distance");
        colorSensor.enableLed(false);
    }

    /*public boolean noJewelDetected() {
        float hue = getHue();
        if(hue > 15 && hue <35) return true;
        return false;
    }*/

    public boolean blueJewelDetected() {
        // blue std value is ~34
        // blue 53 red 26
        //if (colorSensor.blue() >= 50 && colorSensor.red() <= 25) return true;
        // Hue >=180 && <=300
        float hue = getHue();
        if (hue > 140.0 && hue < 300.0 || (hue > 35 && hue < 70)) return true;
        return false;
    }

    public boolean redJewelDetected() {
        // red std value is ~71
        // blue 15, red 75
        //if (colorSensor.blue() <= 25 && colorSensor.red() >= 75) return true;
       // Hue > 300 || < 60
        float hue = getHue();
        if (hue > 300.0 || hue < 15) return true;
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

    public float getHue() {
        float hsvValues[] = { 0F, 0F, 0F};
        Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues);
        return hsvValues[0];
    }

    public void extend() {
        knee.setPosition(0.87 );

        colorSensor.enableLed(true);
    }

    public void home() {
        knee.setPosition(0.3);
        colorSensor.enableLed(false);
    }

    public void homeReconfigured(){
        knee.setPosition(0.0);
        colorSensor.enableLed(false);
    }

}
