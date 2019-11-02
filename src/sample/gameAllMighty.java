package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class gameAllMighty implements EventHandler<KeyEvent> {

    final private double FRAMES_PER_SECOND = 60.0;

    ArrayList<plant> plantArrayList;
    ArrayList<zombie> zombieArrayList;
    ArrayList<pea> peaArrayList;
    private Timer timer;

    public gameAllMighty(ArrayList<plant> pl, ArrayList<zombie> zo,ArrayList<pea> pe){
        plantArrayList = pl;
        zombieArrayList = zo;
        peaArrayList = pe;
    }

    public void initialize(){
        this.startTimer();
    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateGame();
                    }
                });
            }
        };

        long frameRate = (long) 1; //1 milliseconds
        this.timer.schedule(timerTask,0,frameRate);
    }

    private void updateGame(){
        ArrayList<plant> plantArrayList = new ArrayList<>();
        ArrayList<zombie> zombieArrayList = new ArrayList<>();
        ArrayList<pea> peaArrayList = new ArrayList<pea>();

        //checkingFight();
        updateAnimation();
    }

    private void updateAnimation() {
        for (pea p : peaArrayList){
            if(p.posX > 1280){
                p.removePea();
            }
            p.step();
        }
        for(zombie z : zombieArrayList){
            z.step();
        }
    }

    void checkingFight(){

    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
}
