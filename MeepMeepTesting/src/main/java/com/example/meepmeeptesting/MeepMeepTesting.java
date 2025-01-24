package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(55, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(0, -60.0, Math.toRadians(-90)))
                        .waitSeconds(1.0)
                        .addTemporalMarker(() -> {
                        })
                        // 前に進む
                        .lineToLinearHeading(new Pose2d(0.0, -31.0, Math.toRadians(-90)))
                        // スライダーを伸ばして、下から設置する
                        .addTemporalMarker(() -> {
                        })
                        // 1秒待つ (スライダーが伸びきるまで)
                        .waitSeconds(1.0)
                        // 標本を離す
                        .addTemporalMarker(() -> {
                        })
                        // 0.5秒待つ
                        .waitSeconds(0.5)
                        // スライダーを元に戻す
                        .addTemporalMarker(() -> {
                        })
                        // 元に戻すまで待つ
                        .waitSeconds(0.8)
//                        // 横に移動する
//                        .lineToLinearHeading(new Pose2d(35.0, -45.0, Math.toRadians(-90)))
//                        // 前に移動する
//                        .lineToLinearHeading(new Pose2d(35.0, -30.0, Math.toRadians(90)))
//                        // 横に行く
//                        .lineToLinearHeading(new Pose2d(50.0, -30.0, Math.toRadians(90)))
//                        .addTemporalMarker(() -> {
//                        })
//                        // 後ろに移動する (サンプルをヒューマンエリアに)
//                        .lineToLinearHeading(new Pose2d(50.0, -55.0, Math.toRadians(90)))
//                        // 標本の前に行く
//                        .lineToLinearHeading(new Pose2d(37.0, -50.0, Math.toRadians(90)))
//                        // 標本をつかむ準備をする
//                        .waitSeconds(0.5)
//                        // 速度を制限する
//                        .setAccelConstraint(new ProfileAccelerationConstraint(20.0))
//                        // 標本に近づく
//                        .lineToLinearHeading(new Pose2d(37.0, -58.0, Math.toRadians(90)))
//                        // 標本をつかむ
//                        .addTemporalMarker(() -> {
//                        })
//                        // つかみおわるまで待つ
//                        .waitSeconds(0.5)
//                        // アウトテイクを少し上に上げる
//                        .addTemporalMarker(() -> {
//                        })
//                        // 上がるまで待つ
//                        .waitSeconds(0.5)
//                        // 少し上げるのを解除
//                        .addTemporalMarker(() -> {
//                        })
//                        // 後ろに下がる
//                        .lineToLinearHeading(new Pose2d(40.0, -50.0, Math.toRadians(90)))
//                        // 速度制限解除
//                        .resetAccelConstraint()
//                        // 引っかける位置に移動する
//                        .lineToLinearHeading(new Pose2d(-3.0, -25.0, Math.toRadians(-90)))
//                        // スライダーを伸ばし、フックに下から標本を引っかける
//                        .addTemporalMarker(() -> {
//                        })
//                        // スライダーが伸びきるまで待つ
//                        .waitSeconds(1.0)
//                        // 標本を離す
//                        .addTemporalMarker(() -> {
//                        })
//                        // 離し終わるのを待つ
//                        .waitSeconds(0.5)
//                        // スライダーを縮める
//                        .addTemporalMarker(() -> {
//                        })
//                        .waitSeconds(1.0)
//                        // 横に移動する
//                        .lineToLinearHeading(new Pose2d(35.0, -45.0, Math.toRadians(-90)))
//                        // 前に移動する
//                        .lineToLinearHeading(new Pose2d(45.0, -10.0, Math.toRadians(-90)))
//                        // 横に行く
//                        .lineToLinearHeading(new Pose2d(70.0, -10.0, Math.toRadians(-90)))
//                        .addTemporalMarker(() -> {
//                        })
//                        // 後ろに移動する (サンプルをヒューマンエリアに)
//                        .lineToLinearHeading(new Pose2d(70.0, -65.0, Math.toRadians(-90)))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}