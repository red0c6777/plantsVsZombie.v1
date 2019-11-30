package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
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
import java.util.*;
import java.util.regex.Pattern;

//TODO : Add zombie biting
//TODO: making the throwing not simultaneous for all plants
//TODO: pause the game


public class gameAllMighty implements EventHandler<KeyEvent> {

    final private double FRAMES_PER_SECOND = 60.0;

    ArrayList<plant> plantArrayList;
    ArrayList<zombie> zombieArrayList;
    ArrayList<pea> peaArrayList;
    ArrayList<peaShooter> peaShooterArrayList;
    ArrayList<sunFlower> sunFlowerArrayList;
    ArrayList<lawnmower> lawnmowerArrayList;
    HashMap <zombie,plant> eatingZombieArrayList;
    static int[] timeElapsedSinceBuying; //0=sunflower, 1=peashooter, 2=wallnut, 3=potatomine
    static ArrayList<sun> sunArrayList = new ArrayList<>();
    private Timer timer;
    private double numberOfZombiesLeft;
    private double totalNumberOfZombies;
    Pane primaryPane;
    int secondsPassed;
    Text clock;
    StackPane clockPane;
    ProgressBar progressBar;
    StackPane pbPane;
    boolean gameLost;
    boolean gameWin;

    public gameAllMighty(Pane pp,ArrayList<plant> pl, ArrayList<zombie> zo,ArrayList<lawnmower> lw,ArrayList<peaShooter> psal,ArrayList<sunFlower> sfal,int nozl){
        primaryPane = pp;
        plantArrayList = pl;
        zombieArrayList = zo;
        peaArrayList = new ArrayList<>();
        peaShooterArrayList = psal;
        sunFlowerArrayList = sfal;
        lawnmowerArrayList = lw;
        eatingZombieArrayList = new HashMap<>();
        timeElapsedSinceBuying = new int[4];
        for(int i=0;i<4;i++){
            timeElapsedSinceBuying[i] = 0;
        }
        numberOfZombiesLeft = nozl;
        totalNumberOfZombies = nozl;
        gameLost = false;
        gameWin = false;

        //initializing progress bar:
        progressBar = new ProgressBar();
        pbPane = new StackPane(progressBar);
        progressBar.setStyle("-fx-accent: green");
        progressBar.setProgress(0);
        pbPane.setLayoutX(240);
        pbPane.setLayoutY(25);
        primaryPane.getChildren().add(pbPane);

    }

    protected static void removeSun(sun s) {
        sunArrayList.remove(s);
    }

    public void initialize() throws FileNotFoundException {
        this.startTimer();
        this.startSunSpawner();
        this.startSunFlowerSunSpawner();
        this.startPeaSpawner();
        this.startPlantEating();
        this.startClock();
    }

    public void startPlantEating(){
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Iterator it = eatingZombieArrayList.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            plant pl = (plant) pair.getValue();
                            pl.setHealth(pl.getHealth() - 20);
                            System.out.println("plant heath: "+pl.getHealth());
                            if(pl.getHealth() <=0){  //if dead plant, do the following
                                pl.dead(primaryPane);
                                plantArrayList.remove(pl);
                                eatingZombieArrayList.remove(pair.getKey());
                            }
                        }
                    }
                });
            }
        };
        long frameRate = (long) 1000; //1 second
        this.timer.schedule(timerTask,0,frameRate);
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
        long frameRate = (long) 3000; //3 seconds
        this.timer.schedule(timerTask,0,frameRate);
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

    private void startSunFlowerSunSpawner(){
        Random rand = new Random();
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (sunFlower sf: sunFlowerArrayList){
                            double px = sf.getPosX() + rand.nextInt(20);
                            double py = sf.getPosY() +rand.nextInt(20);
                            try {
                                sunArrayList.add(sun.sunFlowerSunSpawner(primaryPane,px,py));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
        long frameRate = (long) 10000; //10 seconds
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

        for(int i=0;i<4;i++){ //timing the time elapsed since the plant was last bought
            timeElapsedSinceBuying[i]+=1;
        }


    }

    private void updateAnimation() throws FileNotFoundException {
        peaStepper();
        progressBarUpdate();
        for(zombie z : zombieArrayList){
            if(!eatingZombieArrayList.containsKey(z))
                z.step();
        }
    }

    private void progressBarUpdate() {

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
                        if(z.health <= 0) {
                            numberOfZombiesLeft-=1;
                            progressBar.setProgress(1.0 - numberOfZombiesLeft/totalNumberOfZombies);
                            zombiesToRemove.add(z);
                        }
                    }
                }

                for(plant pl: plantArrayList) {
                    if (z.getRow() == pl.getRow() && z.getPosX() - pl.getPosX() <= 60) { //checking if zombie is in vicinity of the plant to be eating it
                        if(!eatingZombieArrayList.containsKey(z)) {
                            System.out.println("Zombie eating plant!");
                            eatingZombieArrayList.put(z, pl);
                        }
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
            if(eatingZombieArrayList.containsKey(ztr))
                eatingZombieArrayList.remove(ztr);
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

    public static int getTimeElapsedSinceLastBought(int i){
        return(timeElapsedSinceBuying[i]);
    }

    public static void resetTimeElapsedSinceLastBought(int i){
        timeElapsedSinceBuying[i] = 0;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
}