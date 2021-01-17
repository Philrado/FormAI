package com.example.formai;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;

import com.example.formai.MLKIT.Angles;
import com.example.formai.MLKIT.PoseClassification;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    // Define cameraprovider and previewview fields
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    // Get the image capture
    private ImageCapture imageCapture;

    // Get the progress bar
    private ProgressBar progressBar;

    // Get the pose from onCreate()
    private String poseFromIntent;

    // Get floating action button
    private FloatingActionButton cameraButton;

    // Preview view
    private PreviewView previewView;

    // SurfaceView overlay
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private int cameraHeight, cameraWidth, xOffset, yOffset, boxWidth, boxHeight;
    private int x = 0;
    private TextToSpeech tts;
    private boolean fuckyou = false;
    // Method to bind camera to preview view
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider, PreviewView previewView) {
        Size resolution = new Size(1080, 1920);
        // Generate preview with target resolution
        Preview preview = new Preview.Builder()
                .setTargetResolution(resolution)
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        // Generate the image analysis
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(resolution)
                // This sets how it will handle the data pipeline.
                // Pipeline handler to deal with keeping only latest image. Drop other image frames
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        // Get main executor thread to feed into analyzer
        imageAnalysis.setAnalyzer(getMainExecutor(), imageProxy -> {
            // Grab image from phone's camera

            @SuppressLint("UnsafeExperimentalUsageError")
            Image mediaImage = imageProxy.getImage();
            if (mediaImage != null) {

                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                PoseClassification poseClassification = new PoseClassification();
                poseClassification.getPose(image).addOnSuccessListener(pose -> {

                    List<PoseLandmark> allPoseLandmarks = pose.getAllPoseLandmarks();
                    Angles angle = new Angles();
                    // Test pose landmark hitboxes
                    try {
                        int width = 1080;
                        int xOffSet = 0;
                        int xPosition = width + xOffSet + (int) pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW).getPosition().x * -1;
                        int yPosition = (int) pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW).getPosition().y;
                        DrawFocusRect(Color.parseColor("#FF0000"), xPosition, yPosition, true);
                        xPosition = width + xOffSet + (int) pose.getPoseLandmark(PoseLandmark.LEFT_WRIST).getPosition().x * -1;
                        yPosition = (int) pose.getPoseLandmark(PoseLandmark.LEFT_WRIST).getPosition().y;
                        DrawFocusRect(Color.parseColor("#FF0000"), xPosition, yPosition, false);
                    } catch (NullPointerException ignore) {}
                    imageProxy.close();
                }).addOnFailureListener(e -> {
                    System.out.println("FUCK" + e.getMessage() + e.getCause());
                });
            }

        });


        // Set up image capture methods
        this.imageCapture =
                new ImageCapture.Builder()
                        .build();

        // Create the lifecycle, bind camera preview to previewView
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture, imageAnalysis, preview);
        preview.setSurfaceProvider(previewView.createSurfaceProvider());

    }

    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the initial prompt
        new MaterialAlertDialogBuilder(this)
                .setTitle("Start posing!")
                .setMessage("Position your camera so it can show your full body. When you've held that position correctly for a few seconds, you'll receive a checkmark for that pose!")
                .setPositiveButton("Accept", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
        // Create surface view overlay
        surfaceView = findViewById(R.id.overlay);
        surfaceView.setZOrderOnTop(true);
        surfaceView.setWillNotDraw(false);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(this);

        // Create virtual assistant
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.CHINESE);
                }
            }
        });

        // Define the processCameraProvider
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        // When the camera provider resolves, it will bind the hardware camera to the preview
        cameraProviderFuture.addListener(() -> {
            try {
                // If the listener resolves, define the cameraProvider and bind the cameraProvider to the previewView
                previewView = (PreviewView) findViewById(R.id.previewView);
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, previewView);

            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }
    private Canvas canvas;
    private Paint paint;
    private void DrawFocusRect(int color, int x, int y, boolean clear) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height;
        int width;
        height = 1080;
        width = 1920;

        //cameraHeight = height;
        //cameraWidth = width;

        canvas = surfaceHolder.lockCanvas();
        if (!clear) {
            canvas.drawColor(0, PorterDuff.Mode.DARKEN);
        } else {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        //border's properties
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(20);

        int xOffSet = 0;
        int yOffSet = 0;

        int diameterOfRectangle = 40;
        //Changing the value of x in diameter/x will change the size of the box ; inversely proportionate to x
        canvas.drawRect(xOffSet + x, yOffSet + y, xOffSet + x + diameterOfRectangle, yOffSet + y + diameterOfRectangle, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
    private void refreshCanvas() {
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

//        DrawFocusRect(Color.parseColor("#b3dabb"), x);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}