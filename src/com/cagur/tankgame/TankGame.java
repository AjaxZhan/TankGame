package com.cagur.tankgame;

import javax.swing.*;

/**
 * @author Cagur
 * @version 1.0
 */
public class TankGame extends JFrame {
    //定义一个画板
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame hspTankGame01 = new TankGame();

    }
    public TankGame(){
        mp = new MyPanel();
        this.add(mp); // 面板
        this.setSize(1000,750);
        this.addKeyListener(mp);//增加监听器
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
