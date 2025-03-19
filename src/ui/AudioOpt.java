package ui;

import Main.Game;
import gamestates.Gamestate;

import java.awt.*;
import java.awt.event.MouseEvent;

import static utilz.Constans.UI.PButtons.S_SIZE;
import static utilz.Constans.UI.VButtons.SL_W;
import static utilz.Constans.UI.VButtons.VOL_H;


public class AudioOpt {

    private VolumeButton volB;
    private SoundButton music,sfxB;

    private Game game;

    public AudioOpt(Game game){
        this.game = game;
        createSB();
        createVolB();
    }

    private void createSB() {
        int soundX = (int)(450 * Game.SCALE);
        int musicY = (int)(140 * Game.SCALE);
        int sfxy = (int)(186 * Game.SCALE);
        music = new SoundButton(soundX,musicY,S_SIZE,S_SIZE);
        sfxB = new SoundButton(soundX,sfxy,S_SIZE,S_SIZE);
    }

    private void createVolB() {
        int vX = (int)(309 * Game.SCALE);
        int vY = (int)(250 * Game.SCALE);
        volB = new VolumeButton(vX,vY,SL_W, VOL_H);
    }

    public void update(){
        volB.update();
        sfxB.update();
        music.update();
    }

    public void draw(Graphics g){
        music.draw(g);
        sfxB.draw(g);

        volB.draw(g);
    }

    public void mouseDragged(MouseEvent e){
        if(volB.isMouseOver()){
            float valB = volB.getFloatVal();
            volB.changeX(e.getX());
            float valA = volB.getFloatVal();
            if(valB != valA){
                game.getAudioPlayer().setVol(valA);
            }
        }
    }


    public void mousePressed(MouseEvent e) {
        if(isIn(e, music))
            music.setMousePressed(true);
        else if(isIn(e, sfxB))
            sfxB.setMousePressed(true);
        else if(isIn(e,volB))
            volB.setMousePressed(true);
    }


    public void mouseRelessed(MouseEvent e) {
        if(isIn(e, music)){
            if(music.isMousePressed()){
                music.setMuted(!music.isMuted());
                game.getAudioPlayer().toggleSM();
            }
        }
        else if(isIn(e, sfxB)){
            if(sfxB.isMousePressed()) {
                sfxB.setMuted(!sfxB.isMuted());
                game.getAudioPlayer().toggleEM();
            }
        }

        music.reset();
        sfxB.reset();
        volB.resetB();
    }


    public void mouseMoved(MouseEvent e) {
        music.setMouseOver(false);
        sfxB.setMouseOver(false);

        volB.setMouseOver(false);

        if(isIn(e, music))
            music.setMouseOver(true);
        else if(isIn(e, sfxB))
            sfxB.setMouseOver(true);
        else if(isIn(e, volB))
            volB.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e,PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
