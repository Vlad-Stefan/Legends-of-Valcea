package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.Constans;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constans.ObjCst.*;

public class ObjManager {

    private Playing playing;
    private BufferedImage[][] contImg, potionImgs;
    private BufferedImage spikeImg;
    private ArrayList<GameCont> containers;
    private ArrayList<Potion> potions;
    private ArrayList<Spike> spikes;

    public ObjManager(Playing playing) {
        this.playing = playing;
        loadImgs();

        /*containers = new ArrayList<>();
        containers.add(new GameCont(500,300,BARREL));*/
    }

    public void checkSpikesT(Player player){
        for(Spike s: spikes){
            if(s.getHitbox().intersects(player.getHitbox())){
                player.kill();
            }
        }
    }

    public void loadObj(Level newLvl) {
        potions = new ArrayList<>(newLvl.getPotions());
        containers = new ArrayList<>(newLvl.getContainers());
        spikes = newLvl.getSpikes();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions)
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjT() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
    }

    public void checkObjHit(Rectangle2D.Float attackbox) {
        for(GameCont c : containers)
            if(c.isActive() && !c.doAni){
                if(c.getHitbox().intersects(attackbox)){
                    c.setAnimation(true);
                    //if(c.getObjT() == BARREL)
                        potions.add(new Potion((int) (c.getHitbox().x + c.getHitbox().width / 2), (int) (c.getHitbox().y + c.getHitbox().height / 8 ), 1));
                    return;
                }
            }
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[1][7];

        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage contSpr = LoadSave.GetSpriteAtlas(LoadSave.OBJ);
        contImg = new BufferedImage[1][7];

        for(int j = 0; j < contImg.length; j++)
            for(int i = 0; i < contImg[0].length; i++)
                contImg[j][i] = contSpr.getSubimage(64 * i, 37 * j, 64, 37);

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.SPK);
    }

    public void update(){
        for (Potion p : potions)
            if (p.isActive())
                p.update();

        for(GameCont c : containers){
            if(c.isActive())
                c.update();
        }
    }

    public void draw(Graphics g, int xLvlO){
        deawPotions(g,xLvlO);
        drawCont(g,xLvlO);
        drawSpike(g,xLvlO);
    }

    private void drawSpike(Graphics g, int xLvlO) {
        for(Spike s : spikes)
            g.drawImage(spikeImg,(int)(s.getHitbox().x - xLvlO),(int)(s.getHitbox().y - s.getyDrawO()),SPIKE_W,SPIKE_H,null);
    }

    private void deawPotions(Graphics g, int xLvlO) {
        for (Potion p : potions)
            if (p.isActive()) {
                if (p.getObjT() == RED_POTION)
                    g.drawImage(potionImgs[0][p.getAniInd()], (int) (p.getHitbox().x - p.getxDrawO() - xLvlO), (int) (p.getHitbox().y - p.getyDrawO()), POTION_W, POTION_H,
                        null);
            }
    }

    private void drawCont(Graphics g, int xLvlO) {
        for(GameCont c : containers){
            if(c.isActive())
                if(c.getObjT() == BARREL) {
                    g.drawImage(contImg[0][c.getAniInd()], (int) (c.getHitbox().x - c.getxDrawO() - xLvlO), (int) (c.getHitbox().y - c.getyDrawO()), BARREL_W, BARREL_H, null);
                   // c.drawHitbox(g,xLvlO);
                }
        }
    }

    public void resetAllO(){

        loadObj(playing.getLm().getCurentLvl());

        for (Potion p : potions)
            p.rest();

        for(GameCont c : containers)
            c.rest();
    }

}
