package Main;

import audio.AudioPlayer;
import entities.Player;
import gamestates.GOptions;
import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.Menu;
import levels.LevelManeger;
import ui.AudioOpt;
import utilz.LoadSave;
import utilz.Save;

import java.awt.*;

public class Game implements Runnable{
    private Game_Window gw;
    private Game_Panel gp;
    private Thread gt;
    private final int FPS=120;
    private final int UPS=200;

    private Playing playing;
    private Menu menu;
    private AudioOpt audioOpt;
    private GOptions options;
    private AudioPlayer audioPlayer;
    public Save saver = new Save(this);


    public final static int DSize = 32;
    public final static float SCALE = 1.5f;
    public final static int WIDTH = 26;
    public final static int HEIGHT = 14;
    public final static int TSIZE = (int) (DSize * SCALE);
    public final static int GWIDTH = TSIZE * WIDTH;
    public final static int GHIGHT = TSIZE * HEIGHT;



    public Game(){

        initClasses();
        gp=new Game_Panel(this);
        gw=new Game_Window(gp);
        gp.setFocusable(true);
        gp.requestFocus();


        StartGame();


    }

    private void initClasses() {
        audioOpt = new AudioOpt(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        options = new GOptions(this);
    }

    private void StartGame(){
        gt=new Thread(this);
        gt.start();
    }

    public void update(){

        switch (Gamestate.state){
            case PLAYING -> {
                playing.update();
            }
            case MENU -> {
                menu.update();
            }
            case QUIT ->{
                System.exit(0);
                break;
            }
            case OPTIONS -> {
                options.update();
                break;
            }
            default -> {
                break;
            }
        }
    }

    public void render(Graphics g){

        switch (Gamestate.state){
            case PLAYING -> {
                playing.draw(g);
                break;
            }
            case MENU -> {
                menu.draw(g);
                break;
            }
            case OPTIONS -> {
                options.draw(g);
                break;
            }
            default -> {
                break;
            }
        }


    }

    @Override
    public void run() {

        double time=1_000_000_000.0/FPS;
        double time2=1_000_000_000.0/UPS;

        long priv=System.nanoTime();

        int frames=0;
        int upd=0;
        long check=System.currentTimeMillis();

        double u=0;
        double f=0;

        while(true){

            long curent = System.nanoTime();

            u+=(curent - priv) / time2;
            f+=(curent - priv) / time;
            priv=curent;

            if(u>=1)
            {
                update();
                upd++;
                u--;
            }

            if(f>=1)
            {
                gp.repaint();
                f--;
                frames++;
            }


            if(System.currentTimeMillis() - check >= 1000){
                check = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "  UPS: "+ upd);
                frames=0;
                upd=0;
            }

        }
    }


    public void windowFocustLost(){
        if(Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDBoolean();
    }

    public Menu getMenu(){
    return menu;
    }

    public Playing getPlaying(){
        return playing;
    }

    public AudioOpt getAudioOpt(){
        return audioOpt;
    }

    public GOptions getOptions(){
        return options;
    }

    public AudioPlayer getAudioPlayer(){
        return audioPlayer;
    }
}
