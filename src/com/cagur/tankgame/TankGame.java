package com.cagur.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author Cagur
 * @version 1.0
 */
public class TankGame extends JFrame {
    //定义一个画板
    MyPanel mp = null;
    static Scanner scanner  = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame hspTankGame01 = new TankGame();
    }
    public TankGame(){
        System.out.println("请输入选择：1为新游戏，2为继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp放入到Thread并启动线程，
        Thread thread= new Thread(mp);
        thread.start();

        this.add(mp); // 面板
        this.setSize(1300,750);
        this.addKeyListener(mp);//增加监听器
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //JFrame中，增加监听关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                System.out.println("监听到关闭窗口");
                Recorder.storeRecord();
                System.exit(0);
            }
        });
    }
}
