package org.firstinspires.ftc.teamcode.subClass;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;

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
    public static class Arm{
        public static class Name {
            public static final String upSliderArmLeft = "upSliderArmLeft";
            public static final String upSliderArmRight= "upSliderArmRight";
            public static final String upSliderRootRight = "upSliderRootRight";
            public static final String upSliderRootLeft = "upSliderRootLeft";
            public static final String upArm = "upArm";
        }
        public static class Direction{

        }
        public static class Position{

        }
    }
    public static class Slider{
        public static class Name {
            public static final String lastSliderArmLeft = "lastSliderHeadLeft";
            public static final String lastSliderArmRight= "lastSliderHeadRight";
            public static final String lastSliderRootRight = "lastSliderRootRight";
            public static final String lastSliderRootLeft = "lastSliderRootLeft";
            public static final String lastArmLeft = "lastArmLeft";
            public static final String lastArmRight = "lastArmRight";
            public static final String intakeRotation = "intakeRotation";
            public static final String intakeLift = "intakeLift";
        }
        public static class Direction{
            public static final DcMotorSimple.Direction lastArmLeftInit = DcMotorSimple.Direction.REVERSE;
            public static final DcMotorSimple.Direction lastArmRightInit = DcMotorSimple.Direction.FORWARD;
        }
        public static class Power{
            public static final int lastArmPowerInit = 0;
            public static final double lastArmPowerCharge = 0.5;
            public static final double lastArmPowerDischarge = -0.5;
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
