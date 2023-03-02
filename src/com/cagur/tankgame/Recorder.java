package com.cagur.tankgame;

import java.io.*;
import java.util.Vector;

/**
 * @author Cagur
 * @version 1.0
 */
public class Recorder {
    //指向MyPanel的Vector
    private static Vector<EnemyTank> enemyTanks = null;
    //定义记录我方击毁敌人Tank数
    private static int allEnemyTanks = 0;
    //定义IO对象 ： 用于写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    //路径
    private static String recordFile = "e:\\myRecord.txt";
    //定义一个Node的Vector，用于保存敌人的Node信息
    private static Vector<Node> nodes = new Vector<>();

    //增加一个方法，用于读取文件，恢复相关信息
    public static Vector<Node> getNodesAndCount(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTanks = Integer.parseInt(br.readLine());
            //循环读取文件，生成Node集合
            String line = "";
            while((line = br.readLine())!=null){
                //分割一行数据
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node); //将读取到的Node放到集合
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return nodes;
    }

    //set
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTanks() {
        return allEnemyTanks;
    }

    public static void setAllEnemyTanks(int allEnemyTanks) {
        Recorder.allEnemyTanks = allEnemyTanks;
    }
    //当我方击毁一辆坦克应该自增
    public static void addRecord(){
        Recorder.allEnemyTanks ++;
    }

    //当游戏退出时，存盘到文件中
    public static void storeRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTanks + "");
            bw.newLine(); // 换行
            //遍历敌人坦克集合，根据情况保存
            //OOP角度：定义一个属性，通过set方法得到敌人Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出tank
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive){ // 建议判断
                    //保存该EnemyTank的信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //写入文件
                    bw.write(record);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            //close
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
