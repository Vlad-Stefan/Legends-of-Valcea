package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick,aniInd;
    protected int state;
    protected float airSpeed = 0f;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float speed = 1.0f * Game.SCALE;

    public Entity(float x, float y,int width,int height){
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;

        //initHitbox();
    }

    protected void drawAttackBox(Graphics g, int xLvlOf){
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x - xLvlOf), (int)(attackBox.y), (int)attackBox.width, (int)attackBox.height);
    }

    protected void drawHitbox(Graphics g, int xLvlO){
        g.setColor(Color.MAGENTA);
        g.drawRect((int)hitbox.x - xLvlO, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

     protected void initHitbox(int width,int height) {
        hitbox = new Rectangle2D.Float(x, y,(int)(width * Game.SCALE), (int)(height * Game.SCALE));
    }

    protected void updateHitbox(){
        hitbox.x =(int) x;
        hitbox.y = (int) y;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public int getEnemState(){
        return state;
    }

    public int getAniI() {
        return aniInd;
    }

    public int getCurrentHealth(){
        return currentHealth;
    }
}
