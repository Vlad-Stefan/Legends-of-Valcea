package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constans.UI.VButtons.*;


public class VolumeButton extends PauseButton{
    private BufferedImage[] img;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseOver;
    private boolean mousePressed;
    private int bX;
    private int minX, maxX;
    private float floatVal = 0f;

    public VolumeButton(int x, int y, int w, int h) {
        super(x + w/2, y, VOL_W, h);
        bounds.x -=VOL_W/2;
        bX = x + w/2;
        this.x = x;
        this.w = w;
        minX = x + VOL_W/2;
        maxX = x + w - VOL_W/2;
        loadImg();
    }

    private void loadImg() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VB);
        img = new BufferedImage[3];
        for(int i=0; i < img.length; i++)
            img[i] = temp.getSubimage(i*VOL_W_D,0,VOL_W_D,VOL_H_D);

        slider = temp.getSubimage(3*VOL_W_D,0,SL_W_D,VOL_H_D);

    }

    public  void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }

    public void draw(Graphics g){
        g.drawImage(slider,x,y,w,h,null);
        g.drawImage(img[index],bX - VOL_W / 2,y,VOL_W,h,null);
    }

    public void changeX(int x){
        if(x < minX)
            bX = minX;
        else if(x > maxX)
            bX = maxX;
        else bX = x;
        updateFloatVal();
        bounds.x = bX - VOL_W / 2;
    }

    private void updateFloatVal() {
        float range = maxX - minX;
        float val = bX - minX;
        floatVal = val / range;
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

    public float getFloatVal() {
        return floatVal;
    }
}
