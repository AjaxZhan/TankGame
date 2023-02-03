package com.cagur.tankgame;

/**
 * @author Cagur
 * @version 1.0
 * 射击子弹
 */
public class Shot implements Runnable{
    int x;
    int y;
    int direct = 0; // direction
    int speed = 2;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() { // shot
        while(true){
            //线程休眠，防止子弹太快
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向改变x,y坐标
            switch (direct){
                case 0: // up
                    y-=speed;
                    break;
                case 1: // right
                    x+=speed;
                    break;
                case 2: // down
                    y+=speed;
                    break;
                case 3: // left
                    x-=speed;
                    break;
            }
            //输出x和y的坐标

        }
    }
}
