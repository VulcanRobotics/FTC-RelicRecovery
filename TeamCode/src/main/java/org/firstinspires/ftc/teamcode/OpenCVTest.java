package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import static com.sun.tools.javac.util.Assert.error;

/**
 * Created by Ron on 11/1/2017.
 */
@TeleOp(name="OpenCVTest")

public class OpenCVTest extends OpMode implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static boolean openCVInitialized = false;
    public static JavaCameraView openCVCamera;
    public static final int initialMaxSize = 1200;
    private static boolean initialized = false;
    public int width, height;
    private int frameCount;

    @Override
    public void init() {
        frameCount = 0;
        //Initialize camera view
        BaseLoaderCallback openCVLoaderCallback = null;
        try {
            openCVLoaderCallback = new BaseLoaderCallback(hardwareMap.appContext) {
                @Override
                public void onManagerConnected(int status) {
                    switch (status) {
                        case LoaderCallbackInterface.SUCCESS: {
                            //Woohoo!
                            Log.d("OpenCV", "OpenCV Manager connected!");
                            telemetry.addData("OpenCV", "OpenCV Manager connected!");
                            openCVInitialized = true;
                        }
                        break;
                        default: {
                            super.onManagerConnected(status);
                        }
                        break;
                    }
                }
            };
        } catch (NullPointerException e) {
            error("Could not find OpenCV Manager!\r\n" +
                    "Please install the app from the Google Play Store.");
        }

        final Activity activity = (Activity) hardwareMap.appContext;
        final OpenCVTest t = this;

        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            boolean success = OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, hardwareMap.appContext, openCVLoaderCallback);
            if (!success) {
                Log.e("OpenCV", "Asynchronous initialization failed!");
                error("Could not initialize OpenCV!\r\n" +
                        "Did you install the OpenCV Manager from the Play Store?");
            } else {
                Log.d("OpenCV", "Asynchronous initialization succeeded!");
            }
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            if (openCVLoaderCallback != null)
                openCVLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            else {
                Log.e("OpenCV", "Failed to load OpenCV from package!");
                return;
            }
        }

        while (!openCVInitialized) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);

                layout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                openCVCamera = new JavaCameraView(hardwareMap.appContext, 0);

                layout.addView(openCVCamera);
                layout.setVisibility(View.VISIBLE);

                openCVCamera.setCvCameraViewListener(t);
                openCVCamera.setMinimumHeight(initialMaxSize);
                openCVCamera.setMinimumWidth(initialMaxSize);
                /*
                if (openCVCamera != null)
                    openCVCamera.disableView();
                */

                openCVCamera.enableView();

                /*
                if (!openCVCamera.connectCamera(initialMaxSize, initialMaxSize))
                    error("Could not initialize camera!\r\n" +
                            "This may occur because the OpenCV Manager is not installed,\r\n" +
                            "CAMERA permission is not allowed in AndroidManifest.xml,\r\n" +
                            "or because another app is currently locking it.");
                */
                //Done!
                width = openCVCamera.getWidth();
                height = openCVCamera.getHeight();
                initialized = true;
            }
        });

        while (!initialized) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        telemetry.addData("Status", "OpenCV initialized, height:" + height + " width: " + width);
    }

    @Override
    public void loop() {

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        telemetry.addData("CV", "onCameraViewStarted");
    }

    @Override
    public void onCameraViewStopped() {
        telemetry.addData("CV", "onCameraViewStopped");
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        telemetry.addData("CV", "frameCountL " + (++frameCount));
        return inputFrame.rgba();
    }
}
