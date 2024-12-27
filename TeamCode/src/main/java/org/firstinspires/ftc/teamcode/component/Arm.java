package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;

public class Arm implements Component {

        public final Servo hand;

        public final Servo ArmLeft;
        public final Servo ArmRight;

        public final DcMotor SLideLeft;
        public final DcMotor SLideRight;

        public final Servo handRotation;

        public Arm(HardwareMap hardwareMap) {
                hand = hardwareMap.get(Servo.class,Const.upSlider.Name.hand);
                hand.setPosition(0);
                hand.setDirection(Servo.Direction.REVERSE);

                ArmLeft = hardwareMap.get(Servo.class,Const.upSlider.Name.ArmLeft);
                ArmLeft.setDirection(Servo.Direction.REVERSE);
                ArmLeft.setPosition(0);

                ArmRight = hardwareMap.get(Servo.class,Const.upSlider.Name.ArmRight);
                ArmRight.setDirection(Servo.Direction.REVERSE);
                ArmRight.setPosition(0);

                handRotation = hardwareMap.get(Servo.class,Const.upSlider.Name.handRotation);
                handRotation.setDirection(Servo.Direction.FORWARD);
                handRotation.setPosition(0);

                SLideLeft = hardwareMap.get(DcMotor.class,Const.upSlider.Name.SLideLeft);
                SLideRight = hardwareMap.get(DcMotor.class,Const.upSlider.Name.SlideRight);
                SLideLeft.setDirection(Const.upSlider.Direction.slideLeft);
                SLideRight.setDirection(Const.upSlider.Direction.slideRight);
                SLideLeft.setMode(Const.upSlider.Mode.slideInit);
                SLideRight.setMode(Const.upSlider.Mode.slideInit);
                SLideLeft.setTargetPosition(0);
                SLideRight.setTargetPosition(0);
                SLideLeft.setPower(0);
                SLideRight.setPower(0);
                SLideLeft.setMode(Const.upSlider.Mode.slideMoving);
                SLideRight.setMode(Const.upSlider.Mode.slideMoving);

        }

        @Override
        public void autonomousInit() {

        }

        @Override
        public void teleopInit() {

        }

        @Override
        public void disabledInit() {

        }

        @Override
        public void testInit() {

        }

        @Override
        public void readSensors(State state) {

        }

        @Override
        public void applyState(State state) {
//         if (state.driveState.sliderIsUp){
//                 SLideLeft.setTargetPosition(1000);
//                 SLideRight.setTargetPosition(1000);
//                 SLideLeft.setPower(0.5);
//                 SLideRight.setPower(0.5);
//         }else{
//                 SLideLeft.setTargetPosition(0);
//                 SLideRight.setTargetPosition(0);
//                 SLideLeft.setPower(0.5);
//                 SLideRight.setPower(0.5);
//         }

                //        if (state.driveState.climb){
//            climbLeft.setTargetPosition(30);
//            climbRight.setTargetPosition(30);
//            climbLeft.setPower(0.5);
//            climbRight.setPower(0.5);
//        }else{
//            climbLeft.setTargetPosition(0);
//            climbRight.setTargetPosition(0);
//            climbLeft.setPower(0.5);
//            climbRight.setPower(0.5);
//        }

           if (state.driveState.handIsUP){
                   ArmLeft.setPosition(1);
                   ArmRight.setPosition(1);
           }else{
                   ArmLeft.setPosition(0);
                   ArmRight.setPosition(0);
           }

//           if (state.driveState.handIsOpen){
//                   hand.setPosition(1);
//           }else{
//                   hand.setPosition(0);
//           }

           if (state.driveState.handIsRolling){
                   handRotation.setPosition(1);
           }else{
                   handRotation.setPosition(0);
           }
        }
    }
    