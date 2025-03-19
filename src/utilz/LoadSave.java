package utilz;

import Main.Game;
import entities.Dummy;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;



public class LoadSave {

    public static final String PA = "Assassin.png";
    public static final String BC = "BlueCleric.png";
    public static final String P2 = "MitheralKnight.png";
    public static final String LA = "sprite_sheet (1).png";
    public static final String LA2 = "sprite_sheet (4).png";
    public static final String LA3 = "sprite_sheet (6).png";
    public static final String LVL1 = "lvls/lvl11.png";
    public static final String LVL1B = "lvl1_back.jpg";
    public static final String LVL2B = "bg_lvl2.jpg";
    public static final String LVL3B = "bg_lvl3.jpg";
    public static final String MB = "button_atlas.png";
    public static final String MBI = "menu_back.jpg";
    public static final String MBC = "menu_atlas1.png";
    public static final String PBK = "pause_menu2.png";
    public static final String SB = "sound_button.png";
    public static final String URMB = "urm_buttons.png";
    public static final String VB = "volume_buttons.png";
    public static final String DS = "Death.png";
    public static final String HB = "health_bar.png";
    public static final String CLVL = "SfLvl.png";
    public static final String DM = "menuDead.png";
    public static final String OBJ = "Object.png";
    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String SPK = "Spike.png";
    public static final String MS = "PurpleOrc.png";

    public static BufferedImage GetSpriteAtlas(String file){
        BufferedImage img = null;
        InputStream is=LoadSave.class.getResourceAsStream("/" + file);
        try {
            img = ImageIO.read(is);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLvls() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();

        /*for(File f : files){
            System.out.println("file " + f.getName());
        }*/

        BufferedImage[] imgs = new BufferedImage[files.length];

        for(int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(files[i]);
            }catch (IOException e){
                e.printStackTrace();
            }

        return imgs;
    }



}
