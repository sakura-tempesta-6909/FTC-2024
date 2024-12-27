package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;

public class Slider implements Component {

    private final CRServo lastArmLeft;
    private final CRServo lastArmRight;
    public final Servo lastSliderSlideRight;
    public final Servo lastSliderSlideLeft;
    public final Servo intakeHorizontalRotation;
    public final Servo intakeLiftLeft ;
    public final Servo intakeLiftRight ;
//    public  final DcMotor climbLeft;
//    public  final DcMotor climbRight;

    public final Servo intakeVerticalRotation;
    public Slider(HardwareMap hardwareMap) {
//        climbLeft = hardwareMap.get(DcMotor.class,Const.bottomSlider.Name.climbLeft);
//        climbRight = hardwareMap.get(DcMotor.class,Const.bottomSlider.Name.climbRight);
//        climbLeft.setDirection(Const.bottomSlider.Direction.climbLeft);
//        climbRight.setDirection(Const.bottomSlider.Direction.climbRight);
//        climbLeft.setMode(Const.bottomSlider.Mode.climbInit);
//        climbRight.setMode(Const.bottomSlider.Mode.climbInit);
//        climbLeft.setTargetPosition(Const.bottomSlider.Position.climbInit);
//        climbRight.setTargetPosition(Const.bottomSlider.Position.climbInit);
//        climbLeft.setPower(Const.bottomSlider.Power.climbInit);
//        climbRight.setPower(Const.bottomSlider.Power.climbInit);
//        climbLeft.setMode(Const.bottomSlider.Mode.climbMoving);
//        climbRight.setMode(Const.bottomSlider.Mode.climbMoving);

        lastArmLeft = hardwareMap.get(CRServo.class, Const.bottomSlider.Name.ArmLeft);
        lastArmRight = hardwareMap.get(CRServo.class, Const.bottomSlider.Name.ArmRight);
        lastArmRight.setPower(Const.bottomSlider.Power.ArmPowerInit);
        lastArmLeft.setPower(Const.bottomSlider.Power.ArmPowerInit);
        lastArmLeft.setDirection(Const.bottomSlider.Direction.ArmLeftInit);
        lastArmRight.setDirection(Const.bottomSlider.Direction.ArmRightInit);

        lastSliderSlideLeft = hardwareMap.get(Servo.class, Const.bottomSlider.Name.SlideLeft);
        lastSliderSlideRight = hardwareMap.get(Servo.class, Const.bottomSlider.Name.SlideRight);
        lastSliderSlideLeft.setDirection(Const.bottomSlider.Direction.SlideLeft);
        lastSliderSlideRight.setDirection(Const.bottomSlider.Direction.SlideRight);
        lastSliderSlideLeft.setPosition(Const.bottomSlider.Position.SliderInit);
        lastSliderSlideRight.setPosition(Const.bottomSlider.Position.SliderInit);

        intakeHorizontalRotation = hardwareMap.get(Servo.class, Const.bottomSlider.Name.intakeHorizontalRotation);
        intakeHorizontalRotation.setPosition(0);
        intakeHorizontalRotation.setDirection(Servo.Direction.FORWARD);

        intakeVerticalRotation =  hardwareMap.get(Servo.class, Const.bottomSlider.Name.intakeVerticalRotation);
        intakeVerticalRotation.setDirection(Servo.Direction.FORWARD);
        intakeVerticalRotation.setPosition(Const.bottomSlider.Position.intakeRotationInit);

        intakeLiftLeft = hardwareMap.get(Servo.class, Const.bottomSlider.Name.intakeLiftLeft);
        intakeLiftLeft.setDirection(Const.bottomSlider.Direction.intakeLiftLeft);
        intakeLiftLeft.setPosition(Const.bottomSlider.Position.intakeLift);

        intakeLiftRight = hardwareMap.get(Servo.class, Const.bottomSlider.Name.intakeLiftRight);
        intakeLiftRight.setDirection(Const.bottomSlider.Direction.intakeLiftRight);
        intakeLiftRight.setPosition(Const.bottomSlider.Position.intakeLift);
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
        if (state.driveState.charge){
            lastArmRight.setPower(Const.bottomSlider.Power.ArmPowerCharge);
            lastArmLeft.setPower(Const.bottomSlider.Power.ArmPowerCharge);
        }else if(state.driveState.discharge) {
            lastArmRight.setPower(Const.bottomSlider.Power.ArmPowerDischarge);
            lastArmLeft.setPower(Const.bottomSlider.Power.ArmPowerDischarge);
        }else{
            lastArmRight.setPower(Const.bottomSlider.Power.ArmPowerInit);
            lastArmLeft.setPower(Const.bottomSlider.Power.ArmPowerInit);
        }

        if (state.driveState.intakeRotation){
            intakeHorizontalRotation.setPosition(Const.bottomSlider.Position.intakeHorizontalRotationRolling);
        }else{
            intakeHorizontalRotation.setPosition(Const.bottomSlider.Position.intakeRotationInit);
        }

        if (state.driveState.sliderIsOut){
            lastSliderSlideLeft.setPosition(Const.bottomSlider.Position.SliderHead);
            lastSliderSlideRight.setPosition(Const.bottomSlider.Position.SliderHead);
        }else{
            lastSliderSlideLeft.setPosition(Const.bottomSlider.Position.SliderInit);
            lastSliderSlideRight.setPosition(Const.bottomSlider.Position.SliderInit);
        }


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

        if (state.driveState.liftIsDown && !state.driveState.charge){
            //b
            intakeLiftLeft.setPosition(Const.bottomSlider.Position.limitedIntakeHeight);
            intakeLiftRight.setPosition(Const.bottomSlider.Position.limitedIntakeHeight);
        } else if(state.driveState.liftIsDown) {
            intakeLiftLeft.setPosition(Const.bottomSlider.Position.lowestIntakeHeight);
            intakeLiftRight.setPosition(Const.bottomSlider.Position.lowestIntakeHeight);
        }else{
            intakeLiftLeft.setPosition(0);
            intakeLiftRight.setPosition(0);
        }

        if (state.driveState.verticalRotation){
            //a
            //キャッチ
            intakeVerticalRotation.setPosition(1);
        }else{
            //普通
            intakeVerticalRotation.setPosition(0);
        }

    }
}