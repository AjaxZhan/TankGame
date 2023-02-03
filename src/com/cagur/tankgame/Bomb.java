package com.cagur.tankgame;

/**
 * @author Cagur
 * @version 1.0
 * 炸弹
 */
public class Bomb {
    int x,y;
    int life = 9; // 生命周期
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //减少生命值
    public void lifeDown(){ // 配置出现图片的爆炸效果
        if(life > 0 ) life--;
        else isLive = false;
    }
}
