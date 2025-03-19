package ui;

import Main.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.Constans;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import static utilz.Constans.UI.PButtons.*;
import static utilz.Constans.UI.URMButtons.*;
import static utilz.Constans.UI.VButtons.*;


public class PauseOv {
    private Playing playing;
    private BufferedImage background;
    private int bX,bY,bW,bH;
    private URMButtons menuB, replayB, unpauseB;
    private AudioOpt audioOpt;
    public PauseOv(Playing playing){
        this.playing = playing;
        loadBackground();
        audioOpt = playing.getGame().getAudioOpt();
        createUrmB();

    }


    private void createUrmB() {
        int mX = (int) (313 * Game.SCALE);
        int rX = (int) (387 * Game.SCALE);
        int uX = (int) (462 * Game.SCALE);
        int bY = (int) (300 * Game.SCALE);

        menuB = new URMButtons(mX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new URMButtons(rX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new URMButtons(uX, bY, URM_SIZE, URM_SIZE, 0);
    }


    private void loadBackground() {
        background = LoadSave.GetSpriteAtlas(LoadSave.PBK);
        bW = (int) (background.getWidth() * Game.SCALE);
        bH = (int) (background.getHeight() * Game.SCALE);
        bX = Game.GWIDTH / 2 - bW / 2;
        bY = (int) (25 * Game.SCALE);
    }

    public void update(){


        menuB.update();
        replayB.update();
        unpauseB.update();

        audioOpt.update();

    }

    public void draw(Graphics g){
        g.drawImage(background,bX,bY,bW,bH,null);



        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        audioOpt.draw(g);


    }

    public void mouseDragged(MouseEvent e){

        audioOpt.mouseDragged(e);
    }


    public void mousePressed(MouseEvent e) {
        if(isIn(e,menuB))
            menuB.setMousePressed(true);
        else if(isIn(e,replayB))
            replayB.setMousePressed(true);
        else if(isIn(e,unpauseB))
            unpauseB.setMousePressed(true);
        else audioOpt.mousePressed(e);
    }


    public void mouseRelessed(MouseEvent e) {

        if(isIn(e, menuB)){
            if(menuB.isMousePressed()) {
                playing.getGame().saver.saveAll();
                //playing.resetAll();
                playing.setGameState(Gamestate.MENU);
                playing.unpausedG();
            }
        }
        else if(isIn(e, replayB)){
            if(replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpausedG();
            }
        }
        else if(isIn(e, unpauseB)){
            if(unpauseB.isMousePressed())
                playing.unpausedG();
        }
        else audioOpt.mouseRelessed(e);

        menuB.resetB();
        replayB.resetB();
        unpauseB.resetB();

    }


    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);



        if(isIn(e, menuB))
            menuB.setMouseOver(true);
        else if(isIn(e, replayB))
            replayB.setMouseOver(true);
        else if(isIn(e, unpauseB))
            unpauseB.setMouseOver(true);
        else audioOpt.mouseMoved(e);

    }

    private boolean isIn(MouseEvent e,PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
