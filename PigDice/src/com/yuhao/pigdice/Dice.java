package com.yuhao.pigdice;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;

public class Dice{
    private ImageView mainDV;
    private ImageView hideDV;
    Group rootDV;
    private int LorR;
    private int TorB;
    int randTime;
    private int randNum;
    private int randTX;
    private int randTY;
    private double randR;
    private DropShadow ds01;
    public Dice (int LorR, int TorB) {
        //LT=-1,BR=1
        //juesguy
        this.LorR = LorR;
        this.TorB = TorB;
        this.rootDV = new Group();
        this.ds01 =new DropShadow();
        ds01.setRadius(54.0);
        ds01.setOffsetX(0.0);
        ds01.setOffsetY(4.0);
        ds01.setColor(Color.color(0,0,0,0.38));
        ds01.setSpread(0.0);
    }
    public int getNum() {
        return this.randNum;
    }
    public void createDice() {
        String mainUrl;
        int xPos;
        if (LorR == 1) {
            xPos = 130;
            if (TorB == 1) {
                mainUrl = "images/bottomRight.png";
            }
            else {
                mainUrl = "images/topRight.png";
            }
        }
        else {
            xPos = 0;
            if (TorB == 1) {
                mainUrl = "images/bottomLeft.png";
            } else {
                mainUrl = "images/topLeft.png";
            }
        }

        Image mainD = new Image(mainUrl,280, 280, false, false);
        mainDV = new ImageView();
        mainDV.setImage(mainD);
        mainDV.setX(xPos);
        mainDV.setEffect(ds01);

        hideDV = new ImageView();
        hideDV.setFitHeight(280);
        hideDV.setFitWidth(280);
        hideDV.setX(xPos);
        hideDV.setEffect(ds01);

        rootDV.getChildren().addAll(hideDV,mainDV);
    }
    public void resetDice() {
        rootDV.setScaleX(1);
        rootDV.setScaleY(1);
        rootDV.setRotate(0);
        rootDV.setTranslateX(0);
        rootDV.setTranslateY(0);
        rootDV.setOpacity(1);
        mainDV.setOpacity(1);
        hideDV.setImage(new Image("images/blank.png"));
    }
    public void getRandAll() {
        this.randTX = (int) (Math.random()*(200));
        this.randTY = (int) (-1*(Math.random()*(380-220)+220));
        this.randNum = (int) (Math.random()*(7-1)+1);
        this.randTime = (int) (Math.random()*(4000-1700)+1700);
        this.randR = 360f*(8+Math.random());
    }
    public void startRoll(int anotherTime) {
        String hideDUrl = "images/r"+randNum+".png";
        Image hideD = new Image(hideDUrl);
        hideDV.setImage(hideD);

        int lastTime = Math.max(anotherTime,randTime);
        double lastR = 90f*Math.round(randR/90f);

        Timeline timelineM = new Timeline();
        timelineM.setCycleCount(1);
        timelineM.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(rootDV.scaleXProperty(), 1),
                        new KeyValue(rootDV.scaleYProperty(), 1),
                        new KeyValue(rootDV.translateXProperty(), 0),
                        new KeyValue(rootDV.translateYProperty(), 0),
                        new KeyValue(rootDV.rotateProperty(), 0),
                        new KeyValue(mainDV.opacityProperty(),1)),
                new KeyFrame(new Duration(500),
                        new KeyValue(rootDV.scaleXProperty(), 0.7),
                        new KeyValue(rootDV.scaleYProperty(), 0.7)),
                new KeyFrame(new Duration(randTime*0.55),
                        new KeyValue(rootDV.rotateProperty(), randR-180f),
                        new KeyValue(mainDV.opacityProperty(),1)),
                new KeyFrame(new Duration(randTime*0.75),
                        new KeyValue(rootDV.translateXProperty(), LorR*randTX),
                        new KeyValue(rootDV.translateYProperty(), randTY*TorB),
                        new KeyValue(rootDV.rotateProperty(), randR),
                        new KeyValue(mainDV.opacityProperty(),0)),
                new KeyFrame(new Duration(lastTime*0.9),
                        new KeyValue(rootDV.translateXProperty(), LorR*randTX),
                        new KeyValue(rootDV.translateYProperty(), randTY*TorB),
                        new KeyValue(rootDV.rotateProperty(), randR),
                        new KeyValue(rootDV.scaleXProperty(), 0.7),
                        new KeyValue(rootDV.scaleYProperty(), 0.7)),
                new KeyFrame(new Duration(lastTime),
                        new KeyValue(rootDV.translateXProperty(), LorR*20),
                        new KeyValue(rootDV.translateYProperty(), -450*TorB),
                        new KeyValue(rootDV.rotateProperty(), lastR),
                        new KeyValue(rootDV.scaleXProperty(), 1.1),
                        new KeyValue(rootDV.scaleYProperty(), 1.1)));
        timelineM.play();
    }
    public void autoRoll(List<Integer> list, int counter) {

        String hideDUrl = "images/w"+list.get(0)+".png";
        Image hideD = new Image(hideDUrl);
        hideDV.setImage(hideD);
        getRandAll();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(counter);
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            private int i = 1;
            public void handle(ActionEvent t) {
                resetDice();
                if (i < counter) {
                    String hideDUrl = "images/w"+list.get(i)+".png";
                    Image hideD = new Image(hideDUrl);
                    hideDV.setImage(hideD);
                }
                i++;
            }
        };

        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(rootDV.scaleXProperty(), 1),
                        new KeyValue(rootDV.scaleYProperty(), 1),
                        new KeyValue(rootDV.translateXProperty(), 0),
                        new KeyValue(rootDV.translateYProperty(), 0),
                        new KeyValue(rootDV.rotateProperty(), 0),
                        new KeyValue(mainDV.opacityProperty(),1)),
                new KeyFrame(new Duration(500),
                        new KeyValue(rootDV.scaleXProperty(), 0.7),
                        new KeyValue(rootDV.scaleYProperty(), 0.7)),
                new KeyFrame(new Duration(randTime*0.55),
                        new KeyValue(rootDV.rotateProperty(), randR-180f),
                        new KeyValue(mainDV.opacityProperty(),1)),
                new KeyFrame(new Duration(randTime*0.8),
                        new KeyValue(rootDV.translateXProperty(), LorR*randTX),
                        new KeyValue(rootDV.translateYProperty(), randTY*TorB),
                        new KeyValue(rootDV.rotateProperty(), randR),
                        new KeyValue(mainDV.opacityProperty(),0),
                        new KeyValue(rootDV.opacityProperty(),1)),
                new KeyFrame(new Duration(4000),onFinished,
                        new KeyValue(rootDV.opacityProperty(),0)));
        timeline.play();
    }
}
