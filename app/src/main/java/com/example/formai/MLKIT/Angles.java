package com.example.formai.MLKIT;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import static java.lang.StrictMath.atan2;

public class Angles {

    // private Pose pose;
    // Returns the angle of three different PoseLandmark positions
    private double getAngle(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
        double result = Math.toDegrees(
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

    // Elbow
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

    // Knee
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

    // Shoulder
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

    // Hip
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

    // Ankle
    public double leftAnkleAngle(Pose pose) {
        PoseLandmark leftFoot = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
        PoseLandmark leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
        PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
        if (leftFoot != null && leftAnkle != null && leftKnee != null) {
            return getAngle(leftFoot, leftAnkle, leftKnee);
        } else {
            return 0;
        }
    }
    public double rightAnkleAngle(Pose pose) {
        PoseLandmark rightFoot = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);
        PoseLandmark rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);
        PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
        if (rightFoot != null && rightAnkle != null && rightKnee != null) {
            return getAngle(rightFoot, rightAnkle, rightKnee);
        } else {
            return 0;
        }
    }
}
