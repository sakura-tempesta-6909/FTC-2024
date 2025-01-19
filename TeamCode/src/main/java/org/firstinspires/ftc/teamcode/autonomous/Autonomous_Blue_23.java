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
package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.component.Component;
import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.component.Outtake;
import org.firstinspires.ftc.teamcode.lib.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lib.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.state.State;
import org.firstinspires.ftc.teamcode.subClass.Util;

import java.util.ArrayList;

@Autonomous(name = "Blue_23", group = "Autonomous")
public class Autonomous_Blue_23 extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    private final ArrayList<Component> components = new ArrayList<>();
    private final State state = new State();
    private SampleMecanumDrive drive;
    private TrajectorySequence mainTrajectory;

    /*
     * This is executed once after the driver presses INIT.
     * ドライバーがINITを押した後、1度実行される
     */
    @Override
    public void init() {
        state.stateInit();
        components.add(new Intake(hardwareMap));
        components.add(new Outtake(hardwareMap));

        drive = new SampleMecanumDrive(hardwareMap);
        // スタート位置の設定
        Pose2d startPose = new Pose2d(0, 60.0, Math.toRadians(90.0));
        drive.setPoseEstimate(startPose);
        mainTrajectory = drive.trajectorySequenceBuilder(startPose)
                .addTemporalMarker(() -> {
                    state.outtakeState.isOuttakeCollectorClose = true;
                    state.outtakeState.mode = State.SliderMode.HOOK_PREPARE;
                })
                // 前に進む
                .lineToLinearHeading(new Pose2d(0.0, 31.0, Math.toRadians(90)))
                // スライダーを伸ばして、下から設置する
                .addTemporalMarker(() -> {
                    state.outtakeState.mode = State.SliderMode.HOOK;
                })
                // 1秒待つ (スライダーが伸びきるまで)
                .waitSeconds(1.0)
                // 標本を離す
                .addTemporalMarker(() -> {
                    state.outtakeState.isOuttakeCollectorClose = false;
                })
                // 0.5秒待つ
                .waitSeconds(0.5)
                // スライダーを元に戻す
                .addTemporalMarker(() -> {
                    state.outtakeState.mode = State.SliderMode.HOOK_PREPARE;
                })
                // 元に戻すまで待つ
                .waitSeconds(0.5)
                // 横に移動する
                .lineToLinearHeading(new Pose2d(-35.0, 48.0, Math.toRadians(90)))
                // 前に移動する
                .lineToLinearHeading(new Pose2d(-35.0, 10.0, Math.toRadians(-90)))
                // 横に行く
                .lineToLinearHeading(new Pose2d(-50.0, 10.0, Math.toRadians(-90)))
                // 後ろに移動する (サンプルをヒューマンエリアに)
                .lineToLinearHeading(new Pose2d(-50.0, 60.0, Math.toRadians(-90)))
                .addTemporalMarker(() -> {
                    state.outtakeState.mode = State.SliderMode.INTAKE;
                })
                // 標本の前に行く
                .lineToLinearHeading(new Pose2d(-45.0, 55.0, Math.toRadians(-90)))
                // 一回目
                // 標本をつかむ準備をする
                .waitSeconds(0.3)
                // 速度を制限する
                .setAccelConstraint(new ProfileAccelerationConstraint(20.0))
                // 標本に近づく
                .lineToLinearHeading(new Pose2d(-45.0, 57.0, Math.toRadians(-90)))
                // 標本をつかむ
                .addTemporalMarker(() -> {
                    state.outtakeState.isOuttakeCollectorClose = true;
                })
                // つかみおわるまで待つ
                .waitSeconds(1.0)
                // アウトテイクを少し上に上げる
                .addTemporalMarker(() -> {
                    state.outtakeState.isIntakeUp = true;
                })
                // 上がるまで待つ
                .waitSeconds(0.5)
                // 後ろに下がる
                .lineToLinearHeading(new Pose2d(-45.0, 55.0, Math.toRadians(-90)))
                // 速度制限解除
                .resetAccelConstraint()
                // 引っかける位置に移動する
                .lineToLinearHeading(new Pose2d(5.0, 30.0, Math.toRadians(90)))
                // 少し上げるのを解除
                .addTemporalMarker(() -> {
                    state.outtakeState.isIntakeUp = false;
                    state.outtakeState.mode = State.SliderMode.HOOK_PREPARE;
                })
                // スライダーを伸ばし、フックに下から標本を引っかける
                .addTemporalMarker(() -> {
                    state.outtakeState.mode = State.SliderMode.HOOK;
                })
                // スライダーが伸びきるまで待つ
                .waitSeconds(1.0)
                // 標本を離す
                .addTemporalMarker(() -> {
                    state.outtakeState.isOuttakeCollectorClose = false;
                })
                // 離し終わるのを待つ
                .waitSeconds(0.5)
                // スライダーを縮める
                .addTemporalMarker(() -> {
                    state.outtakeState.mode = State.SliderMode.HOOK_PREPARE;
                    // スライダーを伸ばし、フックに標本を引っかける
                })
                // 横に移動する
                .lineToLinearHeading(new Pose2d(-35.0, 45.0, Math.toRadians(90)))
                // 前に移動する
                .lineToLinearHeading(new Pose2d(-45.0, 10.0, Math.toRadians(90)))
                // 横に行く
                .lineToLinearHeading(new Pose2d(-70.0, 10.0, Math.toRadians(90)))
                .addTemporalMarker(() -> {
                    state.outtakeState.mode = State.SliderMode.INTAKE;
                })
                // 後ろに移動する (サンプルをヒューマンエリアに)
                .lineToLinearHeading(new Pose2d(-70.0, 60.0, Math.toRadians(-90)))
                .build();

        drive.followTrajectorySequenceAsync(mainTrajectory);
        state.outtakeState.isOuttakeCollectorClose = true;
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

        drive.update();

        components.forEach(component -> {
            component.applyState(state);
        });
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
