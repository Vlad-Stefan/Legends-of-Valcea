package entities;

import Main.Game;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constans.EnemyC.*;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager{

    private Playing playing;
    private BufferedImage[][] dummy, minotaur, assassin, mage;
    private ArrayList<Dummy> dummies = new ArrayList<>();
    private ArrayList<Minotaur> minotaurs = new ArrayList<>();
    private ArrayList<Assassin> assassins = new ArrayList<>();
    private ArrayList<Mage> mages = new ArrayList<>();

    public EnemyManager(Playing playing){


        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        dummies = level.getDummyes();
        minotaurs = level.getMinotaurs();
        assassins = level.getAssassins();
        mages = level.getMages();


        System.out.println("size: " + dummies.size());
        System.out.println("size: " + minotaurs.size());
        System.out.println("size: " + assassins.size());
        System.out.println("size: " + mages.size());
    }

    public void update(int[][] lvlData, Player player){
        boolean isAnyA = false;
        for(Dummy d : dummies){
            if(d.isActive()) {
                d.update(lvlData, player);
                isAnyA = true;
            }
        }
        for(Minotaur m : minotaurs){
            if(m.isActive()) {
                m.update(lvlData, player);
                isAnyA = true;
            }
        }

        for(Assassin a : assassins){
            if(a.isActive()) {
                a.update(lvlData, player);
                isAnyA = true;
            }
        }

        for(Mage ma : mages){
            if(ma.isActive()) {
                ma.update(lvlData, player);
                isAnyA = true;
            }
        }

        if(!isAnyA)
            playing.setLvlC(true);
    }

    public void draw(Graphics g, int xLvlO){
        drawDummy(g, xLvlO);
        drawMinotaur(g, xLvlO);
        drawAssassin(g,xLvlO);
        drawMage(g,xLvlO);

    }

    private void drawMage(Graphics g, int xLvlO) {
        for(Mage ma : mages){
            if(ma.isActive()) {
                g.drawImage(mage[ma.getEnemState()][ma.getAniI()], (int)(ma.getHitbox().x - (14 * Game.SCALE)) - xLvlO + ma.flipX(), (int)(ma.getHitbox().y- Game.SCALE), MAGE_W * ma.flipW(), MAGE_H, null);
                /*g.setColor(Color.MAGENTA);
                g.drawRect((int)ma.getHitbox().x - xLvlO, (int)ma.getHitbox().y, (int)ma.getHitbox().width, (int)ma.getHitbox().height);
                ma.drawAttackBox(g,xLvlO);*/
            }
        }
    }

    private void drawAssassin(Graphics g, int xLvlO) {
        for(Assassin a : assassins){
            if(a.isActive()) {
                g.drawImage(assassin[a.getEnemState()][a.getAniI()], (int)(a.getHitbox().x - (14 * Game.SCALE)) - xLvlO + a.flipX(), (int)(a.getHitbox().y- Game.SCALE), ASSASSIN_W * a.flipW(), ASSASSH_H, null);
                /*g.setColor(Color.MAGENTA);
                g.drawRect((int)a.getHitbox().x - xLvlO, (int)a.getHitbox().y, (int)a.getHitbox().width, (int)a.getHitbox().height);
                a.drawAttackBox(g,xLvlO);*/
            }
        }
    }

    private void drawMinotaur(Graphics g, int xLvlO) {
        for(Minotaur m : minotaurs)
            if(m.isActive()){
                g.drawImage(minotaur[m.getEnemState()][m.getAniI()], (int)(m.getHitbox().x - (14 * Game.SCALE)) - xLvlO + m.flipX(), (int)(m.getHitbox().y- Game.SCALE), MINOTAUR_W * m.flipW(), MINOTAUR_H, null);
                /*g.setColor(Color.MAGENTA);
                g.drawRect((int)m.getHitbox().x - xLvlO, (int)m.getHitbox().y, (int)m.getHitbox().width, (int)m.getHitbox().height);
                m.drawAttackBox(g,xLvlO);*/
            }
    }

    private void drawDummy(Graphics g, int xLvlO) {
        for(Dummy d : dummies)
            if(d.isActive()){
            g.drawImage(dummy[d.getEnemState()][d.getAniI()], (int)(d.getHitbox().x - (14 * Game.SCALE)) - xLvlO + d.flipX(), (int)(d.getHitbox().y- Game.SCALE), DUMMY_W * d.flipW(), DUMMY_H, null);
            /*g.setColor(Color.MAGENTA);
            g.drawRect((int)d.getHitbox().x - xLvlO, (int)d.getHitbox().y, (int)d.getHitbox().width, (int)d.getHitbox().height);
            d.drawAttackBox(g,xLvlO);*/
        }

    }

    public void checkEnemH(Rectangle2D.Float attackBox){
        for(Dummy d : dummies)
            if(d.isActive()){
                if(d.getCurrentHealth() > 0)
                    if(attackBox.intersects(d.getHitbox())){
                        d.hurt(10);
                        return;
                    }
            }

        for(Minotaur m : minotaurs)
            if(m.isActive()){
                if(m.getCurrentHealth() > 0)
                    if(attackBox.intersects(m.getHitbox())){
                        m.hurt(10);
                        return;
                    }
            }

        for(Assassin a : assassins)
            if(a.isActive()){
                if(a.getCurrentHealth() > 0)
                    if(attackBox.intersects(a.getHitbox())){
                        a.hurt(10);
                        return;
                    }
            }

        for(Mage ma : mages)
            if(ma.isActive()){
                if(ma.getCurrentHealth() > 0)
                    if(attackBox.intersects(ma.getHitbox())){
                        ma.hurt(10);
                        return;
                    }
            }
    }

    private void loadEnemyImgs() {
        dummy = new BufferedImage[5][10];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.DS);
        for(int j = dummy.length - 1; j >= 0; j--){
            for(int i =  dummy[j].length - 1; i >= 0; i--){
                dummy[j][i] = temp.getSubimage(i * DUMMY_W_D, j * DUMMY_H_D, DUMMY_W_D, DUMMY_H_D);
            }
        }

        minotaur = new BufferedImage[5][10];
        temp = LoadSave.GetSpriteAtlas(LoadSave.MS);
        for(int j = minotaur.length - 1; j >= 0; j--){
            for(int i =  minotaur[j].length - 1; i >= 0; i--){
                minotaur[j][i] = temp.getSubimage(i * MINOTAUR_W_D, j * MINOTAUR_H_D, MINOTAUR_W_D, MINOTAUR_H_D);
            }
        }

        assassin = new BufferedImage[5][10];
        temp = LoadSave.GetSpriteAtlas(LoadSave.PA);
        for(int j = assassin.length - 1; j >= 0; j--){
            for(int i =  assassin[j].length - 1; i >= 0; i--){
                assassin[j][i] = temp.getSubimage(i * ASSASSIN_W_D, j * ASSASSIN_H_D, ASSASSIN_W_D, ASSASSIN_H_D);
            }
        }

        mage = new BufferedImage[5][10];
        temp = LoadSave.GetSpriteAtlas(LoadSave.BC);
        for(int j =  mage.length - 1; j >= 0; j--){
            for(int i =  mage[j].length - 1; i >= 0; i--){
                mage[j][i] = temp.getSubimage(i * MAGE_W_D, j * MAGE_H_D, MAGE_W_D, MAGE_H_D);
            }
        }
    }

    public void resetAll() {
        for(Dummy d : dummies)
            d.resetE();
        for(Minotaur m : minotaurs)
            m.resetE();
        for(Assassin a : assassins)
            a.resetE();
        for(Mage ma : mages)
            ma.resetE();
    }
}
