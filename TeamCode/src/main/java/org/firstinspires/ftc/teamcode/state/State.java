package org.firstinspires.ftc.teamcode.state;

public class State {
    public enum Mode {
        STOP, //停止中
        DRIVE, //ドライブモード
    }

    // Robot operation mode
    // ロボットの動作モード
    public Mode currentMode;

    public static class DriveState {
        // Current heading of the robot in radians
        // ロボットの現在の方位（ラジアン）
        public double botHeading;
        // Flag to indicate if IMU reset is needed
        // IMUリセットが必要かどうかを示すフラグ
        public boolean imuReset;
    }

    public static class ControllerState {
        public double leftStickX;
        public double leftStickY;
        public double rightStickX;
        public boolean buttonY;
        public boolean buttonX;
    }

    // Instances of the sub-classes
    // サブクラスのインスタンス
    public DriveState driveState = new DriveState();
    public ControllerState controllerState = new ControllerState();

    public void stateInit() {
        // General
        this.currentMode = Mode.STOP;
        // DriveState
        this.driveState.imuReset = false;
        this.driveState.botHeading = 0.0;
        // ControllerState
        this.controllerState.leftStickX = 0.0;
        this.controllerState.leftStickY = 0.0;
        this.controllerState.rightStickX = 0.0;
        this.controllerState.buttonY = false;
        this.controllerState.buttonX = false;
    }

    public void stateReset() {
        // DriveState
        this.driveState.botHeading = 0.0;
        // ControllerState
        this.controllerState.leftStickX = 0.0;
        this.controllerState.leftStickY = 0.0;
        this.controllerState.rightStickX = 0.0;
        this.controllerState.buttonY = false;
        this.controllerState.buttonX = false;
    }
}
