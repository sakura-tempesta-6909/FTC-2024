/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.component.Outtake;
import org.firstinspires.ftc.teamcode.component.Component;
import org.firstinspires.ftc.teamcode.component.Drive;
import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Util;

import java.util.ArrayList;

@TeleOp(name = "Main OpMode", group = "Main")
public class Main extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    private final ArrayList<Component> components = new ArrayList<>();
    private final State state = new State();
    private Boolean previousGamePad1B = false;
    private Boolean previousGamePad2Y = false;
    private Boolean previousGamepad1Trigger = false;
    private Boolean isFinding = false;

    /*
     * This is executed once after the driver presses INIT.
     * ドライバーがINITを押した後、1度実行される
     */

    @Override
    public void init() {
        state.stateInit();
        components.add(new Drive(hardwareMap));
        components.add(new Intake(hardwareMap));
        components.add(new Outtake(hardwareMap));
    }

    /*
     * This is executed repeatedly between INIT and PLAY.
     * ドライバーがINITを押した後からPLAYを押すまでの間、繰り返し実行される
     */
    @Override
    public void init_loop() {
        state.stateReset();
        components.forEach(component -> {
            component.readSensors(state);
        });
        Util.SendLog(state, telemetry);
    }

    /*
     * This is executed once at the start.
     * 開始時に一度だけ実行される
     */
    @Override
    public void start() {
        runtime.reset();
        state.stateInit();
    }

    /*
     * This runs continuously while enabled.
     * Enableの間ずっと実行される
     */
    @Override
    public void loop() {
        state.stateReset();
        components.forEach(component -> {
            component.readSensors(state);
        });

        //モードの設定
        state.currentMode = State.Mode.DRIVE;

        //ドライブの操作
        state.driveState.imuReset = gamepad1.start;
        state.driveState.xSpeed = Util.applyDeadZone(gamepad1.left_stick_x);
        state.driveState.ySpeed = Util.applyDeadZone(gamepad1.left_stick_y);
        state.driveState.rotation = Util.applyDeadZone(gamepad1.right_stick_x);

        if ((Util.applyDeadZone(gamepad1.right_trigger) > 0.0 || Util.applyDeadZone(gamepad1.left_trigger) > 0.0) != previousGamepad1Trigger) {
            isFinding = !isFinding;
        }
        //インテイクの状態
        if (gamepad1.right_bumper) {
            state.intakeState.mode = State.IntakeMode.CHARGE;
        } else if (gamepad1.left_bumper) {
            state.intakeState.mode = State.IntakeMode.DISCHARGE;
        } else if (isFinding) {
            state.intakeState.mode = State.IntakeMode.FINDING;
        } else {
            state.intakeState.mode = State.IntakeMode.STOP;
        }

        // 回収するサンプルの向きを変える
        if (gamepad1.b && !previousGamePad1B) {
            switch (state.intakeState.orientation) {
                case HORIZONTAL:
                    state.intakeState.orientation = State.IntakeOrientation.VERTICAL;
                    break;
                case VERTICAL:
                    state.intakeState.orientation = State.IntakeOrientation.HORIZONTAL;
                    break;
            }
        }


        // スライダーを上げる
        if (gamepad2.dpad_down) {
            state.outtakeState.mode = State.SliderMode.DOWN;
            state.outtakeState.additionalSliderPosition = 0;
        } else if (gamepad2.dpad_up) {
            state.outtakeState.mode = State.SliderMode.TELEOP;
        } else if (gamepad2.x) {
            state.outtakeState.mode = State.SliderMode.HOOK_PREPARE;
        } else if (gamepad2.a) {
            state.outtakeState.mode = State.SliderMode.HOOK;
        } else if (gamepad2.start) {
            state.outtakeState.mode = State.SliderMode.INTAKE;
        }

        if (gamepad2.dpad_left) {
            state.outtakeState.additionalSliderPosition -= 10;
        } else if (gamepad2.dpad_right) {
            state.outtakeState.additionalSliderPosition += 10;
        }

        state.outtakeState.isIntakeUp = gamepad2.right_bumper;

        //outtakeChargeのトグル
        if (gamepad2.y && !previousGamePad2Y) {
            state.outtakeState.isOuttakeCollectorClose = !state.outtakeState.isOuttakeCollectorClose;
        }

        state.driveState.imuReset = gamepad1.start;

        components.forEach(component -> {
            component.applyState(state);
        });

        //ゲームパッドの状態の保存
        previousGamePad1B = gamepad1.b;
        previousGamePad2Y = gamepad2.y;
        previousGamepad1Trigger = Util.applyDeadZone(gamepad1.right_trigger) > 0.0 || Util.applyDeadZone(gamepad1.left_trigger) > 0.0;

        //ログの送信
        Util.SendLog(state, telemetry);
    }

    /*
     * This is executed once when the code is stopped.
     * コードが停止されるときに一度だけ実行される
     */
    @Override
    public void stop() {
    }
}
