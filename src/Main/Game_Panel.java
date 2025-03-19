package Main;

import inputs.KeyIn;
import inputs.MouseIn;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static Main.Game.*;
import static utilz.Constans.Player.*;
import static utilz.Constans.Directions.*;

public class Game_Panel extends JPanel {

    private MouseIn ms;
    private Game game;



    public Game_Panel(Game game){

        ms=new MouseIn(this);
        this.game = game;

        SetPanel();
        addKeyListener(new KeyIn(this));
        addMouseListener(ms);
        addMouseMotionListener(ms);

    }


    private void SetPanel() {
        Dimension size = new Dimension(GWIDTH,GHIGHT);
        setPreferredSize(size);
        System.out.println("size: " + GWIDTH + " " + GHIGHT);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        game.render(g);

    }

    public void updategame(){

    }

    public Game getGame(){
        return game;
    }


}
