package org.firstinspires.ftc.teamcode.subClass;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Const {
    public static class Drive {
        public static class Name {
            public static final String leftFront = "leftFront";
            public static final String rightFront = "rightFront";
            public static final String leftRear = "leftRear";
            public static final String rightRear = "rightRear";
            public static final String imu = "imu";
        }
        public static class Power{

        }
        public static class Direction {
            public static final DcMotor.Direction leftFront = DcMotor.Direction.FORWARD;
            public static final DcMotor.Direction rightFront = DcMotor.Direction.REVERSE;
            public static final DcMotor.Direction leftRear = DcMotor.Direction.FORWARD;
            public static final DcMotor.Direction rightRear = DcMotor.Direction.REVERSE;
        }

        public static final RevHubOrientationOnRobot HUB_ORIENTATION =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                );
        public static class AutonomousDrive {

        }
    }
    public static class upSlider {
        public static class Name {
            public static final String ArmLeft = "ArmLeft";
            public static final String ArmRight = "ArmRight";
            public static final String SlideRight = "upSliderSlideRight";
            public static final String SLideLeft = "upSliderSlideLeft";
            public static final String hand = "hand";
            public static final String handRotation = "handRotation";
        }

        public static class Mode{
            public static final  DcMotor.RunMode slideInit = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
            public static final  DcMotor.RunMode slideMoving = DcMotor.RunMode.RUN_TO_POSITION;

        }
        public static class Direction{
            public static final DcMotor.Direction slideLeft = DcMotor.Direction.REVERSE;
            public static final DcMotor.Direction slideRight = DcMotor.Direction.FORWARD;
        }
        public static class Position{

        }
    }
    public static class bottomSlider {
        public static class Name {
            public static final String SlideRight = "lastSliderSlideRight";
            public static final String SlideLeft = "lastSliderSlideLeft";
            public static final String ArmLeft = "lastArmLeft";
            public static final String ArmRight = "lastArmRight";
            public static final String intakeHorizontalRotation = "intakeHorizontalRotation";
            public static final String intakeLiftLeft = "intakeLiftLeft";
            public static final String intakeLiftRight = "intakeLiftRight";

            public static final String climbLeft = "climbLeft";
            public static final String climbRight = "climbRight";

            public static final String intakeVerticalRotation = "intakeVerticalRotation";

        }
        public static class Direction{
            public static final Servo.Direction SlideLeft = Servo.Direction.REVERSE;
            public static final Servo.Direction SlideRight = Servo.Direction.FORWARD;
            public static final DcMotorSimple.Direction ArmLeftInit = DcMotorSimple.Direction.REVERSE;
            public static final DcMotorSimple.Direction ArmRightInit = DcMotorSimple.Direction.FORWARD;

            public static final DcMotorSimple.Direction intakeHorizontalRotation = DcMotorSimple.Direction.FORWARD;

            public static final Servo.Direction intakeLiftLeft = Servo.Direction.REVERSE;
            public static final Servo.Direction getIntakeLiftRight = Servo.Direction.FORWARD;
            public static final Servo.Direction intakeLiftRight = Servo.Direction.FORWARD;

            public static final DcMotor.Direction climbLeft = DcMotor.Direction.FORWARD;
            public static final DcMotor.Direction climbRight = DcMotor.Direction.REVERSE;

        }
        public static class Mode{
            public static final  DcMotor.RunMode climbInit = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
            public static final  DcMotor.RunMode climbMoving = DcMotor.RunMode.RUN_TO_POSITION;

        }
        public static class Power{
            public static final double ArmPowerInit = 0;
            public static final double ArmPowerCharge = 0.5;
            public static final double ArmPowerDischarge = -0.5;

            public static final double climbInit = 0;


        }
        public static class Position{
            public static final double SliderInit = 0;
            public static final double SliderHead = 0.25;
            public static final double intakeLift = 0;
            public static final double intakeLiftExtending = 0;
            public static final double intakeRotationInit = 0;

            public static final double intakeHorizontalRotationRolling = 0.8;

            public static final  int climbInit = 0;

            public static final double limitedIntakeHeight = 0.6;
            public static final double lowestIntakeHeight = 0.74;
        }
    }
    public static class Camera{
        public static class Name {

        }
        public static class Position{

        }
        public static class Size{

        }
    }

    public static class Other{
        public static final Double CONTROLLER_DEAD_ZONE = 0.1;
    }
}
