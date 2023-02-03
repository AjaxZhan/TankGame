package com.cagur.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author Cagur
 * @version 1.0
 */
@SuppressWarnings({"all"})
//为了让Panel不停重新绘制子弹，需要将MyPanel做成线程。秒啊。不让子弹就绘制了一次。除非你按下键


public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克，放入到vector集合中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 3;
    public MyPanel(){
        hero = new Hero(100,100);//初始化自己的坦克
        hero.setSpeed(4);//设置速度
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            enemyTank.setDirect(2);
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认是黑色
        //画出坦克
        drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),0);
        }
        //画出hero射击的子弹
        if(hero.shot!= null && hero.shot.isLive == true){
            System.out.println("子弹被绘制");
            //draw bullet
            g.fill3DRect(hero.shot.x,hero.shot.y,1,1,false);
        }

    }
    //画出坦克的封装方法

    /**
     *
     * @param x 坦克左上角x坐标
     * @param y 坦克左上角y坐标
     * @param g 画笔
     * @param direct 坦克方向，上下左右
     * @param type 坦克类型（我方tank 或者 敌方tank）
     */
    public void drawTank(int x, int y,Graphics g, int direct, int type){
        //设置颜色
        switch (type){
            case 0:
                //敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1:
                //我们的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克的方向绘制对应形状的坦克
        // 0 上 1 右 2 下 3 左
        switch (direct){
            case 0:
                //向上
                g.fill3DRect(x,y,10,60,false);//坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间矩形
                g.fillOval(x+10,y+20,20,20);//圆形，注意传入的不是半径，是外切矩形
                g.drawLine(x+20,y+30,x+20,y);
                //
                break;
            case 1:
                //向右
                g.fill3DRect(x,y,60,10,false);//坦克左边轮子
                g.fill3DRect(x,y+30,60,10,false);//右边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//中间矩形
                g.fillOval(x+20,y+10,20,20);//圆形，注意传入的不是半径，是外切矩形
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2:
                //向下
                g.fill3DRect(x,y,10,60,false);//坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间矩形
                g.fillOval(x+10,y+20,20,20);//圆形，注意传入的不是半径，是外切矩形
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3:
                //向左
                g.fill3DRect(x,y,60,10,false);//坦克左边轮子
                g.fill3DRect(x,y+30,60,10,false);//右边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//中间矩形
                g.fillOval(x+20,y+10,20,20);//圆形，注意传入的不是半径，是外切矩形
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("其它情况暂时没有处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            //改变坦克的方向
            hero.setDirect(0);
            //改变坦克坐标
            hero.moveUp();
        }else if(e.getKeyCode() ==KeyEvent.VK_D){
            hero.setDirect(1);
            hero.moveRight();
        }else if(e.getKeyCode() ==KeyEvent.VK_S){
            hero.setDirect(2);
            hero.moveDown();
        }else if(e.getKeyCode() ==KeyEvent.VK_A){
            hero.setDirect(3);
            hero.moveLeft();
        }
        //如果用户按下J，就发射
        if(e.getKeyCode() == KeyEvent.VK_J){
            System.out.println("用户按下了J，开始射击");
            hero.shotEnemy();
        }

        //重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //将mypanel制作成线程

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.repaint();
        }
    }
}
