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
    //定义集合存放炸弹
    Vector<Bomb> bombs = new Vector<>();
    //定义三张炸弹图片，显示爆炸效果
    //说明：当子弹击中坦克时，就加入一个Bomb对象到Bombs中
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    //默认坦克数量
    int enemyTankSize = 3;
    public MyPanel(){
        hero = new Hero(100,100);//初始化自己的坦克
        hero.setSpeed(4);//设置速度
        //初始化敌人坦克
        for (int i = 0; i < enemyTankSize; i++) {
            //初始化坦克
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            enemyTank.setDirect(2);
            //启动敌人坦克线程，使之移动
            new Thread(enemyTank).start();
            //给Enemy添加一个子弹
            Shot shot = new Shot(enemyTank.getX() + 20,enemyTank.getY()+60,enemyTank.getDirect());
            //加入到enemyTank的vector内
            enemyTank.shots.add(shot);
            //启动子弹线程
            new Thread(shot).start();
            //加入坦克
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认是黑色
        //画出坦克
        if(hero !=null && hero.isLive)
            drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
        //网友建议：在这里加个休眠，解决第一个坦克无法出现Bomb效果的问题
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //如果bombs集合中有对象，就画出炸弹
        for(int i=0;i<bombs.size();i++){
            Bomb bomb = bombs.get(i);
            //根据当前bomb对象的Live值画出对应的图片
            if(bomb.life > 6) g.drawImage(image1,bomb.x, bomb.y, 60,60,this);
            else if(bomb.life > 3) g.drawImage(image2,bomb.x, bomb.y, 60,60,this);
            else g.drawImage(image3,bomb.x, bomb.y, 60,60,this);
            //生命值递减
            bomb.lifeDown();
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }

        //画出敌人的坦克，遍历vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断坦克是否存活，存活再画
            if(enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画坦克的同时，画出敌人坦克子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制子弹
                    if (shot.isLive == true) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从vector从移除,除了绘制外的另一个任务
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        //画出hero射击的子弹
//        if(hero.shot!= null && hero.shot.isLive == true){ //先判断是否为空，防止空指针异常
//            System.out.println("子弹被绘制");
//            //draw bullet
//            g.fill3DRect(hero.shot.x,hero.shot.y,1,1,false);
//        }
        //遍历hero子弹的容器
        for(int i =0;i<hero.shots.size();i++){
            Shot shot = hero.shots.get(i);
            if(shot!= null && shot.isLive == true) { //先判断是否为空，防止空指针异常
                System.out.println("子弹被绘制");
                g.fill3DRect(shot.x, shot.y, 1, 1, false);
            }else{
                //该对象无效，则从集合中取出。
                hero.shots.remove(shot);
            }
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

    //判断敌人坦克是否击中我方坦克
    public void hitHero(){
        //遍历所有敌人坦克，遍历所有敌人子弹，判断位置关系
        for(int i=0;i< enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            for(int j=0;j<enemyTank.shots.size();j++){
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断子弹和我方tank的位置关系
                if(hero.isLive && shot.isLive){
                    //写到这里，就要修改一下HitTank的方法，实现继承。
                    hitTank(shot,hero);
                }
            }
        }
    }


    //编写方法，判断我方子弹是否击中敌方坦克
    //什么时候判断我方子弹是否击中敌人坦克？ run方法
    //如果我方坦克有多颗子弹，在判断是否击中就需要将集合中的子弹全部取出进行判断
    public void hitEnemyTank(){
        //遍历所有子弹
        Shot shot = null;
        for(int j = 0;j<hero.shots.size();j++){
            shot = hero.shots.get(j);
            if(shot!= null && shot.isLive){ // 这里记得添加为空，否则会抛出空指针异常
                //遍历敌人所有坦克
                for(int i=0;i<enemyTankSize;i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot,enemyTank);
                }
            }
        }
    }
    public void hitTank(Shot s, Tank tank){
        //判断s 击中坦克 == 》 子弹的坐标是否在坦克的区域内
        switch (tank.getDirect()){
            case 0:
            case 2:
                //坦克向上下
                if(s.x > tank.getX() && s.x<tank.getX()+40 && s.y > tank.getY() &&
                s.y<tank.getY()+60){
                    s.isLive = false;
                    tank.isLive = false;
                    //当我的子弹击中敌人坦克后，将enemyTank从集合中删掉
                    if(tank instanceof EnemyTank){
                        EnemyTank enemytank = (EnemyTank)tank;
                        enemyTanks.remove(enemytank);//直接删掉好像有问题
                        enemyTankSize--; //忘记加上这句了
                    }
                    //创建Bomb对象，加入到Bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                //坦克向右边和向左边
                if(s.x>tank.getX() && s.x < tank.getX()+60 && s.y>tank.getY() &&
                s.y < tank.getY() + 40){
                    s.isLive = false;
                    tank.isLive = false;
                    if(tank instanceof EnemyTank) {
                        EnemyTank enemytank = (EnemyTank)tank;
                        //当我的子弹击中敌人坦克后，将enemyTank从集合中删掉
                        enemyTanks.remove(enemytank);//直接删掉好像有问题
                        enemyTankSize--; //忘记加上这句了
                    }
                    //创建Bomb对象，加入到Bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && hero.isLive){
            //改变坦克的方向
            hero.setDirect(0);
            //改变坦克坐标
            if(hero.getY() > 0)
                hero.moveUp();
        }else if(e.getKeyCode() ==KeyEvent.VK_D && hero.isLive){
            hero.setDirect(1);
            if(hero.getX() + 60 < 1000)
                hero.moveRight();
        }else if(e.getKeyCode() ==KeyEvent.VK_S && hero.isLive){
            hero.setDirect(2);
            if(hero.getY() +60 <  750)
                hero.moveDown();
        }else if(e.getKeyCode() ==KeyEvent.VK_A && hero.isLive){
            hero.setDirect(3);
            if(hero.getX() > 0)
                hero.moveLeft();
        }
        //如果用户按下J，就发射
        if(e.getKeyCode() == KeyEvent.VK_J && hero.isLive){
//            System.out.println("用户按下了J，开始射击");
//            if(hero.shot == null || !hero.shot.isLive) // 当一颗子弹打完才能打
//                hero.shotEnemy();
            //发射多颗子弹
            hero.shotEnemy(); // 因为遍历放在了paint，这里不需要判断
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
            //原先：判断一颗子弹是否击中了敌方坦克
            //改进，将下面这段代码封装，在此基础上遍历集合。
            hitEnemyTank();
            //添加判断：子弹是否击中了敌人坦克
//            if(hero.shot!= null && hero.shot.isLive){ // 这里记得添加为空，否则会抛出空指针异常
//                //遍历敌人所有坦克
//                for(int i=0;i<enemyTankSize;i++){
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    hitTank(hero.shot,enemyTank);
//                }
//            }
            //判断我方坦克是否击中
            hitHero();
            this.repaint();
        }
    }
}
