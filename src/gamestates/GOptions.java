package gamestates;

import Main.Game;
import ui.AudioOpt;
import ui.PauseButton;
import ui.URMButtons;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constans.UI.URMButtons.*;

public class GOptions extends State implements Statemethods{

    private AudioOpt audioOpt;
    private BufferedImage backG, optBI;
    private int bgX, bgY, bgW, bgH;
    private URMButtons menuB;

    public GOptions(Game game) {
        super(game);
        loadImg();
        loadB();
        audioOpt = game.getAudioOpt();
    }

    private void loadB() {
        int mX = (int)(387 * Game.SCALE);
        int mY = (int)(325 * Game.SCALE);

        menuB = new URMButtons(mX,mY,URM_SIZE,URM_SIZE,2);
    }

    private void loadImg() {
        backG = LoadSave.GetSpriteAtlas(LoadSave.MBI);
        optBI = LoadSave.GetSpriteAtlas(LoadSave.PBK);

        bgW = (int)(optBI.getWidth() * Game.SCALE);
        bgH = (int)(optBI.getHeight() * Game.SCALE);
        bgX = Game.GWIDTH / 2 -bgW / 2;
        bgY = (int)(33 * Game.SCALE);
    }

    @Override
    public void update() {
        menuB.update();
        audioOpt.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backG, 0, 0,Game.GWIDTH,Game.GHIGHT, null);
        g.drawImage(optBI, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOpt.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOpt.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e,menuB)){
            menuB.setMousePressed(true);
        }
        else audioOpt.mousePressed(e);
    }

    @Override
    public void mouseRelessed(MouseEvent e) {
        if(isIn(e,menuB)){
            if(menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
        }
        else audioOpt.mouseRelessed(e);

        menuB.resetB();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        if(isIn(e,menuB))
            menuB.setMouseOver(true);
        else audioOpt.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Gamestate.state = Gamestate.MENU;
    }

    @Override
    public void keyRelessed(KeyEvent e) {

    }

    private boolean isIn(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
