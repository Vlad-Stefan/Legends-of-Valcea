package ui;

import Main.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.Constans;
import utilz.LoadSave;
import static utilz.Constans.UI.URMButtons.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LvlCompletedO {
    private Playing playing;
    private URMButtons menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    public LvlCompletedO(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int)(330 * Game.SCALE);
        int nextX = (int)(445 * Game.SCALE);
        int y = (int)(195 * Game.SCALE);
        next = new URMButtons(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new URMButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.CLVL);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = Game.GWIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void update(){
        next.update();
        menu.update();
    }

    private boolean isIn(URMButtons b, MouseEvent e){
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu, e)){
            menu.setMouseOver(true);
        }
        else if(isIn(next, e)){
            next.setMouseOver(true);
        }
    }

    public void mouseRelessed(MouseEvent e){
        if(isIn(menu, e)){
            if(menu.isMousePressed()) {
                playing.resetAll();
                playing.setGameState(Gamestate.MENU);
            }
        }
        else if(isIn(next, e)){
            if(next.isMousePressed()){
                playing.loadNextLvl();
                playing.getGame().getAudioPlayer().setLvlS(playing.getLm().getLvlI());
            }

        }

        menu.resetB();
        next.resetB();
    }

    public void mousePressed(MouseEvent e){
        if(isIn(menu, e)){
            menu.setMousePressed(true);
        }
        else if(isIn(next, e)){
            next.setMousePressed(true);
        }
    }
}
