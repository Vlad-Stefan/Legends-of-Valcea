package objects;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constans.ANIMATION_SPEED;
import static utilz.Constans.ObjCst.*;
import static utilz.Constans.EnemyC.*;

public class GameObj {

    protected int x,y , objT;
    protected Rectangle2D.Float hitbox;
    protected boolean doAni, active = true;
    protected int aniTick, aniInd;
    protected int xDrawO, yDrawO;

    public GameObj(int x, int y, int objT) {
        this.x = x;
        this.y = y;
        this.objT = objT;
    }

    protected void updateAniTick(){
        aniTick++;
        if(aniTick >= ANIMATION_SPEED){
            aniTick = 0;
            aniInd++;
            if(aniInd >= getSpriteAmount(objT)) {
                aniInd = 0;
                if(objT == BARREL){
                    doAni = false;
                    active = false;
                }
            }

        }
    }

    public void rest(){
        aniTick = 0;
        aniInd = 0;
        active = true;

        if(objT == BARREL)
            doAni = false;
        else
            doAni = true;
    }


    protected void initHitbox(int width,int height) {
        hitbox = new Rectangle2D.Float(x, y,(int)(width * Game.SCALE), (int)(height * Game.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlO){
        g.setColor(Color.MAGENTA);
        g.drawRect((int)hitbox.x - xLvlO, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getObjT() {
        return objT;
    }

    public int getyDrawO() {
        return yDrawO;
    }

    public int getxDrawO() {
        return xDrawO;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getAniInd(){
        return aniInd;
    }

    public boolean isActive() {
        return active;
    }

    public void setAnimation(boolean doAni){
        this.doAni = doAni;
    }

}
