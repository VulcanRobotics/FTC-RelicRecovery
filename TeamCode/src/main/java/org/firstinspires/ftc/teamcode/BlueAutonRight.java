package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.sch.ftc4914.ColorSensorLeg;
import org.sch.ftc4914.GlyphArm; //NEEDS TO BE LOOKED AT
import org.sch.ftc4914.VladimirEye;
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by hlexer on 10/27/17.
 */

/*
BLUE RIGHT
This auton mode is for when we're on the BLUE alliance, starting from the balancing stone
on the RIGHT side of the alliance station (as seen by the drivers facing the field from the alliance
station)

The color sensor extends from the right side of the robot, which means that when starting on this stone
the rear of the robot will face towards the safe zone / cryptobox.  That means we must drive reverse
to get to the safe zone, and turn left to face the cryptobox

COLOR SENSOR SENSES towards rear of robot, so:
- since we're blue, if we sense red, we want to drive reverse to knock red away
- since we're blue, if we sense blue, we want to drive forward to knock red away

After knocking jewel out of the way, drive reverse the required distance to reach the safe zone,
and turn left 90deg to face cryptobox

Glyph will be placed in rear of robot at start
*/

@Autonomous(name = "Blue Right")

public class BlueAutonRight extends OpMode {
    private int stepNumber = 0;
    private int loopCounter = 0;
    private VladimirOmni robotDrive;
    private VladimirEye robotEye;
    private ColorSensorLeg leg;
    private ElapsedTime runtime = new ElapsedTime();
    private float[] hsvValues = {0F, 0F, 0F};
    private GlyphArm arm; //NEEDS TO BE LOOKED AT
    private String direction = "";
    private String armType = "classic"; //classic, 4-bar, or elevator
    String jewelString;
    //private double turn90 = 1.5

    @Override
    public void init() {
        stepNumber = 0;
        robotDrive = new VladimirOmni(hardwareMap);
        robotEye = new VladimirEye(hardwareMap, VuforiaLocalizer.CameraDirection.FRONT);
        leg = new ColorSensorLeg(hardwareMap);
        jewelString = "NOTHING!";
        arm = new GlyphArm(hardwareMap); //NEEDS TO BE LOOKED AT
    }

    @Override
    public void init_loop() {
        telemetry.addData("Wheels", "LtPos: " + robotDrive.getLeftPos() + " RtPos: " + robotDrive.getRightPos());
    }

    @Override
    public void start() {
        runtime.reset();
        robotEye.startLooking();
    }

    @Override
    public void loop() {
        switch (stepNumber) {
            case 0: //Extends the leg to prepare to look at the jewel color
                if (loopCounter == 0){
                    leg.extend();
                }
                if (++loopCounter >= 20) {
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
                robotDrive.distanceDrive(0.35,5,5);
                //robotDrive.omniDrive(0.35, 0.0);
                direction = "forward";

                stepNumber = 50;  // pause and then stop robot
                break;
            case 20: // drive reverse
                //leg.extendLeg();
                robotDrive.distanceDrive(0.35,-5,-5);
                //robotDrive.omniDrive(-0.35,0);
                direction = "backward";

                stepNumber = 50; // pause and then stop robot
                break;
            case 50:            // pause
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber += 1;

                }
                break;
            //NEEDS TO BE LOOKED AT
            case 51:
                //arm.moveArm(-0.5); //moves the arm to allow the leg to get folded back in
                robotDrive.omniDrive(0,0);
                leg.home(); //folds leg back to top
                //arm.moveArm(0.5); //moves arm back to regular position
                if(direction == "forward"){
                    stepNumber += 9;
                }else {
                    stepNumber += 19;
                }
                break;
            case 60: //robot drives to safe zone (went forwards)
                robotDrive.distanceDrive(0.5, -41,-41);
               stepNumber+=1;
                break;

            case 61:
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber =80;

                }
                break;

            case 70: //robot drives to safe zone (went backwards)
                robotDrive.distanceDrive(0.5, -31,-31);
                stepNumber+=1;
                break;

            case 71:
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber =80;

                }
                break;

            case 80: //put glyph into cryptobox
                 //ready to drive into c.box
               robotDrive.distanceDrive(0.5,(int) (-4.0*Math.PI),(int)(4.0*Math.PI));
                //robotDrive.omniDrive(0, 1); //turns robot
                    stepNumber += 1;


                break;
            case 81:
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber=85;

                }
                break;
            case 85:
                robotDrive.distanceDrive(0.5, -20, -20);
                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 86:
                robotDrive.distanceDrive(1, 2, 0); //jostle right
                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 87:
                robotDrive.distanceDrive(1, 2, 5); //jostle left
                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber = 90;
                }
                break;
            case 90: // stop robot
                arm.moveArm(0);
                robotEye.stopLooking();
                robotDrive.omniDrive(0,0);
                break;


            default:
                break;
        }
        Color.RGBToHSV(leg.getRed(), leg.getGreen(), leg.getBlue(), hsvValues);
        telemetry.addData("Jewel", jewelString);
        telemetry.addData("Colors", "Blue: " + leg.getBlue() + " Red: " + leg.getRed());
        telemetry.addData("HSV", "Hue: " + hsvValues[0] + " Sat: " + hsvValues[1] + " Val: " + hsvValues[2]);
        telemetry.addData("Step Number: ", stepNumber);
        telemetry.addData("VUmark: ", robotEye.getPictograph());
    }
}
