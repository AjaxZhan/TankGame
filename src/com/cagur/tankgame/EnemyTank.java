package com.cagur.tankgame;

import java.util.Vector;

/**
 * @author Cagur
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable{
    //在敌人坦克类创建子弹的vector
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
        this.setSpeed(3);
    }

    @Override
    public void run() {
        while(true){
            //根据坦克的方向来继续移动，再随机改变方向
            switch (getDirect()){
                case 0: // up
                    //多动一下，不能只动一步
                    for(int i=0;i<30;i++) {
                        moveUp();
                        try {
                            //直接将休眠放进去即可
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1: // right
                    for(int i=0;i<30;i++) {
                        moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2: // down
                    for(int i=0;i<30;i++) {
                        moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3: // left
                    for(int i=0;i<30;i++) {
                        moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }


            //随机方向 0 -3
            setDirect((int)(Math.random() * 4));

            //写并发程序，一定要考虑清楚什么时候退出线程
            if(!isLive) break;

        }
    }
}
