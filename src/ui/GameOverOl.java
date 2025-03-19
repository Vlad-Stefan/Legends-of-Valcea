package ui;

import Main.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constans.UI.URMButtons.URM_SIZE;

public class GameOverOl {

    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private URMButtons menu, play;

    public GameOverOl(Playing playing){
        this.playing = playing;
        createImg();
        createB();
    }

    private void createB() {
        int menuX = (int)(330 * Game.SCALE);
        int playX = (int)(445 * Game.SCALE);
        int y = (int)(195 * Game.SCALE);
        menu = new URMButtons(playX, y, URM_SIZE, URM_SIZE, 2);
        play = new URMButtons(menuX, y, URM_SIZE, URM_SIZE, 0);
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DM);
        imgW = (int)(img.getWidth() * Game.SCALE);
        imgH = (int)(img.getHeight() * Game.SCALE);
        imgX = Game.GWIDTH / 2 - imgW / 2;
        imgY = (int)(50 * Game.SCALE);
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GWIDTH, Game.GHIGHT);

        g.drawImage(img, imgX, imgY, imgW, imgH, null);

        menu.draw(g);
        play.draw(g);

        /*g.setColor(Color.WHITE);
        g.drawString("Game Over", Game.GWIDTH/2, 150);
        g.drawString("Press ESC for Menu!", Game.GWIDTH/2, 300);*/
    }

    public void update(){
        menu.update();
        play.update();
    }

    public void keyPressed(KeyEvent e){
    }

    private boolean isIn(URMButtons b, MouseEvent e){
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e){
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu, e)){
            menu.setMouseOver(true);
        }
        else if(isIn(play, e)){
            play.setMouseOver(true);
        }
    }

    public void mouseRelessed(MouseEvent e){
        if(isIn(menu, e)){
            if(menu.isMousePressed()) {
                playing.resetAll();
                playing.setGameState(Gamestate.MENU);
            }
        }
        else if(isIn(play, e)){
            if(play.isMousePressed()){
                playing.resetAll();
                playing.getGame().getAudioPlayer().setLvlS(playing.getLm().getLvlI());
            }

        }

        menu.resetB();
        play.resetB();
    }

    public void mousePressed(MouseEvent e){
        if(isIn(menu, e)){
            menu.setMousePressed(true);
        }
        else if(isIn(play, e)){
            play.setMousePressed(true);
        }
    }


}
