package com.example.formai.MLKIT;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import static java.lang.StrictMath.atan2;

public class Angles {
//    private Pose pose;
    // Returns the angle of three different PoseLandmark positions
    private double getAngle(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
        double result =
                Math.toDegrees(
                        atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
                                lastPoint.getPosition().x - midPoint.getPosition().x)
                                - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
                                firstPoint.getPosition().x - midPoint.getPosition().x));
        result = Math.abs(result); // Angle should never be negative
        if (result > 180) {
            result = (360.0 - result); // Always get the acute representation of the angle
        }
        return result;
    }

//    Angles(Pose pose) {
//        this.pose = pose;
//    }
//
//    // Define different landmarks


//    PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
//
//    PoseLandmark rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
//
//    PoseLandmark rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
//    PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
//    PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
//    PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
//    PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
//    PoseLandmark leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
//    PoseLandmark rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);
//    PoseLandmark leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY);
//    PoseLandmark rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY);
//    PoseLandmark leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
//    PoseLandmark rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
//    PoseLandmark leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
//    PoseLandmark rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
//    PoseLandmark leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL);
//    PoseLandmark rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL);
//    PoseLandmark leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
//    PoseLandmark rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);
//    PoseLandmark nose = pose.getPoseLandmark(PoseLandmark.NOSE);
//    PoseLandmark leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER);
//    PoseLandmark leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE);
//    PoseLandmark leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER);
//    PoseLandmark rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER);
//    PoseLandmark rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE);
//    PoseLandmark rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER);
//    PoseLandmark leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR);
//    PoseLandmark rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR);
//    PoseLandmark leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH);
//    PoseLandmark rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH);

    // Define angles
    public double leftElbowAngle(Pose pose) {
        PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
        PoseLandmark leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
        PoseLandmark leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
        if (leftShoulder != null && leftElbow != null && leftWrist != null) {
            return getAngle(leftShoulder, leftElbow, leftWrist);
        } else {
            return 0;
        }
    }

    public double rightElbowAngle(Pose pose) {
        //get three landmarks and then compute angle
        PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
        PoseLandmark rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
        PoseLandmark rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);

        if(rightElbow != null && rightShoulder !=null && rightWrist != null)
            return getAngle(rightShoulder,rightElbow,rightWrist);
        else
            return 0;
    }

    //Ankle, hip, knee for knee angle
    public double rightKneeAngle(Pose pose) {
        PoseLandmark rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);
        PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
        PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);

        if(rightAnkle != null && rightHip !=null && rightKnee!= null)
            return getAngle(rightAnkle, rightKnee, rightHip);
        else
            return 0;
    }

    public double leftKneeAngle(Pose pose) {
        PoseLandmark leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
        PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
        PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);

        if(leftAnkle != null && leftHip !=null && leftKnee!= null)
            return getAngle(leftAnkle, leftKnee, leftHip);
        else
            return 0;
    }

    public double leftShoulderAngle(Pose pose) {
        PoseLandmark leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
        PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
        PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
        if (leftElbow != null && leftShoulder != null && leftHip != null) {
            return getAngle(leftElbow, leftShoulder, leftHip);
        } else {
            return 0;
        }
    }
    public double rightShoulderAngle(Pose pose) {
        PoseLandmark start = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
        PoseLandmark mid = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
        PoseLandmark end = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
        if (start != null && mid != null && end != null) {
            return getAngle(start, mid, end);
        } else {
            return 0;
        }
    }
    public double rightHipAngle(Pose pose) {
        PoseLandmark start = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
        PoseLandmark mid = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
        PoseLandmark end = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);
        if (start != null && mid != null && end != null) {
            return getAngle(start, mid, end);
        } else {
            return 0;
        }
    }
    public double leftHipAngle(Pose pose) {
        PoseLandmark start = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
        PoseLandmark mid = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
        PoseLandmark end = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
        if (start != null && mid != null && end != null) {
            return getAngle(start, mid, end);
        } else {
            return 0;
        }
    }


}
