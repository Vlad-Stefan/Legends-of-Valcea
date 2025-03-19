package entities;

import Main.Game;
import audio.AudioPlayer;
import gamestates.Playing;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constans.Directions.*;
import static utilz.Constans.Directions.DOWN;
import static utilz.Constans.GRAVITY;
import static utilz.Constans.Player.*;
import static utilz.HelpM.*;

public class Player extends Entity{

    private BufferedImage[][] ani;
    private int aniSpeed=15;
    private boolean left ,up ,down ,right,jump;
    private boolean moving = false;
    private  boolean attacking = false;
    private int[][] lvlData;
    private float xDrawO = 14 * Game.SCALE;
    private float yDrawO = 1 *Game.SCALE;


    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedCol = 0.5f * Game.SCALE;


    private BufferedImage statBar;

    private int statusBarW = (int)(192 * Game.SCALE);
    private int statusBarH = (int)(60 * Game.SCALE);
    private int statusBarX = (int)(14 * Game.SCALE);
    private int statusBarY = (int)(14 * Game.SCALE);

    private int healthBarW = (int)(120 * Game.SCALE);
    private int healthBarH = (int)(33 * Game.SCALE);
    private int healthBarX = (int)(59 * Game.SCALE);
    private int healthBarY = (int)(17 * Game.SCALE);


    private int healthW = healthBarW;


    private int flipX = 0;
    private int flipW = 1;

    private boolean attackC;
    private Playing playing;


    public Player(float x, float y, int width, int height, Playing playing) {

        super(x, y,width,height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.speed = Game.SCALE * 1.0f;
        animations();
        initHitbox(32,64);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,15 * Game.SCALE,30 * Game.SCALE);
        resetAttackBox();
    }

    public void update(){
        updateHealth();
        if(currentHealth <= 0){
            if(state != DIE){
                state = DIE;
                aniTick = 0;
                aniInd = 0;
                playing.setPlayerDead(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            }else if(aniInd == GetSprite(DIE) - 1 && aniTick >= aniSpeed - 1){
                playing.setGO(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEO);
            }else update1();

            //playing.setGO(true);
            return;
        }

        updateAttackBox();

        updatePos();
        if(moving) {
            checkPotionT();
            checkSpikeT();
        }
        if(attacking)
            checkAttack();
        update1();
        setAnimation();

    }

    private void checkSpikeT() {
        playing.checkSpikeT(this);
    }

    private void checkPotionT() {
        playing.checkPotionT(hitbox);
    }

    private void checkAttack() {
        if(attackC || aniInd != 1)
            return;
        attackC = true;
        playing.checkEH(attackBox);
        playing.checkOH(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void updateAttackBox() {
        if(right && left){
            if(flipW == 1){
                attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 5);
            }else{
                attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE - 20 );
            }
        }else if(right){
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 5);
        }else if(left){
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE - 20 );
        }
        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealth() {
        healthW = (int)((currentHealth / (float)maxHealth) * healthBarW);
    }

    public void render(Graphics g, int xLvlO){

        g.drawImage(ani[state][aniInd],
                (int) (hitbox.x- xDrawO) - xLvlO + flipX,
                (int) (hitbox.y- yDrawO),width * flipW,height,null);
        //drawHitbox(g, xLvlO);

        //drawAttackBox(g,xLvlO);
        drawUI(g);
    }


    private void drawUI(Graphics g) {
        g.drawImage(statBar, statusBarX, statusBarY, statusBarW, statusBarH, null);
        g.setColor(Color.red);
        g.fillRect(healthBarX + statusBarX, healthBarY + statusBarY, healthW, healthBarH);
    }

    private void animations() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.P2);

            ani =new BufferedImage[5][10];
            for(int j=0;j<ani.length;j++)
                for(int i=0;i<ani[j].length;i++)
                {
                    ani[j][i]=img.getSubimage(i*32,j*32,32,32);
                }
            statBar = LoadSave.GetSpriteAtlas(LoadSave.HB);

    }

    public void loadlvlData(int[][] lvlData){
        this.lvlData=lvlData;
        if(!IsEntityFloor(hitbox,lvlData))
            inAir = true;
    }

    private void updatePos() {
        moving=false;

        if(jump)
            jump();
        /*if(!left && !right && !inAir)
            return;*/
        if(!inAir)
            if((!left && !right) || (right && left))
                return;

        float xSpeed=0;

        if(left){
            xSpeed-=speed;
            flipX = width;
            flipW = -1;
        }
        if(right){
            xSpeed+=speed;
            flipX = 0;
            flipW = 1;
        }

        if(!inAir){
            if(!IsEntityFloor(hitbox,lvlData)){
                inAir = true;
            }
        }

        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXP(xSpeed);
            }
            else {
                hitbox.y = GetEntityY(hitbox,airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedCol;
                updateXP(xSpeed);
            }
        }else{
            updateXP(xSpeed);
        }
        moving = true;

    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXP(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x+=xSpeed;

        }else{
            hitbox.x = GetEtityXPosW(hitbox,xSpeed);
        }
    }

    public void changeHealth(int val) {
        currentHealth += val;

        if(currentHealth <= 0){
            currentHealth = 0;

        }else if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }

    public void kill(){
        currentHealth = 0;
    }

    private void update1() {
        aniTick++;
        if(aniTick>=aniSpeed)
        {
            aniTick=0;
            aniInd++;
            if(aniInd>=GetSprite(state)) {
                aniInd = 0;
                attacking=false;
                attackC = false;
            }
        }
    }

    private void setAnimation() {
        int startAni=state;
        if(moving)
            state=RUN;
        else {
            state=IDLE;
        }

        if(attacking) {
            state = ATTACK;
            if(startAni != ATTACK){
                aniInd = 0;
                aniTick = 0;
                return;
            }
        }

        if(startAni != state) {
            resetAni();
        }
    }

    private void resetAni() {
        aniTick=0;
        aniInd=0;
    }


    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isRight() {
        return right;
    }


    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void resetDBoolean(){
        left=false;
        down=false;
        right=false;
        up=false;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }

    public void resetAll() {
        resetDBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        airSpeed = 0f;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        resetAttackBox();

        if(!IsEntityFloor(hitbox, lvlData))
            inAir = true;
    }

    private void resetAttackBox(){
        if(flipW == 1){
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 5);
        }else{
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE - 20 );
        }
    }
}
