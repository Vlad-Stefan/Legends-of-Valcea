package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constans.*;
import static utilz.Constans.EnemyC.*;
import static utilz.HelpM.*;
import static utilz.Constans.Directions.*;

public abstract class Enemy extends Entity {
    protected int enemType;
    protected boolean firstupdate = true;

    protected float walkD = LEFT;
    protected int tileY;
    protected float attackD = Game.TSIZE;
    protected boolean active = true;
    protected boolean attackC;

    public Enemy(float x, float y, int width, int height, int enemType) {
        super(x, y, width, height);
        this.enemType = enemType;
        maxHealth = GetMaxHealth(enemType);
        currentHealth = maxHealth;
        speed = Game.SCALE * 0.7f;
    }

    protected void firstUpdC(int[][] lvlData){
        if(!IsEntityFloor(hitbox,lvlData))
            inAir = true;
        firstupdate = false;
    }

    protected void move(int[][] lvlData){
        float xS = 0;
        if(walkD == LEFT)
            xS = -speed;
        else
            xS = speed;

        if(CanMoveHere(hitbox.x + xS, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            if(IsFloor(hitbox,xS,lvlData)){
                hitbox.x += xS;
                return;
            }
        }
        changeWalkD();
    }

    protected void updateinAir(int[][] lvlData){
        if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        }else{
            inAir = false;
            hitbox.y = GetEntityY(hitbox, airSpeed);
            tileY = (int) (hitbox.y / Game.TSIZE);
        }
    }

    protected void turnPlaye(Player player){
        if(player.hitbox.x > hitbox.x)
            walkD = RIGHT;
        else walkD = LEFT;
    }

    protected boolean CanSeeP(int[][] lvlData,Player player){
        int playerY = (int) (player.getHitbox().y / Game.TSIZE);
        if(playerY == tileY)
            if(isPlayerInRange(player)){
                if(IsSight(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absV = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absV <= attackD * 5;

    }

    protected boolean isPlClose(Player player){
        int absV = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absV <= attackD;
    }

    protected void newState(int enemState){
        this.state = enemState;
        aniInd = 0;
        aniTick = 0;
    }

    public void hurt(int a){
        currentHealth -= a;
        if(currentHealth <= 0)
            newState(DIE);
        else newState(HIT);
    }

    protected void checkEH(Rectangle2D.Float attackBox , Player player){
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDMG(enemType));
        attackC = true;
    }

    protected void updateAniTick(){
        aniTick++;
        if(aniTick >= ANIMATION_SPEED){
            aniTick = 0;
            aniInd++;
            if(aniInd >= getSpriteAmount(enemType,state)){
                aniInd = 0;

                switch (state){
                    case ATTACK,HIT -> state = IDLE;
                    case DIE -> active = false;
                }

                if(state == ATTACK)
                    state = IDLE;
                else if(state == HIT)
                    state = IDLE;
                else if(state == DIE)
                    active = false;
            }
        }
    }





    public boolean isActive(){
        return active;
    }


    protected void changeWalkD() {
        if(walkD == LEFT)
            walkD = RIGHT;
        else walkD = LEFT;
    }

    public void resetE(){
        hitbox.x = x;
        hitbox.y = y;
        firstupdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;
    }
}
