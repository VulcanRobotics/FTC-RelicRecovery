package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by hlexer on 10/27/17.
 */

/*TODO
Put out leg
Use color sensor to scan jewel
If it it the right color, knock it over
If it is not the right color, knock the other color over



 */
@Autonomous(name = "Jewel Auton")

public class JewelAuton extends OpMode {
    ColorSensor sensorColor;
    int stepNumber = 0;
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void init() {
        stepNumber = 0;
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
    }
    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        runtime.start();
    }

    @Override
    public void loop() {

    }
}
