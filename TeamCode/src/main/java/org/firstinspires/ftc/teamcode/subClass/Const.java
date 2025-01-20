package org.firstinspires.ftc.teamcode.subClass;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Const {
    public static class Drive {
        public static class Name {
            public static final String leftFront = "leftFront";
            public static final String rightFront = "rightFront";
            public static final String leftRear = "leftRear";
            public static final String rightRear = "rightRear";
            public static final String imu = "imu";
        }

        public static class Power {

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

    public static class outtake {
        public static class Name {
            public static final String liftLeft = "outtakeLiftLeft";
            public static final String liftRight = "outtakeLiftRight";
            public static final String sliderRight = "outtakeSliderRight";
            public static final String sliderLeft = "outtakeSliderLeft";
            public static final String Collector = "outtakeCollector";
            public static final String Rotation = "outtakeCollectorRotation";
            public static final String hangerLeft = "hangerLeft";
            public static final String hangerRight = "hangerRight";
        }

        public static class Mode {
            public static final DcMotor.RunMode encoderInit = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
            public static final DcMotor.RunMode encoderMoving = DcMotor.RunMode.RUN_TO_POSITION;

        }

        public static class Direction {
            public static final DcMotor.Direction sliderLeft = DcMotor.Direction.FORWARD;
            public static final DcMotor.Direction sliderRight = DcMotor.Direction.REVERSE;
        }

        public static class Position {
            public static final double collectorOpen = 0;
            public static final double collectorClose = 0.24;
            public static final double liftInit = 0;
            public static final double liftUp = 1.0;
            public static final double liftIntake = 0.8;
            public static final double liftSet = 0.7;
            public static final double liftAutoHookPrepare = 0.7;
            public static final double liftAutoHook = 0.7;
            public static final double rotationInit = 0;
            public static final double rotationUp = 0.6;
            public static final double rotationIntake = 0.2;
            public static final double rotationSet = 0.18;
            public static final double rotationAutoHook = 0.05;
            public static final double rotationAutoHookPrepare = 0.20;
            public static final int sliderInit = 0;
            public static final int sliderUp = 1200;
            public static final int sliderAutoHook = 1650;
            public static final int hangerHook = 30;
            public static final int hangerInit = 70;
            public static final int sliderClimb = 1500;
        }

        public static class Power {
            public static final double sliderInit = 0;
            public static final double sliderMoving = 0.8;
            public static final double hangerInit = 0;
            public static final double hangerMoving = 0.8;
        }
    }

    public static class intake {
        public static class Name {
            public static final String sliderRight = "intakeSliderRight";
            public static final String sliderLeft = "intakeSliderLeft";
            public static final String collectorLeft = "intakeCollectorLeft";
            public static final String collectorRight = "intakeCollectorRight";
            public static final String horizontalRotation = "intakeHorizontalRotation";
            public static final String liftLeft = "intakeLiftLeft";
            public static final String liftRight = "intakeLiftRight";
            public static final String verticalRotation = "intakeVerticalRotation";

        }

        public static class Direction {
            public static final Servo.Direction sliderLeft = Servo.Direction.REVERSE;
            public static final Servo.Direction sliderRight = Servo.Direction.FORWARD;
            public static final DcMotorSimple.Direction collectorLeftInit = DcMotorSimple.Direction.FORWARD;
            public static final DcMotorSimple.Direction collectorRightInit = DcMotorSimple.Direction.REVERSE;

            public static final DcMotorSimple.Direction HorizontalRotation = DcMotorSimple.Direction.FORWARD;

            public static final Servo.Direction liftLeft = Servo.Direction.REVERSE;
            public static final Servo.Direction liftRight = Servo.Direction.FORWARD;

            public static final DcMotor.Direction climbLeft = DcMotor.Direction.FORWARD;
            public static final DcMotor.Direction climbRight = DcMotor.Direction.REVERSE;

        }

        public static class Mode {
            public static final DcMotor.RunMode climbInit = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
            public static final DcMotor.RunMode climbMoving = DcMotor.RunMode.RUN_TO_POSITION;

        }

        public static class Power {
            public static final double collectorPowerInit = 0;
            public static final double ArmPowerCharge = 0.5;
            public static final double ArmPowerDischarge = -0.5;

            public static final double climbInit = 0;


        }

        public static class Position {
            public static final double sliderInit = 0;
            public static final double sliderHead = 0.25;
            public static final double liftInit = 0;
            public static final double verticalRotationUpper = 0.1;
            public static final double verticalRotationInit = 0.46;
            public static final double verticalRotationSide = 1;
            public static final double horizontalRotationInit = 0;
            public static final double horizontalRotationMoving = 0.3;
            public static final double liftLimited = 0.6;
            public static final double liftLowest = 0.74;
        }
    }

    public static class Other {
        public static final Double CONTROLLER_DEAD_ZONE = 0.1;
    }
}