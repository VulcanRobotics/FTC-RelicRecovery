package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.sch.ftc4914.ColorSensorLeg;
import org.sch.ftc4914.Driveable;
import org.sch.ftc4914.GlyphArm; //NEEDS TO BE LOOKED AT
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by hlexer on 10/27/17.
 */



/*TODO
Put out leg
Use color sensor to scan jewel
If it it the right color, knock it over
If it is not the right color, knock the other color over

COLOR SENSOR SENSES towards rear of robot, so:
- if we're red, and we sense red, we want to drive forward to knock blue away
- if we're red, and we sense blue, we want to drive reverse to knock blue away
- if we're blue, and we sense red, we want to drive reverse to knock red away
- if we're blue, and we sense blue, we want to drive forward to knock red away

*/
@Autonomous(name = "Jewel Auton Blue")

public class JewelAutonBlue extends OpMode {
    private int stepNumber = 0;
    private int loopCounter = 0;
    private Driveable robotDrive;
    private ColorSensorLeg leg;
    private ElapsedTime runtime = new ElapsedTime();
    private float[] hsvValues = {0F, 0F, 0F};
    private GlyphArm arm; //NEEDS TO BE LOOKED AT
    private String direction = "";
    private String armType = "classic"; //classic, 4-bar, or elevator
    String jewelString;

    @Override
    public void init() {
        stepNumber = 0;
        robotDrive = new VladimirOmni(hardwareMap);
        leg = new ColorSensorLeg(hardwareMap);
        jewelString = "NOTHING!";
        arm = new GlyphArm(hardwareMap); //NEEDS TO BE LOOKED AT
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        switch (stepNumber) {
            case 0: //Extends the leg to prepare to look at the jewel color
                if (loopCounter == 0)
                    leg.extendLeg();


                if (++loopCounter >= 10) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 1: //Senses the color of the jewel on the right side
/*
                COLOR SENSOR SENSES towards rear of robot, so:
                - since we're blue, if we sense red, we want to drive reverse to knock red away
                - since we're blue, if we sense blue, we want to drive forward to knock red away
*/
                if (leg.blueJewelDetected()) {
                    jewelString = "BLUE JEWEL!";
                    stepNumber = 10; // drive forward
                }
                if (leg.redJewelDetected()) {
                    jewelString = "RED JEWEL!";
                    stepNumber = 20; // drive reverse
                }
                break;
            case 10: // drive forward
                //leg.extendLeg();
                robotDrive.omniDrive(1.00, 0.0);
                direction = "forward";
                leg.halfRetractLeg();

                stepNumber = 50;  // pause and then stop robot
                break;
            case 20: // drive reverse
                //leg.extendLeg();
                robotDrive.omniDrive(-1.00,0);
                direction = "backward";
                leg.halfRetractLeg();

                stepNumber = 50; // pause and then stop robot
                break;
            case 50:            // pause
                if (++loopCounter >= 15) {
                    loopCounter = 0;
                    stepNumber +=1;
                }

                break;
            //NEEDS TO BE LOOKED AT
            case 51:
                //arm.moveArm(-0.5); //moves the arm to allow the leg to get folded back in
                leg.halfRetractLeg(); //folds leg back to top
                //arm.moveArm(0.5); //moves arm back to regular position
                if(direction == "forward"){
                    stepNumber += 9;
                }else {
                    stepNumber += 19;
                }
            case 60: //robot drives to safe zone (went forwards)
                robotDrive.omniDrive(-4.50, 0);

                stepNumber += 20;
            case 70: //robot drives to safe zone (went backwards)
                robotDrive.omniDrive(-2.50, 0);

                stepNumber += 10;
            case 80: //put glyph into cryptobox
                arm.moveArm(-1.0); //ready to drive into c.box
                robotDrive.omniDrive(1, -1); //turns robot?
                robotDrive.omniDrive(1, 0);

                stepNumber += 10;
            case 90: // stop robot
                robotDrive.omniDrive(0,0);
                break;


            default:
                break;
        }
        Color.RGBToHSV(leg.getRed(), leg.getGreen(), leg.getBlue(), hsvValues);
        telemetry.addData("Jewel", jewelString);
        telemetry.addData("Colors", "Blue: " + leg.getBlue() + " Red: " + leg.getRed());
        telemetry.addData("HSV", "Hue: " + hsvValues[0] + " Sat: " + hsvValues[1] + " Val: " + hsvValues[2]);
    }
}
