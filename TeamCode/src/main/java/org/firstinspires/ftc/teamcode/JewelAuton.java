package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.sch.ftc4914.ColorSensorLeg;
import org.sch.ftc4914.Driveable;
import org.sch.ftc4914.VladimirMecanum;

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
    private int stepNumber = 0;
    private Driveable robotDrive;
    private ColorSensorLeg leg;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        stepNumber = 0;
        robotDrive = new VladimirMecanum(hardwareMap);
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

    }
}
