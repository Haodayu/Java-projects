package com.yuhao.pigdice;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private PairOfDice myDices;
    private PairOfDice rbDices;
    private Text myScore;
    private Text rbScore;
    private ImageView rollBt;
    private ImageView disBt;
    private ImageView resBt;
    private ImageView win;
    private ImageView winBG;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        //阴影
        DropShadow ds01 =new DropShadow();
        ds01.setRadius(54.0);
        ds01.setOffsetX(0.0);
        ds01.setOffsetY(4.0);
        ds01.setColor(Color.color(0,0,0,0.38));
        ds01.setSpread(0.0);
        //两组骰子
        myDices = new PairOfDice(1,142,720);
        myDices.createDices();
        rbDices = new PairOfDice(-1,142,-20);
        rbDices.createDices();
        //背景图片
        ImageView mainBG = new ImageView(new Image("images/mainBg.png"));
        ImageView topBG = new ImageView(new Image("images/topBg.png"));
        topBG.setEffect(ds01);

        //图片按钮
        rollBt = new ImageView("images/rollBt.png");
        rollBt.setPickOnBounds(true);
        rollBt.setOnMouseClicked(this::rollBtPress);
        rollBt.setLayoutX(152);
        rollBt.setLayoutY(520);

        disBt = new ImageView("images/disBt.png");
        disBt.setPickOnBounds(true);
        disBt.setOnMouseClicked(this::disBtPress);
        disBt.setLayoutX(152+188+18);
        disBt.setLayoutY(520);

        resBt = new ImageView("images/resBt.png");
        resBt.setPickOnBounds(true);
        resBt.setOnMouseClicked(this::resBtPress);
        resBt.setLayoutX(580);
        resBt.setLayoutY(780);
        resBt.setScaleX(0.6);
        resBt.setScaleY(0.6);

        //信息文本
        myScore = new Text();
        myScore.setText("My Score: "+myDices.getScore());
        myScore.setFont(Font.font ("Fugaz One",48));
        myScore.setFill(Color.WHITE);
        myScore.setX(20); myScore.setY(70);
        rbScore = new Text();
        rbScore.setText("Rb Score: "+rbDices.getScore());
        rbScore.setFont(Font.font ("Fugaz One",48));
        rbScore.setFill(Color.WHITE);
        rbScore.setX(380); rbScore.setY(70);
        Group textG = new Group(myScore,rbScore);

        //结束场景
        win = new ImageView("images/win.png");
        win.setX(150);win.setY(300);
        win.setVisible(false);
        winBG = new ImageView("images/winBG.png");
        winBG.setVisible(false);

        root.getChildren().addAll(mainBG,myDices.rootDV,rbDices.rootDV,topBG,rollBt,disBt,resBt,winBG,win,textG);

        primaryStage.setTitle("Pig Dice");
        Scene scene = new Scene(root, 700, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void rollBtPress(MouseEvent event) {
        int signal;
        signal = myDices.rollDices();
        if (disBt.getScaleX()!=0) {
            outButton(disBt);
        }
        outButton(rollBt);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                myScore.setText("My Score: "+myDices.getScore());
                checkWinner();
                checkButton(signal);
            }
        }, 3000);
    }
    private void disBtPress(MouseEvent event) {
        myDices.resetDices();
        rbDices.autoRollDs();

        if (rollBt.getScaleX()!=0) {
            outButton(rollBt);
        }
        outButton(disBt);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                rbScore.setText("Rb Score: "+rbDices.getScore());
                checkWinner();
                inButton(rollBt,103);
            }
        }, 4000*rbDices.counter);
    }
    private void resBtPress(MouseEvent event) {
        reStart();
    }
    private void reStart() {
        myDices.resetDices();
        rbDices.resetDices();
        myDices.setZero();
        rbDices.setZero();
        roRes(resBt);
        if (rollBt.getScaleX()==0 || rollBt.getTranslateX()>0) {
            inButton(rollBt,0);
        }
        if (disBt.getScaleX()==0 || disBt.getTranslateX()>0) {
            inButton(disBt,0);
        }
        myScore.setText("My Score: "+myDices.getScore());
        rbScore.setText("Rb Score: "+rbDices.getScore());
    }
    private void checkWinner() {
        if (rbDices.getScore()>=100) {
            win.setImage(new Image("images/Sorry.png"));
            win.setX(90);win.setY(400);
            playEnd();
        }
        else if (myDices.getScore()>=100) {
            win.setImage(new Image("images/win.png"));
            win.setX(150);win.setY(400);
            playEnd();
        }
    }
    private void playEnd() {
        win.setVisible(true);
        winBG.setVisible(true);
        Timeline timelineM = new Timeline();
        timelineM.setCycleCount(1);
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                win.setVisible(false);
                winBG.setVisible(false);
                reStart();
            }
        };
        timelineM.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(win.scaleXProperty(), 0),
                        new KeyValue(win.scaleYProperty(),0),
                        new KeyValue(winBG.opacityProperty(),0)),
                new KeyFrame(new Duration(800),
                        new KeyValue(win.scaleXProperty(), 1.3),
                        new KeyValue(win.scaleYProperty(),1.3),
                        new KeyValue(winBG.opacityProperty(),1)),
                new KeyFrame(new Duration(1000),
                        new KeyValue(win.scaleXProperty(), 1),
                        new KeyValue(win.scaleYProperty(),1)),
                new KeyFrame(new Duration(4500),
                        new KeyValue(win.scaleXProperty(), 1),
                        new KeyValue(win.scaleYProperty(),1),
                        new KeyValue(win.opacityProperty(),1),
                        new KeyValue(winBG.opacityProperty(),1)),
                new KeyFrame(new Duration(4500),onFinished,
                        new KeyValue(win.opacityProperty(), 0),
                        new KeyValue(winBG.opacityProperty(),0)));
        timelineM.play();
    }
    private void checkButton(int signal) {
        switch (signal) {
            case 1:
                inButton(disBt,-103);
                break;
            case 0:
                inButton(disBt,-103);
                break;
            case -1:
                inButton(rollBt,0);
                inButton(disBt,0);
                break;
        }
    }
    private void inButton(ImageView button,int X) {
        Timeline timelineM = new Timeline();
        timelineM.setCycleCount(1);
        timelineM.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(button.scaleXProperty(), 0),
                        new KeyValue(button.scaleYProperty(), 0),
                        new KeyValue(button.translateXProperty(), 0)),
                new KeyFrame(new Duration(400),
                        new KeyValue(button.scaleXProperty(), 1.1),
                        new KeyValue(button.scaleYProperty(), 1.1),
                        new KeyValue(button.translateXProperty(), X)),
                new KeyFrame(new Duration(600),
                        new KeyValue(button.scaleXProperty(), 1),
                        new KeyValue(button.scaleYProperty(), 1)));
        timelineM.play();
    }
    private void outButton(ImageView button) {
        Timeline timelineM = new Timeline();
        timelineM.setCycleCount(1);
        timelineM.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(button.scaleXProperty(), 1),
                        new KeyValue(button.scaleYProperty(), 1)),
                new KeyFrame(new Duration(600),
                        new KeyValue(button.scaleXProperty(), 0),
                        new KeyValue(button.scaleYProperty(), 0)));
        timelineM.play();
    }
    private void roRes(ImageView button) {
        Timeline timelineM = new Timeline();
        timelineM.setCycleCount(1);
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                button.setRotate(0);
            }
        };
        timelineM.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(button.rotateProperty(), 0)),
                new KeyFrame(new Duration(600),onFinished,
                        new KeyValue(button.rotateProperty(), 360f)));
        timelineM.play();
    }
}
