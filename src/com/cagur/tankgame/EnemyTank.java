package com.cagur.tankgame;

import java.util.Vector;

/**
 * @author Cagur
 * @version 1.0
 */
public class EnemyTank extends Tank{
    //在敌人坦克类创建子弹的vector
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
        this.setSpeed(3);
    }

}
