package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constans.UI.URMButtons.*;

public class URMButtons extends PauseButton{
    private BufferedImage[] imgs;
    private int rowI, index;
    private boolean mouseOver, mousePressed;

    public URMButtons(int x, int y, int width, int height, int rowI) {
        super(x, y, width, height);
        this.rowI = rowI;
        loadImg();
    }

    private void loadImg() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URMB);
        imgs = new BufferedImage[3];
        for(int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * URM_SIZE_D, rowI * URM_SIZE_D, URM_SIZE_D, URM_SIZE_D);

    }

    public  void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }

    public void draw(Graphics g){
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetB(){
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
}
