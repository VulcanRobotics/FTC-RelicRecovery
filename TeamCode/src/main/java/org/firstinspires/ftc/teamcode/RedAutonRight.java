package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.sch.ftc4914.ColorSensorLeg;
import org.sch.ftc4914.FourBarArm;
import org.sch.ftc4914.VladimirEye;
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by hlexer on 10/27/17.
 */

/*
RED RIGHT
This auton mode is for when we're on the RED alliance, starting from the balancing stone
on the RIGHT side of the alliance station (as seen by the drivers facing the field from the alliance
station)

The color sensor extends from the right side of the robot, which means that when starting on this stone
the front of the robot will face towards the safe zone / cryptobox.  That means we must drive forwards
to get to the safe zone; from this stone we must move left to align with the cryptobox so will drive
 an 'S' pattern after knocking the jewel away.

COLOR SENSOR SENSES towards rear of robot, so:
- since we're red, if we sense red, we want to drive forward to knock blue away
- since we're red, if we sense blue, we want to drive reverse to knock blue away

After knocking jewel out of the way, drive the required 'S' pattern to park in the safe zone, facing
the cryptobox:
    forward 12in, turn left 90deg, forward 12in, turn right 90deg, forward 12in

Glyph will be placed in rear of robot at start
*/

@Autonomous(name = "Red Right")

public class RedAutonRight extends OpMode {
    private int stepNumber = 0;
    private int loopCounter = 0;
    private VladimirOmni robotDrive;
    private VladimirEye robotEye;
    private ColorSensorLeg leg;
    private ElapsedTime runtime = new ElapsedTime();
    private float[] hsvValues = {0F, 0F, 0F};
    private FourBarArm arm; //NEEDS TO BE LOOKED AT
    private String direction = "";
    private String armType = "classic"; //classic, 4-bar, or elevator
    private double pictographNumber = -7.5; //7.5 = right (to person), 0 == middle, -7.5 = left (to person)
    String jewelString;
    //private double turn90 = 1.5

    @Override
    public void init() {
        stepNumber = 0;
        robotDrive = new VladimirOmni(hardwareMap);
        robotEye = new VladimirEye(hardwareMap, VuforiaLocalizer.CameraDirection.FRONT);
        leg = new ColorSensorLeg(hardwareMap);
        jewelString = "NOTHING!";
        arm = new FourBarArm(hardwareMap); //NEEDS TO BE LOOKED AT
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
            case 1: //checks the picture to see if left, center or right
                robotEye.startLooking();
                stepNumber += 1;
                break;
            case 2: //analyzes data and finishes looking
                if (++loopCounter >= 20) {
                    //pictographType = robotEye.getPictograph();
                    loopCounter = 0;
                    robotEye.stopLooking();
                    stepNumber += 1;
                }
                break;
            case 3: //Closes the arm so that it holds onto a glyph
                arm.closeGripper(); //This holds on to the glyph so that it is possible to put it into the cryptobox later on in auton

                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 4:
                arm.moveArm(-0.2);
                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 5: //Senses the color of the jewel on the right side
/*
                COLOR SENSOR SENSES towards rear of robot, so:
                - since we're red, if we sense red, we want to drive forward to knock blue away
                - since we're red, if we sense blue, we want to drive reverse to knock blue away
*/
                if (leg.blueJewelDetected()) {
                    jewelString = "BLUE JEWEL!";
                    stepNumber = 20; // drive reverse
                }
                if (leg.redJewelDetected()) {
                    jewelString = "RED JEWEL!";
                    stepNumber = 10; // drive forward
                }
                if (++loopCounter >= 100){
                    jewelString = "NO JEWEL";
                    loopCounter = 1;
                    direction = "none";
                    stepNumber = 51;
                }
                break;
            case 10: // drive forward
                robotDrive.distanceDrive(0.30,5,5);
                //robotDrive.omniDrive(0.35, 0.0);
                direction = "forward";

                stepNumber = 50;  // pause and then stop robot
                break;
            case 20: // drive reverse
                //leg.extendLeg();
                robotDrive.distanceDrive(0.30,-5,-5);
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

            case 51:
                //arm.moveArm(-0.5); //moves the arm to allow the leg to get folded back in
                robotDrive.omniDrive(0,0);
                leg.home(); //folds leg back to top
                stepNumber += 1;
                break;
            case 52:
                if (++loopCounter >= 20) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 53:
                //arm.moveArm(0.5); //moves arm back to regular position
                if(direction == "forward"){
                    stepNumber = 70;
                }else if(direction == "backward") {
                    stepNumber = 60;
                }
                else {
                    stepNumber = 55;
                }
                break;
            /*
            After knocking jewel out of the way, drive the required 'S' pattern to park in the safe zone, facing
            the cryptobox:
            forward 24in, turn left 90deg, forward 12in, turn right 90deg, forward ~12in
            */
            case 55:
                robotDrive.distanceDrive(0.5, 24,  24);
                stepNumber = 71;
                break;
            case 60: //robot drove 5in reverse to knock jewel away, so drive 29in forwards
                robotDrive.distanceDrive(0.5, 29, 29);
                stepNumber+=1;
                break;
            case 61: // wait for drive to complete
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    //stepNumber = 75;
                    stepNumber = 75;   // don't S turn or jostle, just stop
                }
                break;
            case 70: //robot drove 5in forwards to knock jewel away, so drive 19in forwards
                robotDrive.distanceDrive(0.5, 19, 19);
                stepNumber+=1;
                break;
            case 71: // wait for drive to complete
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    // stepNumber = 75;
                    stepNumber = 75;   // don't S turn or jostle, just stop
                }
                break;

            case 75: // turn 90deg left
                robotDrive.distanceDrive(0.5, -4.0 * Math.PI, 4.0 * Math.PI);
                stepNumber += 1;
                break;
            case 76: // wait for turn to complete
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 77: // drive 12in forwards
                robotDrive.distanceDrive(0.5, 12 + (pictographNumber / 1.2), 12 + (pictographNumber / 1.2));
                stepNumber += 1;
                break;
            case 78: // wait for drive to complete
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 79: // turn 90deg right
                robotDrive.distanceDrive(0.5,(int) (4
                        *Math.PI - (pictographNumber * 0 / 3)),(int)(-4*Math.PI + (pictographNumber * 0 / 3)));
                stepNumber += 1;
                break;
            case 80: // wait for turn to complete
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 81: // drives into the cryptobox
                robotDrive.distanceDrive(0.5, 12, 12);
                stepNumber = 91;
                break;
            case 91:
                if (!robotDrive.isBusy() || ++ loopCounter>= 30) {
                    loopCounter = 0;
                    stepNumber=96;

                }
                break;
            case 96:
                robotDrive.distanceDrive(1, 2, 0); //jostle right
                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber += 1;
                }
                break;
            case 97:
                robotDrive.distanceDrive(1, 0, 2); //jostle left
                if (++loopCounter >= 30) {
                    loopCounter = 0;
                    stepNumber = 100;
                }
                break;

            case 100: //Lets go of Glyph
                arm.openGripper();
                if (++loopCounter >= 30){
                    loopCounter = 0;
                    stepNumber = 101;
                }
                break;
            case 101: //Back up into safe zone
                robotDrive.distanceDrive(0.5, -3, -3); // drives back to safe zone
                if (++loopCounter >= 2){
                    loopCounter = 0;
                    stepNumber = 105;
                }
                break;
            case 105:
                if (++loopCounter >= 30){
                    loopCounter = 0;
                    stepNumber = 110;
                }
                break;
            case 110:  // stop robot
                arm.moveArm(0);
                robotEye.stopLooking();
                robotDrive.omniDrive(0,0);
            default:
                break;
        }
        Color.RGBToHSV(leg.getRed(), leg.getGreen(), leg.getBlue(), hsvValues);
        telemetry.addData("Left","Target: " + robotDrive.getLeftTarget() + " Actual: " + robotDrive.getLeftPos());
        telemetry.addData("Right","Target: " + robotDrive.getRightTarget() + " Actual: " + robotDrive.getRightPos());
        telemetry.addData("Jewel", jewelString);
        telemetry.addData("Colors", "Blue: " + leg.getBlue() + " Red: " + leg.getRed());
        telemetry.addData("HSV", "Hue: " + hsvValues[0] + " Sat: " + hsvValues[1] + " Val: " + hsvValues[2]);
        telemetry.addData("Step Number: ", stepNumber);
        telemetry.addData("VUmark: ", robotEye.getPictograph());
    }
}
