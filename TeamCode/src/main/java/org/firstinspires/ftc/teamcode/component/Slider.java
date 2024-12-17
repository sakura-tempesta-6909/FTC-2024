package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;

public class Slider implements Component {

    private final CRServo lastArmLeft;
    private final CRServo lastArmRight;

    public final Servo lastSliderSlideRight;
    public final Servo lastSliderSlideLeft;

    public final CRServo intakeRotation;

    public final Servo intakeLiftLeft ;
    public final Servo intakeLiftRight;


    public Slider(HardwareMap hardwareMap) {
        lastArmLeft = hardwareMap.get(CRServo.class, Const.Slider.Name.lastArmLeft);
        lastArmRight = hardwareMap.get(CRServo.class, Const.Slider.Name.lastArmRight);
        lastArmRight.setPower(Const.Slider.Power.lastArmPowerInit);
        lastArmLeft.setPower(Const.Slider.Power.lastArmPowerInit);
        lastArmLeft.setDirection(Const.Slider.Direction.lastArmLeftInit);
        lastArmRight.setDirection(Const.Slider.Direction.lastArmRightInit);

        lastSliderSlideLeft = hardwareMap.get(Servo.class,Const.Slider.Name.lastSliderSlideLeft);
        lastSliderSlideRight = hardwareMap.get(Servo.class,Const.Slider.Name.lastSliderSlideRight);
        lastSliderSlideLeft.setDirection(Const.Slider.Direction.lastSliderSlideLeft);
        lastSliderSlideRight.setDirection(Const.Slider.Direction.lastSliderSlideRight);
        lastSliderSlideLeft.setPosition(Const.Slider.Position.lastSliderInit);
        lastSliderSlideRight.setPosition(Const.Slider.Position.lastSliderInit);


        intakeRotation = hardwareMap.get(CRServo.class,Const.Slider.Name.intakeRotation);
        intakeRotation.setDirection(Const.Slider.Direction.intakeRotation);
        intakeRotation.setPower(Const.Slider.Power.intakeRotationInit);

        intakeLiftLeft = hardwareMap.get(Servo.class,Const.Slider.Name.intakeLiftLeft);
        intakeLiftRight = hardwareMap.get(Servo.class,Const.Slider.Name.intakeLiftRight);
        intakeLiftLeft.setDirection(Const.Slider.Direction.intakeLiftLeft);
        intakeLiftRight.setDirection(Const.Slider.Direction.intakeLiftRight);
        intakeLiftLeft.setPosition(Const.Slider.Position.intakeLiftInit);
        intakeLiftRight.setPosition(Const.Slider.Position.intakeLiftInit);
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
            lastArmRight.setPower(Const.Slider.Power.lastArmPowerCharge);
            lastArmLeft.setPower(Const.Slider.Power.lastArmPowerCharge);
        }else if(state.driveState.discharge) {
            lastArmRight.setPower(Const.Slider.Power.lastArmPowerDischarge);
            lastArmLeft.setPower(Const.Slider.Power.lastArmPowerDischarge);
        }else{
            lastArmRight.setPower(Const.Slider.Power.lastArmPowerInit);
            lastArmLeft.setPower(Const.Slider.Power.lastArmPowerInit);
        }
        if (state.driveState.intakeRotation){
            intakeRotation.setPower(Const.Slider.Power.intakeRotationRolling);
        }else{
            intakeRotation.setPower(Const.Slider.Power.intakeRotationInit);
        }
    }
}


