package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

public class Slider implements Component {

    private final CRServo lastArmLeft;
    private final CRServo lastArmRight;

    public Slider(HardwareMap hardwareMap) {
        lastArmLeft = hardwareMap.get(CRServo.class, Const.Slider.Name.lastArmLeft);
        lastArmRight = hardwareMap.get(CRServo.class, Const.Slider.Name.lastArmRight);
        lastArmRight.setPower(Const.Slider.Power.lastArmPowerInit);
        lastArmLeft.setPower(Const.Slider.Power.lastArmPowerInit);
        lastArmLeft.setDirection(Const.Slider.Direction.lastArmLeftInit);
        lastArmRight.setDirection(Const.Slider.Direction.lastArmRightInit);
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

    }
}


