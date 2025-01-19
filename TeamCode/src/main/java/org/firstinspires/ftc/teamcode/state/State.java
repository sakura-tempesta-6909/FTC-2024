package org.firstinspires.ftc.teamcode.state;

public class State {
    public enum Mode {
        DRIVE, //ドライブモード
        CLIMB, //クライムモード
    }

    // Robot operation mode
    // ロボットの動作モード
    public Mode currentMode;
    public double currentRuntime;

    public static class DriveState {
        // Current heading of the robot in radians
        // ロボットの現在の方位（ラジアン）
        public double botHeading;
        // Flag to indicate if IMU reset is needed
        // IMUリセットが必要かどうかを示すフラグ
        public boolean imuReset;
        public double xSpeed;
        public double ySpeed;
        public double rotation;
    }

    //インテイクの状態
    public enum IntakeMode {
        INIT,
        STOP, // 何もしない
        FINDING,
        CHARGE, // 回収
        DISCHARGE, // 吐き出し
    }

    public enum IntakeOrientation {
        HORIZONTAL, // 横向き
        VERTICAL    // 縦向き
    }

    public static class IntakeState {
        public IntakeMode mode;
        public IntakeOrientation orientation;
    }

    public enum SliderMode {
        INIT,
        DOWN,
        TELEOP,
        HOOK,
        HOOK_PREPARE,
        INTAKE,
        AUTO_END,
        CLIMB,
        CLIMB_PREPARE,
        CLIMB_HOOK,
    }

    public static class OuttakeState {
        public SliderMode mode;
        public int additionalSliderPosition;
        public boolean isOuttakeCollectorClose;
        public double currentSliderPosition;
        public boolean isIntakeUp;
        public int currentHangerLeftPosition;
        public int currentHangerRightPosition;
        public int additionalHangerPosition;
    }

    // Instances of the subclasses
    // サブクラスのインスタンス
    public DriveState driveState = new DriveState();
    public IntakeState intakeState = new IntakeState();
    public OuttakeState outtakeState = new OuttakeState();

    public void stateInit() {
        // General
        this.currentMode = Mode.DRIVE;
        // DriveState
        this.driveState.imuReset = false;
        this.driveState.botHeading = 0.0;
        this.driveState.xSpeed = 0.0;
        this.driveState.ySpeed = 0.0;
        this.driveState.rotation = 0.0;
        this.outtakeState.isOuttakeCollectorClose = true;

        // IntakeState
        this.intakeState.mode = IntakeMode.STOP;
        this.intakeState.orientation = IntakeOrientation.VERTICAL;

        // OuttakeState
        this.outtakeState.mode = SliderMode.INIT;
        this.outtakeState.additionalSliderPosition = 0;
        this.outtakeState.currentSliderPosition = 0;
        this.outtakeState.isIntakeUp = false;
        this.outtakeState.additionalHangerPosition = 0;
        this.outtakeState.currentHangerLeftPosition = 0;
        this.outtakeState.currentHangerRightPosition = 0;
    }

    public void stateReset() {
        // DriveState
        this.driveState.botHeading = 0.0;
        this.driveState.xSpeed = 0.0;
        this.driveState.ySpeed = 0.0;
        this.driveState.rotation = 0.0;

        // OuttakeState
        this.outtakeState.currentSliderPosition = 0;
        this.outtakeState.currentHangerLeftPosition = 0;
        this.outtakeState.currentHangerRightPosition = 0;
    }
}