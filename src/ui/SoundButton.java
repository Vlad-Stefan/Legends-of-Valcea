package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constans.UI.PButtons.*;

public class SoundButton extends PauseButton{

    private BufferedImage[][] soundI;
    private boolean mousePressed, mouseOver;
    private boolean muted;
    private int rowI, colI;

    public SoundButton(int x, int y, int width, int height ) {
        super(x, y, width, height);
        loadSI();
    }

    private void loadSI() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SB);
        soundI = new BufferedImage[2][3];
        for(int j = 0; j < soundI.length; j++)
            for(int i = 0; i < soundI[j].length; i++)
                soundI[j][i] = temp.getSubimage(i*S_SIZE_D, j*S_SIZE_D, S_SIZE_D, S_SIZE_D);
    }

    public void update(){
        if(muted)
            rowI = 1;
        else rowI = 0;

        colI = 0;
        if(mouseOver)
            colI = 1;
        if(mousePressed)
            colI = 2;
    }

    public void reset(){
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g){
        g.drawImage(soundI[rowI][colI], x, y, w, h, null);

    }

    public boolean isMuted() {
        return muted;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
