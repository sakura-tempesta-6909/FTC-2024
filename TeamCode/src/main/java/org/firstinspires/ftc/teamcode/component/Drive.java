package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;

public class Drive implements Component {
    private final DcMotor leftFront;
    private final DcMotor leftRear;
    private final DcMotor rightFront;
    private final DcMotor rightRear;
    private final IMU imu;

    public Drive(HardwareMap hardwareMap) {
        // Get motors from hardware map
        // モーターをハードウェアマップから取得する
        leftFront = hardwareMap.get(DcMotor.class, Const.Drive.Name.leftFront);
        leftFront.setDirection(Const.Drive.Direction.leftFront);
        rightFront = hardwareMap.get(DcMotor.class, Const.Drive.Name.rightFront);
        rightFront.setDirection(Const.Drive.Direction.rightFront);
        leftRear = hardwareMap.get(DcMotor.class, Const.Drive.Name.leftRear);
        leftRear.setDirection(Const.Drive.Direction.leftRear);
        rightRear = hardwareMap.get(DcMotor.class, Const.Drive.Name.rightRear);
        rightRear.setDirection(Const.Drive.Direction.rightRear);

        // Set zero power behavior for motors to brake mode
        // モーターのゼロパワー時の挙動をブレーキモードに設定する
        leftFront.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);

        // Initialize IMU
        // IMU（慣性計測装置）の初期化
        imu = hardwareMap.get(IMU.class, Const.Drive.Name.imu);
        imu.resetYaw();
        IMU.Parameters parameters = new IMU.Parameters(Const.Drive.HUB_ORIENTATION);
        imu.initialize(parameters);
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
        // Get the robot's current heading in radians
        // ロボットの現在の方位をラジアンで取得する
        state.driveState.botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    @Override
    public void applyState(State state) {
        // Convert joystick values to field-centric coordinates
        // ジョイスティックの値をフィールドセントリック座標に変換する
        double rotX = state.driveState.xSpeed * Math.cos(-state.driveState.botHeading) - (-state.driveState.ySpeed) * Math.sin(-state.driveState.botHeading);
        double rotY = state.driveState.xSpeed * Math.sin(-state.driveState.botHeading) + (-state.driveState.ySpeed) * Math.cos(-state.driveState.botHeading);

        // Apply a correction factor for strafing
        // ストレーフの補正係数を適用する
        rotX *= 1.1;

        // Normalize motor power to ensure no value exceeds 1.0
        // モーターのパワーを正規化し、1.0を超えないようにする
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(-state.driveState.xSpeed), 1.0);

        // Calculate motor power values
        // モーターのパワー値を計算する
        double leftFrontPower = (rotY + rotX - state.driveState.rotation) / denominator;
        double leftRearPower = (rotY - rotX - state.driveState.rotation) / denominator;
        double rightFrontPower = (rotY - rotX + state.driveState.rotation) / denominator;
        double rightRearPower = (rotY + rotX + state.driveState.rotation) / denominator;

        // Set motor power to respective motors
        // 各モーターにパワーを設定する
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftRearPower);
        rightRear.setPower(rightRearPower);
    }
}
