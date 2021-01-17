package com.example.formai.MLKIT;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

public class PoseClassification {
    private PoseDetector poseDetector;

    // constructor for pose detection
    public PoseClassification() {
        // Accurate pose detector on static images, when depending on the pose-detection-accurate sdk
        AccuratePoseDetectorOptions options =
                new AccuratePoseDetectorOptions.Builder()
                        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                        .build();
        poseDetector = PoseDetection.getClient(options);
//        PoseDetectorOptions options =
//                new PoseDetectorOptions.Builder()
//                        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
//                        .build();
    }

    // Return the task of getting the pose from the posedetector
    public Task<Pose> getPose(InputImage inputImage) {
        return poseDetector.process(inputImage);
    }
}
