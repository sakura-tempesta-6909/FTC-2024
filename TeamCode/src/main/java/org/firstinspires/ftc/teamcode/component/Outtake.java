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
    public final DcMotor hangerLeft;
    public final DcMotor hangerRight;

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
        outtakeSliderLeft.setMode(Const.outtake.Mode.encoderInit);
        outtakeSliderRight.setMode(Const.outtake.Mode.encoderInit);
        outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
        outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
        outtakeSliderLeft.setPower(Const.outtake.Power.sliderInit);
        outtakeSliderRight.setPower(Const.outtake.Power.sliderInit);
        outtakeSliderLeft.setMode(Const.outtake.Mode.encoderMoving);
        outtakeSliderRight.setMode(Const.outtake.Mode.encoderMoving);

        hangerLeft = hardwareMap.get(DcMotor.class, Const.outtake.Name.hangerLeft);
        hangerRight = hardwareMap.get(DcMotor.class, Const.outtake.Name.hangerRight);
        hangerLeft.setDirection(DcMotor.Direction.REVERSE);
        hangerRight.setDirection(DcMotor.Direction.FORWARD);
        hangerLeft.setMode(Const.outtake.Mode.encoderInit);
        hangerRight.setMode(Const.outtake.Mode.encoderInit);
        hangerLeft.setTargetPosition(Const.outtake.Position.hangerHook);
        hangerRight.setTargetPosition(Const.outtake.Position.hangerHook);
        hangerLeft.setPower(Const.outtake.Power.hangerInit);
        hangerRight.setPower(Const.outtake.Power.hangerInit);
        hangerLeft.setMode(Const.outtake.Mode.encoderMoving);
        hangerRight.setMode(Const.outtake.Mode.encoderMoving);
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
        state.outtakeState.currentHangerLeftPosition = hangerLeft.getCurrentPosition();
        state.outtakeState.currentHangerRightPosition = hangerRight.getCurrentPosition();
    }

    @Override
    public void applyState(State state) {
        // Outtakeのスライダーの状態
        switch (state.outtakeState.mode) {
            case INIT:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                // スライダーの高さが上昇時の1/4になるまでは、liftを上げたままにする
                if (outtakeSliderLeft.getCurrentPosition() > (Const.outtake.Position.sliderUp / 4)) {
                    outtakeLiftLeft.setPosition(Const.outtake.Position.liftSet + state.outtakeState.additionalOuttakePosition);
                    outtakeLiftRight.setPosition(Const.outtake.Position.liftSet + state.outtakeState.additionalOuttakePosition);
                    outtakeRotation.setPosition(Const.outtake.Position.rotationSet);
                } else {
                    outtakeLiftLeft.setPosition(Const.outtake.Position.liftInit + state.outtakeState.additionalOuttakePosition);
                    outtakeLiftRight.setPosition(Const.outtake.Position.liftInit + state.outtakeState.additionalOuttakePosition);
                    outtakeRotation.setPosition(Const.outtake.Position.rotationInit);
                }
                break;
            case DOWN:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftSet + state.outtakeState.additionalOuttakePosition);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftSet + state.outtakeState.additionalOuttakePosition);
                outtakeRotation.setPosition(Const.outtake.Position.rotationSet);
                break;
            case TELEOP:
                // スライダーが上がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderUp + state.outtakeState.additionalSliderPosition);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderUp + state.outtakeState.additionalSliderPosition);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftSet + state.outtakeState.additionalOuttakePosition);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftSet + state.outtakeState.additionalOuttakePosition);
                outtakeRotation.setPosition(Const.outtake.Position.rotationSet);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                break;
            case HOOK:
                // スライダーが上がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderAutoHook + state.outtakeState.additionalSliderPosition);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderAutoHook + state.outtakeState.additionalSliderPosition);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftAutoHook + state.outtakeState.additionalOuttakePosition);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftAutoHook + state.outtakeState.additionalOuttakePosition);
                outtakeRotation.setPosition(Const.outtake.Position.rotationAutoHook);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                break;
            case HOOK_PREPARE:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftAutoHookPrepare + state.outtakeState.additionalOuttakePosition);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftAutoHookPrepare + state.outtakeState.additionalOuttakePosition);
                outtakeRotation.setPosition(Const.outtake.Position.rotationAutoHookPrepare);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                break;
            case INTAKE:
                // スライダーが下がった状態
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftIntake + state.outtakeState.additionalOuttakePosition);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftIntake + state.outtakeState.additionalOuttakePosition);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                if (state.outtakeState.isIntakeUp) {
                    outtakeRotation.setPosition(Const.outtake.Position.rotationIntake);
                } else {
                    outtakeRotation.setPosition(Const.outtake.Position.rotationUp);
                }
                break;
            case CLIMB_PREPARE:
            case CLIMB_UP:
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftInit);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftInit);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                outtakeRotation.setPosition(Const.outtake.Position.rotationInit);
                break;
            case CLIMB:
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderClimb);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderClimb);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftInit);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftInit);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerInit);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                outtakeRotation.setPosition(Const.outtake.Position.rotationInit);
                break;
            case CLIMB_HOOK:
                outtakeSliderLeft.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderRight.setTargetPosition(Const.outtake.Position.sliderInit);
                outtakeSliderLeft.setPower(Const.outtake.Power.sliderMoving);
                outtakeSliderRight.setPower(Const.outtake.Power.sliderMoving);
                outtakeLiftLeft.setPosition(Const.outtake.Position.liftInit);
                outtakeLiftRight.setPosition(Const.outtake.Position.liftInit);
                hangerLeft.setTargetPosition(Const.outtake.Position.hangerHook);
                hangerRight.setTargetPosition(Const.outtake.Position.hangerHook);
                hangerLeft.setPower(Const.outtake.Power.hangerMoving);
                hangerRight.setPower(Const.outtake.Power.hangerMoving);
                outtakeRotation.setPosition(Const.outtake.Position.rotationInit);
        }

        // OuttakeCollector(標本をつかむ部分)の状態
        if (state.outtakeState.isOuttakeCollectorClose) {
            outtakeCollector.setPosition(Const.outtake.Position.collectorClose);
        } else {
            outtakeCollector.setPosition(Const.outtake.Position.collectorOpen);
        }
    }
}
