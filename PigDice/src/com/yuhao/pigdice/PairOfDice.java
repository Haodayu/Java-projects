package com.yuhao.pigdice;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class PairOfDice {
    Group rootDV;
    private Dice dL;
    private Dice dR;
    private int score;
    int counter;
    public PairOfDice(int TorB,int x,int y) {
        this.rootDV = new Group();
        this.rootDV.setTranslateX(x);
        this.rootDV.setTranslateY(y);
        this.dL = new Dice(-1,TorB);
        this.dR = new Dice(1,TorB);
        this.score = 0;
        this.counter = 0;
    }
    public void createDices() {
        dL.createDice();
        dR.createDice();
        rootDV.getChildren().addAll(dL.rootDV,dR.rootDV);
    }
    public void resetDices() {
        dL.resetDice();
        dR.resetDice();
    }
    public int rollDices() {
        int signal;
        dL.getRandAll();
        dR.getRandAll();
        dL.startRoll(dR.randTime);
        dR.startRoll(dL.randTime);
        if (dL.getNum()==1 && dR.getNum()==1) {
            score = 0;
            signal = 1;
        }
        else if (dL.getNum()!=1 && dR.getNum()!=1) {
            score += dL.getNum()+dR.getNum();
            signal = -1;
        }
        else { signal = 0;}
        return signal;
    }
    public void autoRollDs() {
        counter = 0;
        int tempNumL, tempNumR, tempSum=0;
        List<Integer> listL = new ArrayList<Integer>();
        List<Integer> listR = new ArrayList<Integer>();
        while (score<100) {
            tempNumL = (int) (Math.random()*(7-1)+1);
            tempNumR = (int) (Math.random()*(7-1)+1);
            counter++;
            listL.add(tempNumL);
            listR.add(tempNumR);
            if (tempNumL==1 && tempNumR==1) {
                score = 0;
                break;
            }
            if (tempNumL==1 || tempNumR==1) {
                break;
            }
            tempSum += tempNumL+tempNumR;
            score += tempNumL+tempNumR;
            if (tempSum >= 20) {
                break;
            }
        }
        System.out.println(listL);
        System.out.println(listR);

        dL.autoRoll(listL,counter);
        dR.autoRoll(listR,counter);
    }
    public int getScore() {
        return this.score;
    }
    public void setZero() {
        this.score = 0;
    }
}
