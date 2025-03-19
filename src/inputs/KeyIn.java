package inputs;

import Main.Game_Panel;
import gamestates.Gamestate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constans.Directions.*;


public class KeyIn implements KeyListener {

    private Game_Panel gp;
    public KeyIn(Game_Panel gp){
        this.gp=gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().keyPressed(e);
                break;
            }
            case MENU -> {
                gp.getGame().getMenu().keyPressed(e);
                break;
            }
            case OPTIONS -> {
                gp.getGame().getOptions().keyPressed(e);
                break;
            }
            default -> {
                break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().keyRelessed(e);
            }
            case MENU -> {
                gp.getGame().getMenu().keyRelessed(e);
            }
            default -> {
                break;
            }
        }

    }
}
