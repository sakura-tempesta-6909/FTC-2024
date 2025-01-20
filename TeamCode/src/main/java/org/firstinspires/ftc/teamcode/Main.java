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

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.component.Outtake;
import org.firstinspires.ftc.teamcode.component.Component;
import org.firstinspires.ftc.teamcode.component.Drive;
import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Const;
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
    private Boolean previousModeChange = false;
    private Boolean previousDpadDown = false;
    private Boolean previousDpadUp = false;
    private double previousTime = 0.0;
    private State.SliderMode previousSliderMode = State.SliderMode.INIT;

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
        state.currentRuntime = getRuntime();
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
        state.currentRuntime = getRuntime();

        //モードの設定
        if ((gamepad1.back && gamepad2.back) != previousModeChange && (gamepad1.back && gamepad2.back)) {
            if (state.currentMode == State.Mode.DRIVE) {
                state.currentMode = State.Mode.CLIMB;
                state.outtakeState.mode = State.SliderMode.CLIMB_PREPARE;
            } else {
                state.currentMode = State.Mode.DRIVE;
            }
        }
        //ドライブの操作
        state.driveState.imuReset = gamepad1.start;
        state.driveState.xSpeed = Util.applyDeadZone(gamepad1.left_stick_x);
        state.driveState.ySpeed = Util.applyDeadZone(gamepad1.left_stick_y);
        state.driveState.rotation = Util.applyDeadZone(gamepad1.right_stick_x);

        //outtakeChargeのトグル
        state.outtakeState.isIntakeUp = gamepad2.right_bumper;
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


        if (gamepad2.y && gamepad2.y != previousGamePad2Y && previousTime == 0.0) {
            state.outtakeState.isOuttakeCollectorClose = !state.outtakeState.isOuttakeCollectorClose;
        }

        //　クライムモードとドライブモードの判別
        if (state.currentMode == State.Mode.DRIVE) {
            // コレクターが開いたこと(0.5秒)を確認したら、スライダーを下げる
            if ((getRuntime() - previousTime) >= 0.5 && previousTime != 0.0) {
                state.outtakeState.mode = State.SliderMode.DOWN;
                previousTime = 0.0;
            }
            // スライダーを上げる
            if (gamepad2.dpad_down && gamepad2.dpad_down != previousDpadDown) {
                // 現在のスライダーのモードがDOWNだったら、INITまで戻す
                if (state.outtakeState.mode == State.SliderMode.DOWN) {
                    state.outtakeState.mode = State.SliderMode.INIT;
                    state.outtakeState.additionalSliderPosition = 0;
                    previousTime = 0.0;
                } else if (state.outtakeState.mode == State.SliderMode.TELEOP && (state.outtakeState.isOuttakeCollectorClose || previousTime != 0.0)) {
                    if (previousTime == 0.0) previousTime = getRuntime();
                    state.outtakeState.mode = State.SliderMode.TELEOP;
                    state.outtakeState.isOuttakeCollectorClose = false;
                } else if (state.outtakeState.mode != State.SliderMode.INIT) {
                    state.outtakeState.mode = State.SliderMode.DOWN;
                    state.outtakeState.additionalSliderPosition = 0;
                    previousTime = 0.0;
                }
            } else if (gamepad2.dpad_up) {
                state.outtakeState.mode = State.SliderMode.TELEOP;
            } else if (gamepad2.start) {
                if (state.outtakeState.mode == State.SliderMode.TELEOP) {
                    state.outtakeState.mode = State.SliderMode.DOWN;
                } else {
                    state.outtakeState.mode = State.SliderMode.INTAKE;
                }
            }
        } else if (state.currentMode == State.Mode.CLIMB) {
            if (gamepad2.dpad_down && gamepad2.dpad_down != previousDpadDown) {
                if (state.outtakeState.mode == State.SliderMode.CLIMB_HOOK) {
                    state.outtakeState.mode = State.SliderMode.CLIMB_UP;
                } else if (state.outtakeState.mode == State.SliderMode.CLIMB_UP) {
                    state.outtakeState.mode = State.SliderMode.CLIMB;
                } else if (state.outtakeState.mode == State.SliderMode.CLIMB) {
                    state.outtakeState.mode = State.SliderMode.CLIMB_PREPARE;
                }
            } else if (gamepad2.dpad_up && gamepad2.dpad_up != previousDpadUp) {
                if (state.outtakeState.mode == State.SliderMode.CLIMB_PREPARE) {
                    state.outtakeState.mode = State.SliderMode.CLIMB;
                } else if (state.outtakeState.mode == State.SliderMode.CLIMB) {
                    state.outtakeState.mode = State.SliderMode.CLIMB_UP;
                } else if (state.outtakeState.mode == State.SliderMode.CLIMB_UP) {
                    state.outtakeState.mode = State.SliderMode.CLIMB_HOOK;
                }
            }
        }

        if (gamepad2.dpad_left) {
            state.outtakeState.additionalSliderPosition -= 10;
        } else if (gamepad2.dpad_right) {
            state.outtakeState.additionalSliderPosition += 10;
        }

        if (gamepad1.dpad_up) {
            state.outtakeState.additionalOuttakePosition += 10;
        } else if (gamepad1.dpad_down) {
            state.outtakeState.additionalOuttakePosition -= 10;
        }
        if (previousSliderMode != state.outtakeState.mode) {
            state.outtakeState.additionalOuttakePosition = 0.0;
            state.outtakeState.additionalClimbPosition = 0.0;
        }
        components.forEach(component -> {
            component.applyState(state);
        });

        //ゲームパッドの状態の保存
        previousGamePad1B = gamepad1.b;
        previousGamepad1Trigger = Util.applyDeadZone(gamepad1.right_trigger) > 0.0 || Util.applyDeadZone(gamepad1.left_trigger) > 0.0;
        previousDpadDown = gamepad2.dpad_down;
        previousGamePad2Y = gamepad2.y;
        previousDpadUp = gamepad2.dpad_up;
        previousModeChange = gamepad1.back && gamepad2.back;
        previousSliderMode = state.outtakeState.mode;

        //ログの送信
        Util.SendLog(state, telemetry);
        telemetry.addData("Runtime", getRuntime());
        telemetry.addData("RuntimeDiff", (getRuntime() - previousTime));
        telemetry.addData("Previous", previousTime);
    }

    /*
     * This is executed once when the code is stopped.
     * コードが停止されるときに一度だけ実行される
     */
    @Override
    public void stop() {
    }
}
