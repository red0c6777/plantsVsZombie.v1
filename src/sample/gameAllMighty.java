package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

//TODO: Implement
public class gameAllMighty implements EventHandler<KeyEvent>, Serializable {

    final private double FRAMES_PER_SECOND = 60.0;

    ArrayList<plant> plantArrayList;
    ArrayList<zombie> zombieArrayList;
    ArrayList<pea> peaArrayList;
    ArrayList<peaShooter> peaShooterArrayList;
    ArrayList<sunFlower> sunFlowerArrayList;
    ArrayList<potatoMine> potatoMineArrayList;
    ArrayList<lawnmower> lawnmowerArrayList;
    HashMap <zombie,plant> eatingZombieArrayList;
    transient Button nextLevelButton;
    transient Button mainMenuButton;
    transient Button retryButton;
    static int[] timeElapsedSinceBuying; //0=sunflower, 1=peashooter, 2=wallnut, 3=potatomine
    static ArrayList<sun> sunArrayList = new ArrayList<>();
    transient private Timer timer;
    private double numberOfZombiesLeft;
    private double totalNumberOfZombies;
    transient Stage primaryStage;
    transient Pane primaryPane;
    transient Scene mainMenuScene;
    int secondsPassed;
    int level;
    transient Text clock;
    transient StackPane clockPane;
    transient ProgressBar progressBar;
    transient StackPane pbPane;
    transient Popup gameWonPopUp;
    transient Popup gameLostPopUp;
    static boolean paused;
    int sunCount;
    boolean gameLost;
    boolean gameWin;

    public gameAllMighty(Stage ps,Pane pp,Scene mms,ArrayList<plant> pl, ArrayList<zombie> zo,ArrayList<lawnmower> lw,ArrayList<peaShooter> psal,ArrayList<sunFlower> sfal,ArrayList<potatoMine> pmal,double nozl,int l,int sc){
        primaryStage = ps;
        sunCount = sc;
        primaryPane = pp;
        mainMenuScene = mms;
        plantArrayList = pl;
        zombieArrayList = zo;
        peaArrayList = new ArrayList<>();
        peaShooterArrayList = psal;
        sunFlowerArrayList = sfal;
        potatoMineArrayList = pmal;
        lawnmowerArrayList = lw;
        level = l;
        gameWonPopUp = new Popup();
        gameLostPopUp = new Popup();
        eatingZombieArrayList = new HashMap<>();
        timeElapsedSinceBuying = new int[4];
        for(int i=0;i<4;i++){
            timeElapsedSinceBuying[i] = 0;
        }
        numberOfZombiesLeft = nozl;
        totalNumberOfZombies = nozl;
        paused = false;
        gameLost = false;
        gameWin = false;
        mainMenuButton = new Button();
        nextLevelButton = new Button();
        retryButton = new Button();

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
                        if(!paused) {
                            Iterator it = eatingZombieArrayList.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                plant pl = (plant) pair.getValue();
                                pl.setHealth(pl.getHealth() - 20);
                                System.out.println("plant heath: " + pl.getHealth());
                                if (pl.getHealth() <= 0) {  //if dead plant, do the following
                                    pl.dead(primaryPane);
                                    plantArrayList.remove(pl);
                                    eatingZombieArrayList.remove(pair.getKey());
                                }
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
                            if(!paused)
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
                        if(!paused)
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
                            if(!paused)
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
        if(!paused) {
            updateAnimation();
            checkingFight();

            for (int i = 0; i < 4; i++) { //timing the time elapsed since the plant was last bought
                timeElapsedSinceBuying[i] += 1;
            }
        }
    }

    private void updateAnimation() throws FileNotFoundException {
        peaStepper();
        for(zombie z : zombieArrayList){
            if(!eatingZombieArrayList.containsKey(z))
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

                for(plant pl: plantArrayList) {
                    if (z.getRow() == pl.getRow() && z.getPosX() - pl.getPosX() <= 60) { //checking if zombie is in vicinity of the plant to be eating it
                        if(!eatingZombieArrayList.containsKey(z)) {
                            System.out.println("Zombie eating plant!");
                            eatingZombieArrayList.put(z, pl);
                        }
                    }
                }

                //checking if the zombie is near the potatomine

                ArrayList<potatoMine>  potatoMineToRemove = new ArrayList<>();
                for(potatoMine pm: potatoMineArrayList){
                    if(z.getRow() == pm.getRow() && z.getPosX() - pm.getPosX() <= 60){
                        for(zombie ztbb:zombieArrayList){
                            if(pm.getPosX()-150 <= ztbb.getPosX() && ztbb.getPosX() <= pm.getPosX()+150 && pm.getPosY()-150 <= ztbb.getPosY() && ztbb.getPosY() <= pm.getPosY()+150){
                                zombiesToRemove.add(ztbb);
                            }
                        }
                        pm.dead(primaryPane);
                        potatoMineToRemove.add(pm);
                        plantArrayList.remove(pm);
                    }
                }

                //removing potatomine which were used:
                for(potatoMine pmtr: potatoMineToRemove)
                    potatoMineArrayList.remove(pmtr);

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
                    if (lw.getRow() == zombieRow){
                        if(lw.alreadyUnleashed)
                            gameLost = true;
                        else {
                            lw.alreadyUnleashed = true;
                            lw.unleashLawnmower(primaryPane);
                            //killing the zombies in the row of the lawnmower
                            for(zombie ztk: zombieArrayList){
                                if(ztk.getRow() == lw.getRow() && ztk.getPosX()<= 1280){
                                    zombiesToRemove.add(ztk);
                                }
                            }
                        }
                    }
                }
            }
        }
        //removing zombies:
        for (zombie ztr: zombiesToRemove){
            numberOfZombiesLeft-=1;
            if(numberOfZombiesLeft <= 0){
                gameWin = true;
            }
            progressBar.setProgress(1.0 - numberOfZombiesLeft/totalNumberOfZombies);
            ztr.dead(primaryPane);
            zombieArrayList.remove(ztr);
            if(eatingZombieArrayList.containsKey(ztr))
                eatingZombieArrayList.remove(ztr);
        }
        zombiesToRemove.clear();


        if(gameLost) {
            gameLost = false;
            System.out.println("Game lost!");
            VBox gameLostButtons = new VBox();
            Text lostMessage = new Text("You lost this round!");
            retryButton = new Button("Retry Level");
            mainMenuButton = new Button("Main Menu");
            gameLostButtons.getChildren().addAll(retryButton, mainMenuButton);
            gameLostPopUp.getContent().addAll(lostMessage,gameLostButtons);
            paused = true;
            gameLostPopUp.show(primaryStage);
            timer.cancel();
        }

        if(gameWin){
            gameWin = false;
            System.out.println("You won this round!");
            VBox gameWonButtons = new VBox();
            Text winnerMessage = new Text();
            switch(level){
                case 1:
                    winnerMessage.setText("Congratulations! You have unlocked WallNut!");
                    break;
                case 2:
                    winnerMessage.setText("Congratulations! You have unlocked Potato Mine!");
                    break;
                default:
                    winnerMessage.setText("Congratulations! You have won this round!");
            }
            nextLevelButton = new Button("Next Level");
            mainMenuButton = new Button("Main Menu");
            gameWonButtons.getChildren().addAll(nextLevelButton,mainMenuButton);
            gameWonPopUp.getContent().addAll(winnerMessage,gameWonButtons);
            paused = true;
            gameWonPopUp.show(primaryStage);
            timer.cancel();
        }
        mainMenuButton.setOnAction(actionEvent -> {
            try {
                gameWonPopUp.hide();
                primaryStage.setScene(mainMenuScene);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not go to main menu!");
            }
        });

        nextLevelButton.setOnAction(actionEvent -> {
            gameLevel nextGameLevel = new gameLevel(level+1,mainMenuScene);
            try {
                gameWonPopUp.hide();
                nextGameLevel.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot start level game!");
            }
        });

        retryButton.setOnAction(actionEvent -> {
            gameLevel retryGameLevel = new gameLevel(level,mainMenuScene);
            try {
                gameLostPopUp.hide();
                retryGameLevel.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot retry level game!");
            }
        });
    }

    void updateClock(){

        if(!paused) {
            secondsPassed += 1;
            //System.out.println("Seconds passed: "+secondsPassed);
            clock.setText("Time: " + Integer.toString(secondsPassed));
        }
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

    public static void setPaused(boolean p) {
        paused = p;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public double getFRAMES_PER_SECOND() {
        return FRAMES_PER_SECOND;
    }

    public ArrayList<plant> getPlantArrayList() {
        return plantArrayList;
    }

    public ArrayList<zombie> getZombieArrayList() {
        return zombieArrayList;
    }

    public ArrayList<pea> getPeaArrayList() {
        return peaArrayList;
    }

    public ArrayList<peaShooter> getPeaShooterArrayList() {
        return peaShooterArrayList;
    }

    public ArrayList<sunFlower> getSunFlowerArrayList() {
        return sunFlowerArrayList;
    }

    public ArrayList<potatoMine> getPotatoMineArrayList() {
        return potatoMineArrayList;
    }

    public ArrayList<lawnmower> getLawnmowerArrayList() {
        return lawnmowerArrayList;
    }

    public HashMap<zombie, plant> getEatingZombieArrayList() {
        return eatingZombieArrayList;
    }

    public Button getNextLevelButton() {
        return nextLevelButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public Button getRetryButton() {
        return retryButton;
    }

    public static int[] getTimeElapsedSinceBuying() {
        return timeElapsedSinceBuying;
    }

    public static ArrayList<sun> getSunArrayList() {
        return sunArrayList;
    }

    public Timer getTimer() {
        return timer;
    }

    public double getNumberOfZombiesLeft() {
        return numberOfZombiesLeft;
    }

    public double getTotalNumberOfZombies() {
        return totalNumberOfZombies;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Pane getPrimaryPane() {
        return primaryPane;
    }

    public Scene getMainMenuScene() {
        return mainMenuScene;
    }

    public int getLevel() {
        return level;
    }

    public Text getClock() {
        return clock;
    }

    public StackPane getClockPane() {
        return clockPane;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public StackPane getPbPane() {
        return pbPane;
    }

    public Popup getGameWonPopUp() {
        return gameWonPopUp;
    }

    public Popup getGameLostPopUp() {
        return gameLostPopUp;
    }

    public static boolean isPaused() {
        return paused;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public boolean isGameWin() {
        return gameWin;
    }

    public int getSunCount() {
        return sunCount;
    }
}

/*
class  singleton{
    private  static singleton sinlgeinsatance = null;
    private  int
}
 */
