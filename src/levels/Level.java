package levels;

import Main.Game;
import entities.Assassin;
import entities.Dummy;
import entities.Mage;
import entities.Minotaur;
import objects.GameCont;
import objects.Potion;
import objects.Spike;
import utilz.HelpM;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpM.*;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;

    private ArrayList<Dummy> dummyes;
    private ArrayList<Assassin> assassins;
    private ArrayList<Minotaur> minotaurs;
    private ArrayList<Mage> mages;

    private ArrayList<Potion> potions;
    private ArrayList<GameCont> containers;
    private ArrayList<Spike> spikes;

    private int lvlTW;
    private int maxTO;
    private int maxLvlO;
    private Point playerS;

    public Level(BufferedImage img){
        this.img = img;
        createLvlData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        calcLvlO();
        calcPS();
        
    }

    private void createSpikes(){
        spikes = HelpM.GetSpikes(img);
    }

    private void createContainers() {
        containers = HelpM.GetContainers(img);
    }

    private void createPotions() {

        potions = HelpM.GetPotions(img);
    }

    private void calcPS() {
        playerS = GetPlayerS(img);
    }

    private void calcLvlO() {
        lvlTW = img.getWidth();
        maxTO = lvlTW - Game.WIDTH;
        maxLvlO = Game.TSIZE * maxTO;
    }

    private void createEnemies() {
        dummyes = GetDummy(img);
        minotaurs = GetMinotaur(img);
        assassins = GetAssassin(img);
        mages = GetMage(img);
    }


    private void createLvlData() {
        lvlData = GetLvlData(img);
    }

    public int getSpriteI(int x,int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData(){
        return lvlData;
    }

    public int getMaxLvlO(){
        return maxLvlO;
    }

    public ArrayList<Dummy> getDummyes(){
        return dummyes;
    }

    public ArrayList<Minotaur> getMinotaurs() {
        return minotaurs;
    }

    public ArrayList<Assassin> getAssassins() {
        return assassins;
    }

    public Point getPS(){
        return playerS;
    }

    public ArrayList<GameCont> getContainers(){
        return containers;
    }

    public ArrayList<Potion> getPotions() {

        return potions;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }


    public ArrayList<Mage> getMages() {
        return mages;
    }
}
