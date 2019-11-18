package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class gameAllMighty implements EventHandler<KeyEvent> {

    final private double FRAMES_PER_SECOND = 60.0;

    ArrayList<plant> plantArrayList;
    ArrayList<zombie> zombieArrayList;
    ArrayList<pea> peaArrayList;
    ArrayList<plant> peaShooterArrayList;
    ArrayList<lawnmower> lawnmowerArrayList;
    static ArrayList<sun> sunArrayList = new ArrayList<>();
    private Timer timer;
    Pane primaryPane;
    int secondsPassed;
    Text clock;
    StackPane clockPane;
    boolean gameLost;
    boolean gameWin;

    public gameAllMighty(Pane pp,ArrayList<plant> pl, ArrayList<zombie> zo,ArrayList<lawnmower> lw,ArrayList<plant> psal){
        primaryPane = pp;
        plantArrayList = pl;
        zombieArrayList = zo;
        peaArrayList = new ArrayList<>();
        peaShooterArrayList = psal;
        lawnmowerArrayList = lw;
        gameLost = false;
        gameWin = false;
    }

    protected static void removeSun(sun s) {
        sunArrayList.remove(s);
    }

    public void initialize() throws FileNotFoundException {
        this.startTimer();
        this.startSunSpawner();
        this.startPeaSpawner();
        this.startClock();
    }

    private void startPeaSpawner() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            peaSpawner();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        long sunTimer = (long) 3000; //3 seconds
        this.timer.schedule(timerTask,10,sunTimer);
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
                        try {
                            updateGame();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
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
        long sunTimer = (long) 10000; //10 seconds
        this.timer.schedule(timerTask,10,sunTimer);
    }

    private void updateGame() throws FileNotFoundException {
        //checkingFight();
        updateAnimation();
        checkingFight();
    }

    private void updateAnimation() throws FileNotFoundException {
        peaStepper();
        for(zombie z : zombieArrayList){
            z.step();
        }
    }

    private void peaSpawner() throws FileNotFoundException {
        for(plant ps: peaShooterArrayList) {
            pea newPea = new pea(ps.getPosX()+80, ps.getPosY()+30);
            peaArrayList.add(newPea);
            newPea.addPeaToLawn(primaryPane);
        }
    }

    private void peaStepper() throws FileNotFoundException {
        ArrayList<pea> peaToRemove = new ArrayList<>();
        for (pea p : peaArrayList){
            if(p.getPosX() < 1280){
               p.step();
            }
            else
                peaToRemove.add(p);
        }
        for(pea ptr: peaToRemove){
            ptr.removePea(primaryPane);
            peaArrayList.remove(ptr);
        }
        peaToRemove.clear();
    }

    void checkingFight(){  //TODO : Stopping lawnmover when out of screen

        ArrayList<pea> peasToRemove = new ArrayList();
        ArrayList<zombie> zombiesToRemove = new ArrayList();
        //Checking if zombie is hit by pea or crossed the lawn
        for(zombie z : zombieArrayList){
            if(z.getPosX() < 1219){ //checking if peas hit he zombies after appearing on the lawn
                for(pea p: peaArrayList){
                    if((z.getPosX() <= p.getPosX() && p.getPosX() <= z.getPosX()+114) && (z.getPosY() <= p.getPosY() && p.getPosY() <= z.getPosY()+144)){
                        System.out.println("Pea hit zombie");
                        //adding the pea to remove list
                        peasToRemove.add(p);
                        z.damage(p.getDamage());
                        if(z.health <= 0)
                            zombiesToRemove.add(z);
                    }
                }
                //removing the peas which were used:
                for (pea ptr: peasToRemove){
                    ptr.removePea(primaryPane);
                    peaArrayList.remove(ptr);
                }
                peasToRemove.clear();

            }
            if(z.getPosX() <= 280){
                int zombieRow = z.getRow();
                for (lawnmower lw : lawnmowerArrayList){
                    if (lw.getRow() == zombieRow && !(lw.alreadyUnleashed)){
                        if(lw.alreadyUnleashed)
                            gameLost = true;
                        else {
                            lw.alreadyUnleashed = true;
                            lw.unleashLawnmower(primaryPane);
                        }
                    }
                }
            }
        }
        //removing zombies:
        for (zombie ztr: zombiesToRemove){
            ztr.dead(primaryPane);
            zombieArrayList.remove(ztr);
        }
        zombiesToRemove.clear();


        if(gameLost){
            System.out.println("Game lost!");
        }
        if(gameWin){
            System.out.println("You won this round!");
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
