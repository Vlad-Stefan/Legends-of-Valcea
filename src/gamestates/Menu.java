package gamestates;

import Main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backGround, backgroundimg;
    private int mX, mY, mW, mH;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundimg = LoadSave.GetSpriteAtlas(LoadSave.MBI);
    }

    private void loadBackground() {
        backGround = LoadSave.GetSpriteAtlas(LoadSave.MBC);
        mW = (int)(backGround.getWidth() * Game.SCALE);
        mH = (int)(backGround.getHeight()* Game.SCALE);
        mX = Game.GWIDTH / 2 - mW / 2;
        mY = (int) (45 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GWIDTH / 2, (int) (150 * Game.SCALE), 0 , Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GWIDTH / 2, (int) (220 * Game.SCALE), 1 , Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GWIDTH / 2, (int) (290 * Game.SCALE), 2 , Gamestate.QUIT);

    }

    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundimg,0,0,Game.GWIDTH,Game.GHIGHT,null);
        g.drawImage(backGround,mX,mY,mW,mH,null);
        for(MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(IsIn(e,mb)){
                mb.setMousePressed(true);
                break;
            }
        }

    }

    @Override
    public void mouseRelessed(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(IsIn(e,mb)){
                if(mb.isMousePressed())
                    mb.applyGameS();
                if(mb.getState() == Gamestate.PLAYING) {
                    game.getAudioPlayer().setLvlS(game.getPlaying().getLm().getLvlI());
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons){
            mb.resetB();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons)
            mb.setMouseOver(false);

        for(MenuButton mb : buttons)
            if(IsIn(e,mb)) {
                mb.setMouseOver(true);
                break;
            }


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
            game.saver.loadAll();
            System.out.println(game.getPlaying().getLm().getLvlI());
            game.getAudioPlayer().setLvlS(game.getPlaying().getLm().getLvlI());
            game.getPlaying().getLm().setLvlI(game.getPlaying().getLm().getLvlI() - 1);
            //game.getPlaying().getLm().loadLastLvl(game.getPlaying().getLm().getLvlI());
            game.getPlaying().getLm().loadNextLvl();
        }
    }

    @Override
    public void keyRelessed(KeyEvent e) {

    }
}
