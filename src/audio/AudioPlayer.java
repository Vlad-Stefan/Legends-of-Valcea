package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AudioPlayer {

    public static int MENU_1 = 0;
    public static int LVL_1 = 1;
    public static int LVL_2 = 2;
    public static int LVL_3 = 3;

    public static int DIE = 0;
    public static int GAMEO = 1;
    public static int ATTACK_1 = 2;
    public static int ATTACK_2 = 3;
    public static int ATTACK_3 = 4;

    private Clip[] songs, effect;
    private int currentSongI;
    private float vol = 0.5f;
    private boolean songM, effectM;
    private Random rand = new Random();

    public AudioPlayer(){
        loadSong();
        loadEffect();
        playSong(MENU_1);
    }

    private void loadSong(){
        String[] names = {"Main Menu", "Lvl1", "Lvl2", "Lvl3"};
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++){
            songs[i] = getClip(names[i]);
        }

    }

    private void loadEffect(){
        String[] effectNames = {"Minecraft Death Sound Effect", "Game over sound effect", "attack1", "attack2", "attack3"};
        effect = new Clip[effectNames.length];
        for (int i = 0; i < effect.length; i++){
            effect[i] = getClip(effectNames[i]);
        }
        updateEffectVol();
    }

    private Clip getClip(String name){
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audio;

        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setVol(float vol){
        this.vol = vol;
        updateSongVol();
        updateEffectVol();
    }

    public void stopSong(){
        if(songs[currentSongI].isActive())
            songs[currentSongI].stop();
    }

    public void setLvlS(int lvl){
        if(lvl == 0)
            playSong(LVL_1);
        else if(lvl == 1)
            playSong(LVL_2);
        else playSong(LVL_3);
    }


    public void toggleSM(){
        this.songM = !songM;
        for(Clip c : songs){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songM);
        }
    }


    public void toggleEM(){
        this.effectM = !effectM;
        for(Clip c : effect){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectM);
        }
        if(!effectM)
            playEffect(DIE);
    }

    public void playAttackSound(){
        int start = 2;
        start += rand.nextInt(3);
        playEffect(start);
    }

    public void playEffect(int e) {
        effect[e].setMicrosecondPosition(0);
        effect[e].start();
    }

    public void playSong(int song){
        stopSong();

        currentSongI = song;
        updateSongVol();
        songs[currentSongI].setMicrosecondPosition(0);
        songs[currentSongI].loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void updateSongVol(){
        FloatControl gainCtrl = (FloatControl) songs[currentSongI].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainCtrl.getMaximum() - gainCtrl.getMinimum();
        float gain = (range * vol) + gainCtrl.getMinimum();
        gainCtrl.setValue(gain);
    }

    private void updateEffectVol(){
        for(Clip c : effect){
            FloatControl gainCtrl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainCtrl.getMaximum() - gainCtrl.getMinimum();
            float gain = (range * vol) + gainCtrl.getMinimum();
            gainCtrl.setValue(gain);
        }
    }
}
