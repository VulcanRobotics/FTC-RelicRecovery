package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Ron on 11/11/2017.
 */

/*
TODO:  this works, but the camera is rotated 90deg so need to fix that
    see: https://github.com/opencv/opencv/issues/4704
    Also, cannot restart the OpMode without first exiting and restarting the RC....when doing that
    the camera doesn't restart and preview just displays a static frame....potential OCV issue?
*/

@TeleOp(name = "OCVCameraPreview")

public class OCVCameraPreview extends OpMode implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;

    private BaseLoaderCallback mLoaderCallback;
    private LinearLayout rcCameraMonitor;
    private OCVCameraPreview t = this;
    private Mat mRGBa, mRGBt, mRGBf;

    @Override
    public void init() {
        telemetry.addData("Status: ", "init()....setting up view");
        Activity RC = (Activity) hardwareMap.appContext;
        rcCameraMonitor = (LinearLayout) RC.findViewById(R.id.cameraMonitorViewId);

        RC.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOpenCvCameraView = new JavaCameraView(hardwareMap.appContext, JavaCameraView.CAMERA_ID_BACK);
                mOpenCvCameraView.enableFpsMeter();
                rcCameraMonitor.addView(mOpenCvCameraView);
                rcCameraMonitor.setVisibility(View.VISIBLE);
                mOpenCvCameraView.setCvCameraViewListener(t);
            }
        });

        telemetry.addData("Status: ", "init()....loading OpenCV");
        mLoaderCallback = new BaseLoaderCallback(hardwareMap.appContext) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                    {
                        Log.i(TAG, "OpenCV loaded successfully");
                        mOpenCvCameraView.enableView();
                    } break;
                    default:
                    {
                        super.onManagerConnected(status);
                    } break;
                }
            }
        };

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, hardwareMap.appContext, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }


    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        telemetry.addData("Status:", "stop()");
        mOpenCvCameraView.disableView();
        mOpenCvCameraView = null;
        Activity RC = (Activity) hardwareMap.appContext;
        RC.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rcCameraMonitor.setVisibility(SurfaceView.INVISIBLE);
            }
        });
        super.stop();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        telemetry.addData("Status: ", "onComeraViewStarted");
    }

    @Override
    public void onCameraViewStopped() {
        telemetry.addData("Status: ", "onCameraViewStopped");
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }
}
