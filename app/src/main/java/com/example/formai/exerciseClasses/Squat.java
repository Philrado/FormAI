//package com.example.formai.exerciseClasses;
//
//import com.example.formai.MLKIT.Angles;
//
//public class Squat {
//
//    public boolean leg_form () {
//
//        // Maybe unnecessary?
//        // Create an array to store all the angles in sequence.
//        //double [] angle_log;
//        Angles angle = new Angles();
//        // First find the closest side to the camera.
//        //double closestSide;
//        double hipAngle = angle.rightHipAngle();
//
//        // Other variables to initialize.
//        boolean depth_check = false;
//        boolean back_to_start = false;
//
//        //if (closestSide == rightHipAngle) {
//        //    hipAngle = rightHipAngle;
//        //}
//        //if (closestSide == leftHipAngle) {
//        //    hipAngle = leftHipAngle;
//        //}
//
//        // If you are leaning backwards your form fails.
//        if (hipAngle > 190) {
//            return false;
//        }
//        // Otherwise if your back is straight and your back is straight or angled forwards but
//        // straight you pass.
//        else {
//            // Check if the user reached 90 or below degrees.
//            if (hipAngle <= 90) {
//                depth_check = true;
//            }
//            if (hipAngle >= 170 && hipAngle < 190 && depth_check == true) {
//                back_to_start = true;
//            }
//            // Maybe unnecessary?
//            // Add to the angle log.
//            // To do.
//        }
//        // If the user has went all the way down below 90 degrees, and then back up to 180
//        // then they have successfully done the main leg portion of a squat. Otherwise they did not.
//        if (!depth_check && !back_to_start) {
//            return false;
//        }
//        return true;
//    }
//
//    // Once the app says go and starts checking form, the functions are called.
//    public void main() {
//        boolean leg_form = leg_form();
//        if (leg_form == false) {
//            // show that they failed.
//            System.out.println("Bad Form");
//        }
//        if (leg_form == true) {
//            // show that their form was good.
//            System.out.println("Good Form");
//        }
//    }
//
//}
