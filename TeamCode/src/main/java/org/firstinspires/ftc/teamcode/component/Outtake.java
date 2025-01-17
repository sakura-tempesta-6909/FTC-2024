package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;

public class Outtake implements Component {
    public final Servo outtakeCollector;
    public final Servo outtakeLiftLeft;
    public final Servo outtakeLiftRight;
    public final DcMotor outtakeSliderLeft;
    public final DcMotor outtakeSliderRight;
    public final Servo outtakeRotation;

    public Outtake(HardwareMap hardwareMap) {
        outtakeCollector = hardwareMap.get(Servo.class, Const.outtake.Name.Collector);
        outtakeCollector.setDirection(Servo.Direction.REVERSE);
        outtakeCollector.setPosition(Const.outtake.Position.collectorOpen);

        outtakeLiftLeft = hardwareMap.get(Servo.class, Const.outtake.Name.liftLeft);
        outtakeLiftLeft.setDirection(Servo.Direction.REVERSE);
        outtakeLiftLeft.setPosition(Const.outtake.Position.liftInit);

        outtakeLiftRight = hardwareMap.get(Servo.class, Const.outtake.Name.liftRight);
        outtakeLiftRight.setDirection(Servo.Direction.FORWARD);
        outtakeLiftRight.setPosition(Const.outtake.Position.liftInit);

        outtakeRotation = hardwareMap.get(Servo.class, Const.outtake.Name.Rotation);
        outtakeRotation.setDirection(Servo.Direction.FORWARD);
        outtakeRotation.setPosition(Const.outtake.Position.rotationInit);

        outtakeSliderLeft = hardwareMap.get(DcMotor.class, Const.outtake.Name.sliderLeft);
        outtakeSliderRight = hardwareMap.get(DcMotor.class, Const.outtake.Name.sliderRight);
        outtakeSliderLeft.setDirection(Const.outtake.Direction.sliderLeft);
        outtakeSliderRight.setDirection(Const.outtake.Direction.sliderRight);
        outtakeSliderLeft.setMode(Const.outtake.Mode.sliderInit);
        outtakeSliderRight.setMode(Const.outtake.Mode.sliderInit);
        outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
        outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
        outtakeSliderLeft.setPower(Const.outtake.Power.sliderInit);
        outtakeSliderRight.setPower(Const.outtake.Power.sliderInit);
        outtakeSliderLeft.setMode(Const.outtake.Mode.sliderMoving);
        outtakeSliderRight.setMode(Const.outtake.Mode.sliderMoving);

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
        state.outtakeState.currentSliderPosition = outtakeSliderLeft.getCurrentPosition();
    }

    @Override
    public void applyState(State state) {
        // Outtakeのスライダーの状態
        switch (state.outtakeState.mode) {
            case DOWN:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                // スライダーの高さが上昇時の1/4になるまでは、liftを上げたままにする
                if (outtakeSliderLeft.getCurrentPosition() > (Const.outtake.Position.sliderUp / 4)) {
                    outtakeLiftLeft.setPosition(Const.outtake.Position.liftSet);
                    outtakeLiftRight.setPosition(Const.outtake.Position.liftSet);
                    outtakeRotation.setPosition(Const.outtake.Position.rotationSet);
                } else {
                    outtakeLiftLeft.setPosition(Const.outtake.Position.liftInit);
                    outtakeLiftRight.setPosition(Const.outtake.Position.liftInit);
                    outtakeRotation.setPosition(Const.outtake.Position.rotationInit);
                }
                break;
            case TELEOP:
                // スライダーが上がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderUp + state.outtakeState.additionalSliderPosition);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderUp + state.outtakeState.additionalSliderPosition);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftSet);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftSet);
                outtakeRotation.setPosition(Const.outtake.Position.rotationSet);
                break;
            case HOOK:
                // スライダーが上がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderAutoHook + state.outtakeState.additionalSliderPosition);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderAutoHook + state.outtakeState.additionalSliderPosition);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftAutoHook);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftAutoHook);
                outtakeRotation.setPosition(Const.outtake.Position.rotationAutoHook);
                break;
            case HOOK_PREPARE:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftAutoHookPrepare);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftAutoHookPrepare);
                outtakeRotation.setPosition(Const.outtake.Position.rotationAutoHookPrepare);
                break;
            case INTAKE:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftIntake);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftIntake);
                if (state.outtakeState.isIntakeUp) {
                    outtakeRotation.setPosition(Const.outtake.Position.rotationIntake);
                } else {
                    outtakeRotation.setPosition(Const.outtake.Position.rotationUp);
                }
                break;
        }

        // OuttakeCollector(標本をつかむ部分)の状態
        if (state.outtakeState.isOuttakeCollectorClose) {
            outtakeCollector.setPosition(Const.outtake.Position.collectorClose);
        } else {
            outtakeCollector.setPosition(Const.outtake.Position.collectorOpen);
        }
    }
}
