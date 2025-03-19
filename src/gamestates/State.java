package gamestates;

import Main.Game;
import audio.AudioPlayer;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;

    public State(Game game){
        this.game = game;

    }

    public boolean IsIn(MouseEvent e, MenuButton mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame(){
        return game;
    }

    public void setGameState(Gamestate state){
        switch (state){
            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
            case PLAYING -> game.getAudioPlayer().setLvlS(game.getPlaying().getLm().getLvlI());
        }

        Gamestate.state = state;
    }
}
