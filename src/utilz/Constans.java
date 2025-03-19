package utilz;

import Main.Game;

public class Constans {

    public static final float  GRAVITY = 0.04f * Game.SCALE;
    public static final int ANIMATION_SPEED = 25;

    public static class ObjCst{
        public static final int BARREL = 0;
        public static final int RED_POTION = 1;
        public static final int SPIKE = 2;

        public static final int RED_POTION_VALUE = 15;

        public static final int BARREL_W_D = 64;
        public static final int BARREL_H_D = 37;
        public static final int BARREL_W = (int)(Game.SCALE * 100);
        public static final int BARREL_H = (int)(Game.SCALE * Game.SCALE * BARREL_H_D);

        public static final int POTION_W_D = 12;
        public static final int POTION_H_D = 16;
        public static final int POTION_W = (int) (Game.SCALE * POTION_W_D);
        public static final int POTION_H = (int) (Game.SCALE * POTION_H_D);

        public static final int SPIKE_W_D = 32;
        public static final int SPIKE_H_D = 23;
        public static final int SPIKE_W = (int) (Game.SCALE * SPIKE_W_D);
        public static final int SPIKE_H = (int) (Game.SCALE * SPIKE_H_D);

        public static int getSpriteAmount(int objT){
            switch (objT){
                case BARREL -> {
                    return 7;
                }
                case RED_POTION -> {
                    return 7;
                }
            }
            return 1;
        }
    }


    public static class EnemyC{
        public static final int DUMMY = 0;
        public static final int MINOTAUR = 1;
        public static final int ASSASSIN = 2;
        public static final int MAGE = 3;

        public static final int IDLE = 0;
        public static final int HIT = 1;
        public static final int MOVING = 2;
        public static final int ATTACK = 3;
        public static final int DIE = 4;

        public static final int DUMMY_W_D = 32;
        public static final int DUMMY_H_D = 32;
        public static final int DUMMY_W = (int)(64 * Game.SCALE);
        public static final int DUMMY_H = (int)(64 * Game.SCALE);

        public static final int MINOTAUR_W_D = 32;
        public static final int MINOTAUR_H_D = 32;
        public static final int MINOTAUR_W = (int)(64 * Game.SCALE);
        public static final int MINOTAUR_H = (int)(64 * Game.SCALE);

        public static final int ASSASSIN_W_D = 32;
        public static final int ASSASSIN_H_D = 32;
        public static final int ASSASSIN_W = (int)(64 * Game.SCALE);
        public static final int ASSASSH_H = (int)(64 * Game.SCALE);

        public static final int MAGE_W_D = 32;
        public static final int MAGE_H_D = 32;
        public static final int MAGE_W = (int)(64 * Game.SCALE);
        public static final int MAGE_H = (int)(64 * Game.SCALE);

        public static final int DUMMY_DO_X = (int)(9 * 3);
        public static final int DUMMY_DO_Y = (int)(1 * 3);

        public static int getSpriteAmount(int enemyT, int enemyS){
            switch (enemyT){
                case DUMMY:
                case MINOTAUR:
                case ASSASSIN:
                case MAGE:
                    switch (enemyS){
                        case IDLE:
                        case ATTACK:
                        case DIE:
                        case HIT:
                        case MOVING:
                            return 9;

                    }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemyT){
            switch (enemyT){
                case DUMMY:
                    return 10;
                case MAGE:
                case MINOTAUR:
                    return 20;
                case ASSASSIN:
                    return 100;
                default:
                    return 1;

            }
        }

        public static int GetEnemyDMG(int enemyT){
            switch (enemyT){
                case DUMMY:
                    return 15;
                case MAGE:
                case MINOTAUR:
                    return 30;
                case ASSASSIN:
                    return 50;
                default:
                    return 0;

            }
        }
    }

    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_D = 140;
            public static final int B_HEIGHT_D = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_D * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_D * Game.SCALE);
        }
        public static class PButtons{
            public static final int S_SIZE_D = 42;
            public static final int S_SIZE = (int) (S_SIZE_D * Game.SCALE);
        }

        public static class URMButtons{
            public static final int URM_SIZE_D = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_D * Game.SCALE);
        }

        public static class VButtons{
            public static final int VOL_W_D = 28;
            public static final int VOL_H_D = 44;
            public static final int SL_W_D = 215;

            public static final int VOL_W = (int) (VOL_W_D * Game.SCALE);
            public static final int VOL_H = (int) (VOL_H_D * Game.SCALE);
            public static final int SL_W = (int) (SL_W_D * Game.SCALE);
        }
    }

    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;
    }

    public static class Player{

        public static final int IDLE=0;
        public static final int HIT=1;
        public static final int RUN=2;
        public static final int ATTACK=3;
        public static final int DIE=4;

        public static int GetSprite(int action){
            switch (action){
                case RUN:
                case IDLE:
                case HIT:
                case ATTACK:
                case DIE:
                    return 10;
                default:
                    return 1;
            }
        }
    }
}
