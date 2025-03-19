package ui;

import Main.Game;
import gamestates.Gamestate;
import utilz.LoadSave;


import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constans.UI.Buttons.*;

public class MenuButton {
    private int xPos, yPos, rowI, ind;
    private int xOffserC = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowI, Gamestate state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowI = rowI;
        this.state = state;
        loadImg();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffserC, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImg() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MB);
        for(int i = 0; i< imgs.length;i++)
            imgs[i] = temp.getSubimage(i * B_WIDTH_D, rowI * B_HEIGHT_D, B_WIDTH_D, B_HEIGHT_D );
    }

    public void draw(Graphics g){
        g.drawImage(imgs[ind], xPos - xOffserC, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update(){
        ind = 0;
        if(mouseOver)
            ind = 1;
        if(mousePressed)
            ind = 2;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }
    public Rectangle getBounds(){
        return bounds;
    }

    public void applyGameS(){
        Gamestate.state = state;
    }

    public void resetB(){
        mouseOver = false;
        mousePressed = false;
    }

    public Gamestate getState(){
        return state;
    }
}
