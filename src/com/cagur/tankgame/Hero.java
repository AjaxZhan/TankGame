package com.cagur.tankgame;

import java.util.Vector;

/**
 * @author Cagur
 * @version 1.0
 * 自己的坦克
 */
public class Hero extends Tank {
    Shot shot = null; //表示一个射击行为
    //Vector存放多颗子弹
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemy(){
        //创建Shot对象，子弹位置与坦克当前位置有关
        if(shots.size() == 5) return; // 加一个创建限制
        switch (getDirect()){ //得到Hero对象方向
            case 0: // Up
                shot = new Shot(getX() + 20, getY(), 0);// 分析坐标
                break;
            case 1: //right
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2: //down
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,3);
                break;
        }
        //新创建的Shot放入集合
        shots.add(shot);
        //启动射击线程
        new Thread(shot).start();
    }
}
