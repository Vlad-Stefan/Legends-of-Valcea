package inputs;

import Main.Game_Panel;
import gamestates.Gamestate;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseIn implements MouseListener, MouseMotionListener {

    private Game_Panel gp;
    public MouseIn(Game_Panel gp){
        this.gp=gp;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().mouseClicked(e);
                break;
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().mousePressed(e);
                break;
            }
            case MENU -> {
                gp.getGame().getMenu().mousePressed(e);
                break;
            }
            case OPTIONS -> {
                gp.getGame().getOptions().mousePressed(e);
                break;
            }
            default -> {
                break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().mouseRelessed(e);
                break;
            }
            case MENU -> {
                gp.getGame().getMenu().mouseRelessed(e);
                break;
            }
            case OPTIONS -> {
                gp.getGame().getOptions().mouseRelessed(e);
                break;
            }
            default -> {
                break;
            }
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().mouseDragged(e);
                break;
            }
            case OPTIONS -> {
                gp.getGame().getOptions().mouseDragged(e);
                break;
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        switch (Gamestate.state) {
            case PLAYING -> {
                gp.getGame().getPlaying().mouseMoved(e);
                break;
            }
            case MENU -> {
                gp.getGame().getMenu().mouseMoved(e);
                break;
            }
            case OPTIONS -> {
                gp.getGame().getOptions().mouseMoved(e);
                break;
            }
            default -> {
                break;
            }
        }
    }

}
