package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

public class Slider implements Component {

    private final Servo lastArmLeft;
    private final Servo lastArmRight;

    public Slider(HardwareMap hardwareMap) {
        lastArmLeft = hardwareMap.get(Servo.class, Const.Slider.Name.lastArmLeft);
        lastArmRight = hardwareMap.get(Servo.class, Const.Slider.Name.lastArmRight);
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
        if (state.controllerState.buttonX){
            lastArmRight.setDirection(Servo.Direction.FORWARD);
            lastArmLeft.setDirection(Servo.Direction.FORWARD);
        }else if(state.controllerState.buttonY) {
            lastArmRight.setDirection(Servo.Direction.REVERSE);
            lastArmLeft.setDirection(Servo.Direction.REVERSE);
        }

    }
}


