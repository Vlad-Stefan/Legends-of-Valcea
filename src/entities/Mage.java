package entities;

import Main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constans.Directions.LEFT;
import static utilz.Constans.Directions.RIGHT;
import static utilz.Constans.EnemyC.*;

public class Mage extends Enemy {

    private int attackBoxOx;

    public Mage(int x, int y) {
        super(x, y, MAGE_W, MAGE_H, MAGE);
        initHitbox(32,64);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,64 * Game.SCALE,64 * Game.SCALE);
        attackBoxOx = (int)(15 * Game.SCALE);
    }

    public void update(int[][] lvlData, Player player){
        updateB(lvlData, player);
        updateAniTick();
        updateAttackBox();

    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOx;
        attackBox.y = hitbox.y;
    }

    private void updateB(int[][] lvlData, Player player){
        if(firstupdate)
            firstUpdC(lvlData);

        if(inAir)
            updateinAir(lvlData);
        else {
            switch (state){
                case IDLE:
                    newState(MOVING);
                    break;
                case MOVING:
                    if(CanSeeP(lvlData, player)) {
                        turnPlaye(player);
                        if (isPlClose(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if(aniInd == 0)
                        attackC = false;

                    if(aniInd == 3 && !attackC)
                        checkEH(attackBox,player);
                    break;
                case HIT:
                    break;
            }
        }
    }

    public int flipX(){
        if(walkD == RIGHT)
            return width;
        else return 0;
    }

    public int flipW(){
        if(walkD == RIGHT)
            return -1;
        else return +1;
    }
}
