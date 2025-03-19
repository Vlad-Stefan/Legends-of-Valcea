package levels;

import Main.Game;
import gamestates.Gamestate;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManeger {
    public Game game;
    private BufferedImage[] lvlSprite;
    private ArrayList<Level> levels;
    private int lvlI = 0;

    public LevelManeger(Game game){
        this.game = game;
       // lvlSprite = LoadSave.GetSpriteAtlas(LoadSave.LA);
        levels = new ArrayList<>();
        importOutsideSprites();

        buildAllLvls();
    }

    public void loadLastLvl(int lvlI){
        if(lvlI >= levels.size()){
            lvlI = 0;
            System.out.println("Gata!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLvl = levels.get(lvlI);
        game.getPlaying().getEm().loadEnemies(newLvl);
        game.getPlaying().getPlayer().loadlvlData(newLvl.getLvlData());
        game.getPlaying().setMaxLvlOtLvlO(newLvl.getMaxLvlO());
        game.getPlaying().getOm().loadObj(newLvl);
    }

    public void loadNextLvl(){
        lvlI ++;
        importOutsideSprites();
        if(lvlI >= levels.size()){
            lvlI = 0;
            System.out.println("Gata!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLvl = levels.get(lvlI);
        game.getPlaying().getEm().loadEnemies(newLvl);
        game.getPlaying().getPlayer().loadlvlData(newLvl.getLvlData());
        game.getPlaying().setMaxLvlOtLvlO(newLvl.getMaxLvlO());
        game.getPlaying().getOm().loadObj(newLvl);
    }

    private void buildAllLvls() {
        BufferedImage[] allLvls = LoadSave.GetAllLvls();
        for(BufferedImage img : allLvls) {
            levels.add(new Level(img));
        }
    }

    private void importOutsideSprites() {
        if(lvlI >= levels.size()){
            lvlI = 0;
            System.out.println("Gata!");
            Gamestate.state = Gamestate.MENU;
        }
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LA);
        BufferedImage img2 = LoadSave.GetSpriteAtlas(LoadSave.LA3);
        BufferedImage img3 = LoadSave.GetSpriteAtlas(LoadSave.LA2);
        lvlSprite = new BufferedImage[24];
        for(int j=0;j<4;j++)
            for(int i=0;i<6;i++){
                int index = j*6 + i;
                if(lvlI == 0)
                    lvlSprite[index] = img.getSubimage(i*32,j*32,32,32);
                else if(lvlI == 1)
                    lvlSprite[index] = img2.getSubimage(i*16,j*16,16,16);
                else lvlSprite[index] = img3.getSubimage(i*16,j*16,16,16);
            }
    }

    public void draw(Graphics g, int xLvlO){
        for(int j=0;j<Game.HEIGHT;j++)
            for(int i=0;i<levels.get(lvlI).getLvlData()[0].length;i++){
                int ind=levels.get(lvlI).getSpriteI(i,j);
                g.drawImage(lvlSprite[ind],Game.TSIZE*i - xLvlO,Game.TSIZE*j,Game.TSIZE,Game.TSIZE,null);


            }
        if (lvlI == 0)
            drawInst(g);
    }

    public void update(){


    }

    public Level getCurentLvl(){
        return levels.get(lvlI);
    }

    public int getAmountLvl(){
        return levels.size();
    }

    public int getLvlI(){
        return lvlI;
    }

    public void setLvlI(int lvlI) {
        this.lvlI = lvlI;
    }

    public void drawInst(Graphics g){
        g.setColor(Color.RED);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);
        g.drawString("For moving left and right you can use A and D!", Game.GWIDTH/2 - 500, 150);
        g.drawString("For jumping or attacking you can use SPACE or RIGHT CLICK !", Game.GWIDTH/2 - 500, 190);
    }
}
