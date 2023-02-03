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
    boolean isLive = true;  //可以存活

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
            System.out.println("子弹x = " + x + "子弹y=" + y);
            //当子弹移动到面板边界就销毁
            //当子弹碰到敌人坦克时，也应该结束线程
            if(!(x>=0 && x<=1000 && y>=0 && y<=750 && isLive)){
                isLive = false;
                System.out.println("子弹线程退出");
                break; // 退出线程
            }

        }
    }
}
