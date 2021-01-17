package com.example.formai.exerciseClasses;

import android.util.Log;
import android.util.Range;

import com.example.formai.MLKIT.Angles;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private static boolean activation;
    private double leftHipAngle;
    private double rightHipAngle;
    private static boolean firstActivation = false;
    private static boolean secondActivation = false;
    public void activateSquat(Pose pose) {
        // get current pose landmarks
        try {
            Angles angles = new Angles();
            leftHipAngle = angles.leftHipAngle(pose);
            rightHipAngle = angles.rightHipAngle(pose);

//            Log.d("ANGLES", String.valueOf(leftHipAngle) + " " + String.valueOf(rightHipAngle));
            // Squat threshold activation
            Range<Integer> firstSquatThresholdActivation = Range.create(0, 120);
            Range<Integer> secondSquatThresholdActivation = Range.create(150, 180);
//            Log.d("HIP ANGLES:", leftHipAngle + " " + rightHipAngle);
            if (firstSquatThresholdActivation.contains((int) leftHipAngle) || firstSquatThresholdActivation.contains((int) rightHipAngle)) {
                Log.d("ACTIVATION", "FIRST ACTIVATION");
                firstActivation = true;
            }

            if ((secondSquatThresholdActivation.contains((int) leftHipAngle) || secondSquatThresholdActivation.contains((int) rightHipAngle)) && firstActivation) {
                Log.d("ACTIVATION", "SECOND ACTIVATION");
                secondActivation = true;
            }
        } catch (NullPointerException ignore) {}
        // We don't care about null pointer exceptions

    }
    private static boolean depthReached = false;
    private static boolean baseReached = false;
    private static boolean neutralReached = false;
    private static boolean isProperSquat = false;
    private static boolean badDepth = false;
    private static int squatCount = 0;
    private Angles angles = new Angles();
    public void testSquat(Pose pose) {
        // get current pose landmarks
        try {
            angles = new Angles();
            leftHipAngle = angles.leftHipAngle(pose);
            rightHipAngle = angles.rightHipAngle(pose);

            // Base threshold
            Range<Integer> baseThreshold = Range.create(120, 150);
            // Depth threshold
            Range<Integer> depthThreshold = Range.create(0, 110);
            // Neutral position
            Range<Integer> neutralThreshold = Range.create(170, 180);
//            Log.d("HIP ANGLES:", leftHipAngle + " " + rightHipAngle);

            // Check if base threshold reached
            if (baseThreshold.contains((int) leftHipAngle) || baseThreshold.contains((int) rightHipAngle)) {
                baseReached = true;
                Log.d("BASE: ", "REACHED");
            }

            // Check if depth and neutral are reached only after base is reached
            if (baseReached) {
                if (depthThreshold.contains((int) leftHipAngle) || depthThreshold.contains((int) rightHipAngle)) {
                    Log.d("DEPTH: ", "REACHED");
                    depthReached = true;
                }

                // Check if neutral position reached only after base is reached
                if (neutralThreshold.contains((int) leftHipAngle) || neutralThreshold.contains((int) rightHipAngle)) {
                    neutralReached = true;
                    Log.d("NEUTRAL: ", "REACHED");
                }
            }

            // it's a proper squat if and only if depth and neutral position are both reached
            if (depthReached && neutralReached) {
                isProperSquat = true;
                badDepth = false;
                squatCount++;
            }

            if (!depthReached && neutralReached) {
                badDepth = true;
                isProperSquat = false;
            }


        } catch (NullPointerException ignore) {}
        // We don't care about null pointer exceptions
    }

    public static int getSquatCount() {
        return squatCount;
    }
    public static boolean isBadDepth() {
        return badDepth;
    }
    public static boolean getIsProperSquat() {
        return isProperSquat;
    }
    public void resetFields() {
        depthReached = false;
        baseReached = false;
        neutralReached = false;
        isProperSquat = false;
        badDepth = false;
    }
    public boolean getSecondActivation() {
        return secondActivation;
    }



//     Bicep stuff
    private static boolean bicepBaseReached = false;
    private static boolean bicepPreDepthReached = false;
    private static boolean bicepDepthReached = false;
    private static boolean bicepFlexed = false;
    private static boolean bicepNotFlexedProperly = false;
    private static int bicepCount = 0;
    public void testBicep(Pose pose) {
        double leftElbowAngle = angles.leftElbowAngle(pose);
        double rightElbowAngle = angles.rightElbowAngle(pose);

        // Define base thresholds
        Range<Integer> baseThreshold = Range.create(170, 180);
        Range<Integer> depthThreshold = Range.create(0, 60);
        Range<Integer> preDepthThreshold = Range.create(100, 130);

        // If depth reached then that's good
        if ((baseThreshold.contains((int) leftElbowAngle) || baseThreshold.contains((int) rightElbowAngle)) && bicepDepthReached) {
            bicepFlexed = true; // Bicep has been flexed succesfully
//            Log.d("BICEPTEST", "GOOD FORM");
//            bicepCount++;
        }
        // If predepth reached but not depth threshold then form was bad
        if ((baseThreshold.contains((int) leftElbowAngle) || baseThreshold.contains((int) rightElbowAngle)) && !bicepDepthReached && bicepPreDepthReached) {
            bicepNotFlexedProperly = true;
            Log.d("BICEPTEST", "BAD FORM");
        }

        // If pre depth threshold reached
        if (preDepthThreshold.contains((int) leftElbowAngle) || preDepthThreshold.contains((int) rightElbowAngle)) {
            bicepPreDepthReached = true;
//            Log.d("BICEPTEST", "PRE DEPTH THRESHOLD");
        }

        // If depth threshold reached
        if (depthThreshold.contains((int) leftElbowAngle) || depthThreshold.contains((int) rightElbowAngle)) {
            bicepDepthReached = true;
            Log.d("BICEPTEST", "DEPTH THRESHOLD");
        }
        Log.d("BICEPANGLE:", String.valueOf(rightElbowAngle));

    }

    public void resetBicepFields() {
        bicepBaseReached = false;
        bicepPreDepthReached = false;
        bicepDepthReached = false;
        bicepFlexed = false;
        bicepNotFlexedProperly = false;
    }

    public static boolean isBicepFlexed() {
        return bicepFlexed;
    }

    public static boolean isBicepNotFlexedProperly() {
        return bicepNotFlexedProperly;
    }
    public void addSquat() {
        squatCount++;
    }

    public static int getBicepCount() {
        return bicepCount;
    }
    public void updateBicepCount() {
        bicepCount++;
    }
}
