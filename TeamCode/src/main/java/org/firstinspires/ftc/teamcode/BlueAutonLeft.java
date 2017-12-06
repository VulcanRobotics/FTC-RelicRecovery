package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.sch.ftc4914.ColorSensorLeg;
import org.sch.ftc4914.FourBarArm;
import org.sch.ftc4914.VladimirEye;
import org.sch.ftc4914.VladimirOmni;

/**
 * Created by hlexer on 10/27/17.
 */

/*
BLUE LEFT
This auton mode is for when we're on the BLUE alliance, starting from the balancing stone
on the LEFT side of the alliance station (as seen by the drivers facing the field from the alliance
station)

The color sensor extends from the right side of the robot, which means that when starting on this stone
the rear of the robot will face towards the safe zone / cryptobox.  That means we must drive reverse
to get to the safe zone; from this stone we must move right to align with the cryptobox so will drive
 an 'S' pattern after knocking the jewel away.

COLOR SENSOR SENSES towards rear of robot, so:
- since we're blue, if we sense red, we want to drive reverse to knock red away
- since we're blue, if we sense blue, we want to drive forward to knock red away

After knocking jewel out of the way, drive the required 'S' pattern to park in the safe zone, facing
the cryptobox:
    reverse 24in, turn right 90deg, reverse 12in, turn left 90deg, reverse 12in

Glyph will be placed in rear of robot at start
*/

@Autonomous(name = "Blue Left")

public class BlueAutonLeft extends OpMode {
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
    private double pictographNumber = 0; //-7.5 = left (to person), 0 == middle, 7.5 = right (to person)
    private RelicRecoveryVuMark pictographType;
    private int turningConstant = 0; //2 = red, 0 = nothing, -2 = blue
    String jewelString;
    //private double turn90 = 1.5

    @Override
    public void init() {
        stepNumber = 0;
        robotDrive = new VladimirOmni(hardwareMap);
        robotEye = new VladimirEye(hardwareMap, VuforiaLocalizer.CameraDirection.BACK);
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

                waitTimer(10);
                stepNumber += 1;

                break;
            case 1: //checks the picture to see if left, center or right
                pictographType = robotEye.getPictograph();
                stepNumber += 1;
                break;
            case 2: //analyzes data and finishes looking
                pictographType = robotEye.getPictograph();
                if (pictographType == RelicRecoveryVuMark.LEFT){
                    pictographNumber = -7.5;
                    loopCounter = 0;
                    stepNumber += 1;
                }else if (pictographType == RelicRecoveryVuMark.RIGHT){
                    pictographNumber = 7.5;
                    loopCounter = 0;
                    stepNumber += 1;
                }else if (pictographType == RelicRecoveryVuMark.CENTER){
                    pictographNumber = 0;
                    loopCounter = 0;
                    stepNumber += 1;
                }

                waitTimer(10);
                stepNumber += 1;

                break;
            case 3: //Closes the arm so that it holds onto a glyph
                arm.closeGripper(); //This holds on to the glyph so that it is possible to put it into the cryptobox later on in auton
                robotEye.stopLooking();

                waitTimer(10);
                stepNumber += 1;

                break;
            case 4:
                arm.moveArm(-0.2); //This lifts the arm so that it doesn't drag on the ground

                waitTimer(10);
                stepNumber += 1;

                break;
            case 5: //Senses the color of the jewel on the right side
/*
                COLOR SENSOR SENSES towards rear of robot, so:
                - since we're blue, if we sense red, we want to drive reverse to knock red away
                - since we're blue, if we sense blue, we want to drive forward to knock red away
*/
                if (leg.blueJewelDetected()) {
                    jewelString = "BLUE JEWEL!";
                    turningConstant = -2;
                }
                if (leg.redJewelDetected()) {
                    jewelString = "RED JEWEL!";
                    turningConstant = 2;
                }
                if (++loopCounter >= 30){
                    jewelString = "NO STRING";
                    loopCounter = 0;
                    turningConstant = 0;
                }

                stepNumber = 10;
                break;
            case 10: // turn to knock off jewel
                robotDrive.distanceDrive(0.30,turningConstant, -turningConstant);
                //robotDrive.omniDrive(0.35, 0.0);

                stepNumber += 1;  // pause and then stop robot
                break;
            case 11:            // pause

                waitTimer(15);
                stepNumber = 20;

                break;
            case 20: // turn back to regular position
                robotDrive.distanceDrive(0.30,-turningConstant, turningConstant);
                //robotDrive.omniDrive(0.35, 0.0);

                stepNumber += 1;  // pause and then stop robot
                break;
            case 21:            // pause

                waitTimer(15);
                stepNumber = 50;

                break;
            case 50:
                robotDrive.omniDrive(0,0);
                leg.home(); //folds leg back

                stepNumber += 1;
                break;
            case 51:

                waitTimer(20);
                stepNumber = 55;

                break;
            case 55:
                robotDrive.distanceDrive(0.5, -24, -24);
                stepNumber = 60;

                break;
            case 60:

                waitTimer(40);
                stepNumber = 70;

                break;
            case 70: //turn to the common area
                robotDrive.rightAngleTurn(-1); //4.0, -4.0
                // This turns robot so the ARM is facing the cryptobox
                // As of now, the robot is facing the cryptocolumn on its middle.
                stepNumber += 1;
                break;
            case 71:

                waitTimer(20);
                stepNumber += 1;

                break;
            case 72:
                robotDrive.distanceDrive(0.5, 12 +pictographNumber, 12 +pictographNumber);
                stepNumber += 1;

                break;
            case 73:

                waitTimer(20);
                stepNumber = 75;

                break;
            case 75: //put glyph into cryptobox
                //ready to drive into c.box
                robotDrive.rightAngleTurn(-1); //4.0, -4.0
                // This turns robot so the ARM is facing the cryptobox
                // As of now, the robot is facing the cryptocolumn on its middle.
                stepNumber += 1;
                break;
            case 76:

                waitTimer(20);
                stepNumber = 82;

                break;
            case 82: //drives into the cryptobox
                robotDrive.distanceDrive(0.5, 10, 10);
                stepNumber = 83;
                break;
            case 83:

                waitTimer(20);
                stepNumber += 1;

                break;
            case 84: //Lets go of Glyph
                arm.openGripper();
                if (++loopCounter >= 30){
                    loopCounter = 0;
                    stepNumber = 85;
                }
                break;
            case 85:
                robotDrive.distanceDrive(0.5, -10, -10);
                stepNumber = 86;
                break;
            case 86:

                waitTimer(10);
                stepNumber += 1;

                break;
            case 87:
                arm.closeGripper();
                stepNumber += 1;
                break;
            case 88:

                waitTimer(10);
                stepNumber += 1;

                break;
            case 89:
                robotDrive.rightAngleTurn(1);
                stepNumber += 1;
                break;
            case 90:

                waitTimer(20);
                stepNumber += 1;

                break;
            case 91:
                robotDrive.rightAngleTurn(1);
                stepNumber += 1;
            case 92:

                waitTimer(20);
                stepNumber += 1;

                break;
            case 93:
                robotDrive.distanceDrive(0.5, -10, -10);
                stepNumber += 1;
                break;
            case 94:

                waitTimer(10);
                stepNumber += 1;

                break;
            case 95:
                robotDrive.distanceDrive(0.5, -2, 0); //jostle right

                waitTimer(5);
                stepNumber += 1;

                break;
            case 96:
                robotDrive.distanceDrive(0.5, 0, -2); //jostle left

                waitTimer(5);
                stepNumber += 1;

                break;
            case 97: //Lets go of Glyph
                arm.openGripper();

                waitTimer(10);
                stepNumber = 100;

                break;
            case 100: //Back up into safe zone
                robotDrive.distanceDrive(0.5, 3, 3); // drives back to safe zone

                waitTimer(5);
                stepNumber += 1;

                break;
            case 101:

                waitTimer(10);
                stepNumber = 110;

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
        telemetry.addData("VUmark: ",  pictographNumber);
    }

    public void waitTimer(int time){
        if (++ loopCounter >= time) {
            loopCounter = 0;
        }
    }
}
