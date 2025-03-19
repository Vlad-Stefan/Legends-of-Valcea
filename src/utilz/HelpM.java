package utilz;

import Main.Game;
import entities.Assassin;
import entities.Dummy;
import entities.Mage;
import entities.Minotaur;
import objects.GameCont;
import objects.Potion;
import objects.Spike;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constans.EnemyC.*;
import static utilz.Constans.ObjCst.*;


public class HelpM {

    public static boolean CanMoveHere(float x,float y,float width,float height,int[][] lvlData){
        if(!IsSolid(x,y,lvlData))
            if(!IsSolid(x+width,y+height,lvlData))
                if(!IsSolid(x+width,y,lvlData))
                    if(!IsSolid(x,y+height,lvlData))
                        return true;
        return false;

    }

    private static boolean IsSolid(float x,float y,int[][] lvlData){
        int maxW = lvlData[0].length * Game.TSIZE;
        if(x<0 || x>= maxW)
            return true;
        if(y<0 || y>= Game.GHIGHT)
            return true;

        float xI = x/Game.TSIZE;
        float yI = y/Game.TSIZE;

        return IsTileSolid((int)xI,(int)yI,lvlData);
    }

    public static boolean IsTileSolid(int xT, int yT, int[][] lvlData){
        int val = lvlData[yT][xT];

        if(val >= 20 || val < 0 || val!=5)
            return true;
        return false;
    }

    public static float GetEtityXPosW(Rectangle2D.Float hitbpx, float xSpeed){
        int curentTile = (int)(hitbpx.x / Game.TSIZE);
        if(xSpeed > 0){
            int tileX = curentTile * Game.TSIZE;
            int xOff = (int)(Game.TSIZE - hitbpx.width);
            return tileX + xOff +47 ;
        }else{
            return curentTile * Game.TSIZE;
        }
    }

    public static float GetEntityY(Rectangle2D.Float hitbox,float airSpeed){
        int curentTile = (int)(hitbox.y / Game.TSIZE);
        if(airSpeed > 0){
            int tileY = curentTile * Game.TSIZE;
            int yOff = (int)(Game.TSIZE - hitbox.height);
            return tileY + yOff + 95;
        }else{
            return curentTile * Game.TSIZE ;
        }
    }

    public static boolean IsEntityFloor(Rectangle2D.Float hitbox, int[][] lvlData){
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xS, int[][] lvlData){
        if(xS > 0)
            return IsSolid(hitbox.x + hitbox.width +xS, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xS, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTilesW(int xS, int xE, int y, int[][] lvlData){
        for(int i=0; i< xE - xS; i++) {
            if (IsTileSolid(xS + i, y, lvlData))
                return false;
            if (!IsTileSolid(xS + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsSight(int[][] lvlData, Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int y){
        int x1 = (int)(hitbox1.x / Game.TSIZE);
        int x2 = (int)(hitbox2.x / Game.TSIZE);

        if(x1 > x2){
            return IsAllTilesW(x2,x1,y,lvlData);
        }else {
            return IsAllTilesW(x1, x2, y, lvlData);
        }
    }

    public static int[][] GetLvlData(BufferedImage img){


        int [][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getRed();
                if(val >= 24)
                    val=0;
                lvlData[j][i] = val;
            }
        return lvlData;
    }

    public static ArrayList<Dummy> GetDummy(BufferedImage img){
        ArrayList<Dummy> list = new ArrayList<>();
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getGreen();
                if(val == DUMMY)
                    list.add(new Dummy(i * Game.TSIZE, j * Game.TSIZE));
            }
        return list;
    }

    public static ArrayList<Minotaur> GetMinotaur(BufferedImage img){
        ArrayList<Minotaur> list = new ArrayList<>();
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getGreen();
                if(val == MINOTAUR)
                    list.add(new Minotaur(i * Game.TSIZE, j * Game.TSIZE));
            }
        return list;
    }

    public static ArrayList<Assassin> GetAssassin(BufferedImage img){
        ArrayList<Assassin> list = new ArrayList<>();
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getGreen();
                if(val == ASSASSIN)
                    list.add(new Assassin(i * Game.TSIZE, j * Game.TSIZE));
            }
        return list;
    }

    public static ArrayList<Mage> GetMage(BufferedImage img){
        ArrayList<Mage> list = new ArrayList<>();
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getGreen();
                if(val == MAGE)
                    list.add(new Mage(i * Game.TSIZE, j * Game.TSIZE));
            }
        return list;
    }

    public static Point GetPlayerS(BufferedImage img){
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getGreen();
                if(val == 100)
                    return new Point(i * Game.TSIZE, j * Game.TSIZE);
            }
        return new Point(1 * Game.TSIZE, 1 * Game.TSIZE);
    }

    public static ArrayList<Potion> GetPotions(BufferedImage img) {
        ArrayList<Potion> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == RED_POTION)
                    list.add(new Potion(i * Game.TSIZE, j * Game.TSIZE, RED_POTION));
            }

        return list;
    }

    public static ArrayList<GameCont> GetContainers(BufferedImage img){
        ArrayList<GameCont> list = new ArrayList<>();
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getBlue();
                if(val == BARREL)
                    list.add(new GameCont(i * Game.TSIZE, j * Game.TSIZE, BARREL));
            }
        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<>();
        for(int j=0; j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int val = color.getBlue();
                if(val == SPIKE)
                    list.add(new Spike(i * Game.TSIZE, j * Game.TSIZE, SPIKE));
            }
        return list;
    }
}
