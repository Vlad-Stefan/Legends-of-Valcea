package gamestates;

import Main.Game;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManeger;
import objects.ObjManager;
import ui.GameOverOl;
import ui.LvlCompletedO;
import ui.PauseOv;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManeger lm;
    private EnemyManager em;
    private ObjManager om;
    private boolean paused = false;
    private PauseOv po;
    private GameOverOl gameOverOl;
    private LvlCompletedO lvlCompletedO;

    private int xLvlO;
    private int leftB = (int)(0.2 * Game.GWIDTH);
    private int rightB = (int)(0.8 * Game.GWIDTH);
    /*private int lvlTW = LoadSave.GetLvlData()[0].length;
    private int maxTO = lvlTW - Game.WIDTH;*/
    private int maxLvlO;

    private BufferedImage backgr, backgr1, backgr3;

    private boolean GO;
    private boolean LvlC;
    private boolean playerDead;

    public Playing(Game game) {
        super(game);
        initClasses();
        backgr = LoadSave.GetSpriteAtlas(LoadSave.LVL1B);
        backgr1 = LoadSave.GetSpriteAtlas(LoadSave.LVL2B);
        backgr3 = LoadSave.GetSpriteAtlas(LoadSave.LVL3B);

        //po.update();

        calcLvlO();
        loadStartLvl();
    }

    public void loadNextLvl(){

        lm.loadNextLvl();
        player.setSpawn(lm.getCurentLvl().getPS());
        resetAll();
    }

    public void loadStartLvl() {
        em.loadEnemies(lm.getCurentLvl());
        om.loadObj(lm.getCurentLvl());
    }

    private void calcLvlO() {
        maxLvlO = lm.getCurentLvl().getMaxLvlO();
    }

    private void initClasses() {

        lm = new LevelManeger(game);
        em = new EnemyManager(this);
        om = new ObjManager(this);

        player = new Player(200,200,(int) (64 * Game.SCALE),(int) (64 * Game.SCALE), this);
        player.loadlvlData(lm.getCurentLvl().getLvlData());
        player.setSpawn(lm.getCurentLvl().getPS());

        po = new PauseOv(this);
        gameOverOl = new GameOverOl(this);
        lvlCompletedO = new LvlCompletedO(this);

    }

    public Player getPlayer(){
        return player;
    }

    public void windowFocustLost(){
        player.resetDBoolean();
    }

    @Override
    public void update() {
        if(paused){
            po.update();
        }else if(LvlC){
            lvlCompletedO.update();
        }else if(GO){
            gameOverOl.update();
        }else if(playerDead){
            player.update();
        }
        else{
            lm.update();
            om.update();
            player.update();
            em.update(lm.getCurentLvl().getLvlData(), player);
            chackCloseB();
        }

    }

    private void chackCloseB() {
        int pX = (int)player.getHitbox().x;
        int diff = pX - xLvlO;

        if(diff > rightB)
            xLvlO += diff - rightB;
        else if(diff < leftB)
            xLvlO += diff - leftB;

        if(xLvlO > maxLvlO)
            xLvlO = maxLvlO;
        else if(xLvlO < 0)
            xLvlO = 0;

    }

    @Override
    public void draw(Graphics g) {
        if(lm.getLvlI() == 0)
            g.drawImage(backgr, 0, 0, Game.GWIDTH, Game.GHIGHT, null);
        else if(lm.getLvlI() == 1)
            g.drawImage(backgr1,0, 0, Game.GWIDTH, Game.GHIGHT, null);
        else g.drawImage(backgr3,0, 0, Game.GWIDTH, Game.GHIGHT, null);
        lm.draw(g, xLvlO);
        player.render(g, xLvlO);
        em.draw(g, xLvlO);
        om.draw(g, xLvlO);

        if(paused) {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,Game.GWIDTH,Game.GHIGHT);
            po.draw(g);
        }else if(GO)
            gameOverOl.draw(g);
        else if(LvlC)
            lvlCompletedO.draw(g);
    }

    public void resetAll(){
        GO = false;
        paused = false;
        LvlC = false;
        playerDead = false;
        player.resetAll();
        em.resetAll();
        om.resetAllO();
    }

    public void setGO(boolean GO){
        this.GO = GO;
    }

    public void checkEH(Rectangle2D.Float attackBox){
        em.checkEnemH(attackBox);
    }

    public void checkOH(Rectangle2D.Float attackBox){
        om.checkObjHit(attackBox);
    }

    public void checkPotionT(Rectangle2D.Float hitbox){
        om.checkObjectTouched(hitbox);
    }

    public void checkSpikeT(Player player){
        om.checkSpikesT(player);
    }


    public void mouseDragged(MouseEvent e) {
        if(!GO)
            if(paused)
                po.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!GO)
            if(e.getButton()==MouseEvent.BUTTON1)
                player.setAttacking(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!GO) {
            if (paused)
                po.mousePressed(e);
            else if(LvlC)
                lvlCompletedO.mousePressed(e);
        }else{
            gameOverOl.mousePressed(e);
        }
    }

    @Override
    public void mouseRelessed(MouseEvent e) {
        if(!GO) {
            if (paused)
                po.mouseRelessed(e);
            else if(LvlC)
                lvlCompletedO.mouseRelessed(e);
        }else{
            gameOverOl.mouseRelessed(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!GO) {
            if (paused)
                po.mouseMoved(e);
            else if(LvlC)
                lvlCompletedO.mouseMoved(e);
        }else{
        gameOverOl.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(GO)
            gameOverOl.keyPressed(e);
        else
         switch (e.getKeyCode()){

            case KeyEvent.VK_A:
                player.setLeft(true);
                break;

            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }

    }

    @Override
    public void keyRelessed(KeyEvent e) {
        if(!GO)
            switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;

            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }


    public void setLvlC(boolean lvlC){
        this.LvlC = lvlC;
    }

    public void setMaxLvlOtLvlO(int lvlO){
        this.maxLvlO = lvlO;
    }

    public void unpausedG(){
        paused = false;
    }

    public EnemyManager getEm(){
        return em;
    }

    public ObjManager getOm(){
        return om;
    }

    public LevelManeger getLm(){
        return lm;
    }

    public void setPlayerDead(boolean playerDead) {
        this.playerDead = playerDead;
    }
}
