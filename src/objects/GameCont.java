package objects;

import Main.Game;

import static utilz.Constans.ObjCst.BARREL;

public class GameCont extends GameObj{


    public GameCont(int x, int y, int objT) {
        super(x, y, objT);
        createHitbox();
    }

    private void createHitbox() {

        if(objT == BARREL){
            initHitbox(32,32);

            xDrawO = (int) (35 * Game.SCALE);
            yDrawO = (int) (24 * Game.SCALE);
        }
        hitbox.y -= yDrawO ;
        hitbox.y += (int)(Game.SCALE * 23);
        hitbox.x -= (int)(Game.SCALE * 9);
    }

    public void update(){
        if(doAni)
            updateAniTick();
    }
}
