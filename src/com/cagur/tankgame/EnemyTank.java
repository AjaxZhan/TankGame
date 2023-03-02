package com.cagur.tankgame;

import java.util.Vector;

/**
 * @author Cagur
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable{
    //新建一个坦克的集合，方便判断tank不能重叠用
    Vector<EnemyTank> enemyTanks  = new Vector<>();
    //在敌人坦克类创建子弹的vector
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
        this.setSpeed(3);
    }

    //提供一个方法，可以将MyPanel中的Vector放过来

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法判断是否接触
    //代码量比较大
    //这里首先判断的是我方坦克的方向，据此还能按照敌方的方向分成两类。而且还要对左上角和右上角两个点进行判断。
    //所以总共是 8 *2 = 16个判断
    @SuppressWarnings({"all"})
    public boolean isTouchEnemy(){
        switch (getDirect()){
            case 0: //八种情况第一组，敌人坦克向上
                //从Vector中取出一个敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank!=this){  //不能 和自己比较

                        //如果敌人坦克是上下方向
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){

                            //左上角进入区域
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() +40
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }

                            //右上角进入区域
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX() +40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //敌人坦克是左右
                        else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){

                            //左上角进入区域
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() +60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }

                            //右上角进入区域
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX() +60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }

                        }
                    }
                }
                break;
            case 1: // 右边
                //从Vector中取出一个敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不能 和自己比较
                    if(enemyTank!=this){
                        //如果敌人坦克是上下方向，范围不变
                        //分析：1. 确定敌人坦克的坐标范围 x范围是[敌人x，敌人x+40] y范围: [敌人y,敌人y+60]
                        //     2. 当前坦克的点发生变化：x + 60 , y 点  和   x+60  y+40 点
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){

                            //我方坦克的右上角进入区域
                            if(this.getX() + 60 >= enemyTank.getX() && this.getX() + 60  <= enemyTank.getX() +40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }

                            //我方坦克的右下角进入区域
                            if(this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX() +40
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY()+ 40 <= enemyTank.getY()+60){
                                return true;
                            }
                        }//敌人坦克是左右
                        //分析：1. 确定敌人坦克的坐标范围 x范围是[敌人x，敌人x+60] y范围: [敌人y,敌人y+40]
                        //     2. 当前坦克的x.y 点 和  x + 40 ,y 点
                        else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){

                            //我方坦克的左上角进入区域
                            if(this.getX() +60>= enemyTank.getX() && this.getX() +60<= enemyTank.getX() +60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }

                            //我方坦克的右上角进入区域
                            if(this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX() +60
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY()+40){
                                return true;
                            }

                        }
                    }
                }
                break;
            case 2: // 下面
                //从Vector中取出一个敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不能 和自己比较
                    if(enemyTank!=this){
                        //如果敌人坦克是上下方向，范围不变
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){

                            //我方坦克的右上角进入区域
                            if(this.getX() >= enemyTank.getX() && this.getX()  <= enemyTank.getX() +40
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY()+60){
                                return true;
                            }

                            //我方坦克的右下角进入区域
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX() +40
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY()+ 60 <= enemyTank.getY()+60){
                                return true;
                            }
                        }//敌人坦克是左右
                        //分析：1. 确定敌人坦克的坐标范围 x范围是[敌人x，敌人x+60] y范围: [敌人y,敌人y+40]
                        //     2. 当前坦克的x.y 点 和  x + 40 ,y 点
                        else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){

                            //我方坦克的左上角进入区域
                            if(this.getX()>= enemyTank.getX() && this.getX()<= enemyTank.getX() +60
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY()+40){
                                return true;
                            }

                            //我方坦克的右上角进入区域
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX() +60
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY()+40){
                                return true;
                            }

                        }
                    }
                }
                break;
            case 3: // 左边
                //从Vector中取出一个敌人坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不能 和自己比较
                    if(enemyTank!=this){

                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){

                            //我方坦克的右上角进入区域
                            if(this.getX() >= enemyTank.getX() && this.getX()  <= enemyTank.getX() +40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }

                            //我方坦克的右下角进入区域
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() +40
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY()+ 40 <= enemyTank.getY()+60){
                                return true;
                            }
                        }//敌人坦克是左右

                        else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){

                            //我方坦克的左上角进入区域
                            if(this.getX()>= enemyTank.getX() && this.getX()<= enemyTank.getX() +60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }

                            //我方坦克的左下角进入区域
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() +60
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY()+40){
                                return true;
                            }

                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while(true){

            //这里判断shot size是否为0，如果为0就创建一颗子弹

            if(isLive && shots.size() == 0){
                Shot s = null;
                //判断坦克方向，创建对应子弹
                switch (getDirect()){
                    case 0:
                        s = new Shot(getX() + 20,getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60,getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20,getY() + 60,2);
                        break;
                    case 3:
                        s = new Shot(getX() , getY() + 20,3);
                        break;
                }

                //加入到集合
                shots.add(s);

                //启动子弹线程
                new Thread(s).start();
            }

            //根据坦克的方向来继续移动，再随机改变方向
            switch (getDirect()){
                case 0: // up
                    //多动一下，不能只动一步
                    for(int i=0;i<30;i++) {
                        if(getY() >0 && !isTouchEnemy())
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
                        if(getX() + 60 < 1000 && !isTouchEnemy())
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
                        if(getY() +60 < 750 && !isTouchEnemy())
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
                        if(getX() >0 && !isTouchEnemy())
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
