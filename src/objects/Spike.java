package objects;

import Main.Game;

public class Spike extends GameObj{

    public Spike(int x, int y, int objT) {
        super(x, y, objT);

        initHitbox(32,8);
        xDrawO = 0;
        yDrawO = (int)(Game.SCALE * 15);
        hitbox.y += yDrawO + 15;
    }
}
