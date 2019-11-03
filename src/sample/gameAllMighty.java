package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class gameAllMighty implements EventHandler<KeyEvent> {

    final private double FRAMES_PER_SECOND = 60.0;

    ArrayList<plant> plantArrayList;
    ArrayList<zombie> zombieArrayList;
    ArrayList<pea> peaArrayList;
    ArrayList<lawnmower> lawnmowerArrayList;
    static ArrayList<sun> sunArrayList = new ArrayList<>();
    private Timer timer;
    Pane primaryPane;
    int secondsPassed;
    Text clock;
    StackPane clockPane;

    public gameAllMighty(Pane pp,ArrayList<plant> pl, ArrayList<zombie> zo,ArrayList<pea> pe,ArrayList<lawnmower> lw){
        primaryPane = pp;
        plantArrayList = pl;
        zombieArrayList = zo;
        peaArrayList = pe;
        lawnmowerArrayList = lw;
    }

    protected static void removeSun(sun s) {
        sunArrayList.remove(s);
    }

    public void initialize() throws FileNotFoundException {
        this.startTimer();
        this.startSunSpawner();
        this.startClock();
    }

    private void startClock() throws FileNotFoundException {
        secondsPassed = 0;
        clock = new Text();
        clockPane = new StackPane();
        clock.setText("Time: "+Integer.toString(secondsPassed));
        clock.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        clockPane.setLayoutX(120);
        clockPane.setLayoutY(20);
        ImageView cbg = new ImageView(new Image(new FileInputStream("res\\images\\clockBG.png")));
        cbg.setPreserveRatio(true);
        cbg.setFitWidth(100);
        clockPane.getChildren().addAll(cbg,clock);
        primaryPane.getChildren().addAll(clockPane);
        //clockPane.setAlignment(Pos.CENTER);
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateClock();
                    }
                });
            }
        };
        long clockRate = (long) 1000; //1 second
        this.timer.schedule(timerTask,0,clockRate);
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

    public void startSunSpawner(){
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sunArrayList.add(sun.sunSpawner(primaryPane));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        long sunTimer = (long) 3000; //10 seconds
        this.timer.schedule(timerTask,10,sunTimer);
    }

    private void updateGame(){
        //checkingFight();
        updateAnimation();
        checkingFight();
    }

    private void updateAnimation() {
        throwPea();
        for(zombie z : zombieArrayList){
            z.step();
        }
    }

    private void throwPea() {
        for (pea p : peaArrayList){
            if(p.getPosX() > 1280 + p.getSourceX()){
                p.setPosX(p.getSourceX());
            }
            p.step();
        }
    }

    void checkingFight(){  //TODO : Stopping lawnmover when out of screen
        for(zombie z : zombieArrayList){
            if(z.getPosX() <= 280){
                int zombieRow = z.getRow();
                for (lawnmower lw : lawnmowerArrayList){
                    if (lw.getRow() == zombieRow && !(lw.alreadyUnleashed)){
                        lw.alreadyUnleashed = true;
                        lw.unleashLawnmower(primaryPane);
                    }
                }
            }
        }
    }

    void updateClock(){
        secondsPassed+=1;
        //System.out.println("Seconds passed: "+secondsPassed);
        clock.setText("Time: "+Integer.toString(secondsPassed));
    }

    protected static ArrayList<sun> getSunList(){
        return sunArrayList;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
}
