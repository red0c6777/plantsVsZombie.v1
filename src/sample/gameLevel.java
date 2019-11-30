package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/*

@author - Harsh

*/

public class gameLevel extends Application {

    private Stage primaryStage;
    private Pane quickPlayPane;
    private Scene mainMenuScene;
    private int level;
    private  ArrayList<peaShooter> peaShooterArrayList;
    private ArrayList<sunFlower> sunFlowerArrayList;
    private ArrayList<wallNut> wallNutArrayList;
    private ArrayList<potatoMine> potatoMineArrayList;
    private ArrayList<zombie> zombiesList;
    private ArrayList<plant> plantList;
    private ArrayList<lawnmower> lawnmowerArrayList;
    private int sunCount;
    private gameAllMighty gameController;
    private Text sunLabel = new Text();
    private double numberOfZombiesLeft;

    public gameLevel(int l,Scene mms){
        this.level = l;
        mainMenuScene = mms;
    }

    void setLevel(int l){
        level = l;
    }

    @Override
    public void start(Stage pp) throws Exception {

        System.out.println("Starting the level!");
        primaryStage = pp;
        plantList = new ArrayList<>();
        zombiesList = new ArrayList<>();
        peaShooterArrayList = new ArrayList<>();
        sunFlowerArrayList = new ArrayList<>();
        potatoMineArrayList = new ArrayList<>();
        wallNutArrayList = new ArrayList<>();
        lawnmowerArrayList = new ArrayList<>();
        numberOfZombiesLeft = level * 3;
        sunCount = 500;
        resume(primaryStage,gameController,false);
    }

    public void resume(Stage ps,gameAllMighty resumedController,boolean isResumed) throws FileNotFoundException {

        //Creating glow effect for button glowing when cursor is hovered on them
        if(isResumed){
            this.primaryStage = ps;
            this.level = resumedController.getLevel();

            this.plantList = new ArrayList<>();
            for(plant pl: resumedController.getPlantArrayList()){
                System.out.println(pl.getPlantType());
                System.out.println(pl.getRow());
                System.out.println(pl.getColumn());
                System.out.println(pl.getPosX());
                System.out.println(pl.getPosY());
                this.plantList.add(new plant(pl.getPlantType(),pl.getRow(),pl.getColumn(),pl.getPosX(),pl.getPosY()));
            }

            this.zombiesList = new ArrayList<>();
            for(zombie z: resumedController.getZombieArrayList()){
                zombie resumedZombie = new zombie();
                resumedZombie.setZombiePane(new StackPane());
                resumedZombie.setHealth(z.getHealth());
                resumedZombie.setImage(z.getImage());
                resumedZombie.setImageview(z.getImageview());
                resumedZombie.setInitialPosX(z.getInitialPosX());
                resumedZombie.setInitialPosY(z.getInitialPosY());
                resumedZombie.setPosX(z.getPosX());
                resumedZombie.setRow(z.getRow());
                resumedZombie.setSpeed(z.getSpeed());
                resumedZombie.setType(z.getType());
                resumedZombie.setZombiePane(z.getZombiePane());
                this.zombiesList.add(resumedZombie);
            }
            peaShooterArrayList = new ArrayList<>();
            for(peaShooter psr: resumedController.getPeaShooterArrayList()){
                this.peaShooterArrayList.add(new peaShooter(psr.getRow(),psr.getColumn(),psr.getPosX(),psr.getPosY()));
            }

            this.sunFlowerArrayList = resumedController.getSunFlowerArrayList();
            this.potatoMineArrayList = resumedController.getPotatoMineArrayList();
            this.lawnmowerArrayList = resumedController.getLawnmowerArrayList();
            this.numberOfZombiesLeft = resumedController.getNumberOfZombiesLeft();
            this.sunCount = resumedController.getSunCount();
        }
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);

        DropShadow plantGlow = new DropShadow();
        plantGlow.setOffsetY(0f);
        plantGlow.setOffsetX(0f);
        plantGlow.setColor(Color.RED);
        plantGlow.setWidth(150);
        plantGlow.setHeight(150);

        //Button images and image Views

        Image pauseButtonImage = new Image(new FileInputStream("res\\images\\button\\pauseButton.png"));
        ImageView pauseButtonImageView = new ImageView(pauseButtonImage);
        pauseButtonImageView.setPreserveRatio(true);
        pauseButtonImageView.setFitWidth(100);
        Image resumeButtonImage = new Image(new FileInputStream("res\\images\\button\\resumeButton.png"));
        ImageView resumeButtonImageView = new ImageView(resumeButtonImage);
        Image exitButtonImage = new Image(new FileInputStream("res\\images\\button\\exitButtonPauseMenu.png"));
        ImageView exitButtonImageView = new ImageView(exitButtonImage);
        Image saveGameButtonImage = new Image(new FileInputStream("res\\images\\button\\saveGameButton.png"));
        ImageView saveGameButtonImageView = new ImageView(saveGameButtonImage);
        resumeButtonImageView.setPreserveRatio((true));
        resumeButtonImageView.setFitWidth(150);
        exitButtonImageView.setPreserveRatio(true);
        exitButtonImageView.setFitWidth(150);
        saveGameButtonImageView.setPreserveRatio(true);
        saveGameButtonImageView.setFitWidth(150);

        Image lawnImage = new Image(new FileInputStream("res\\images\\lawn\\lawnHD_crop.jpg"), 1280, 720, false, false);
        Image zombieFlyingimage = new Image(new FileInputStream("res\\images\\zombie\\zombieFlying.gif"), 114, 144, false, false);

        //Images and image views of plants
        Image sunflowerImage = new Image(new FileInputStream("res\\images\\plant\\sunflower.png"));
        Image peashooterImage = new Image(new FileInputStream("res\\images\\plant\\peashooter.png"));
        Image wallnutImage = new Image(new FileInputStream("res\\images\\plant\\wallnut.png"));
        Image potatoMineImage = new Image(new FileInputStream("res\\images\\plant\\potatoMine.png"));
        Image peashooterCard = new Image(new FileInputStream("res\\images\\plant\\peashooterCard.png"));
        Image sunflowerCard = new Image(new FileInputStream("res\\images\\plant\\sunflowerCard.png"));
        Image wallnutCard = new Image(new FileInputStream("res\\images\\plant\\wallnutCard.png"));
        Image potatoMineCard = new Image(new FileInputStream("res\\images\\plant\\potatoMineCard.png"));
        Image pauseMenuImage = new Image(new FileInputStream("res\\images\\button\\pauseMenuPicture.png"));
        ImageView peashooterCardImageView = new ImageView(peashooterCard);
        ImageView sunflowerCardImageView = new ImageView(sunflowerCard);
        ImageView wallnutCardImageView = new ImageView(wallnutCard);
        ImageView potatoMineCardImageView = new ImageView(potatoMineCard);
        ImageView pauseMenuImageView = new ImageView(pauseMenuImage);

        Image lawnmowerImage = new Image(new FileInputStream("res\\images\\lawnmower.png"));
        ImageView lawnmowerImageView = new ImageView((lawnmowerImage));

        peashooterCardImageView.setId("peashooter");
        sunflowerCardImageView.setId("sunflower");
        wallnutCardImageView.setId("wallnut");
        potatoMineCardImageView.setId("potatomine");


        pauseMenuImageView.setPreserveRatio(true);
        pauseMenuImageView.setFitWidth(360);

        //-----------pause Button-------------------------------
        Button pauseButton = new Button();
        pauseButton.setGraphic(pauseButtonImageView);
        pauseButton.setLayoutX(1100);
        pauseButton.setLayoutY(10);
        pauseButton.setPadding(new Insets(-5, -5, -5, -5));

        //------Resume Button----------
        Button resumeButton = new Button();
        resumeButton.setGraphic(resumeButtonImageView);
        resumeButton.setPadding(new Insets(-3, -3, -3, -3));

        //-----------------Exit Button--------------------
        Button exitButton = new Button();
        exitButton.setGraphic(exitButtonImageView);
        exitButton.setPadding(new Insets(-3, -3, -3, -3));

        //-----------------Save Game Button--------------------
        Button saveGameButton = new Button();
        saveGameButton.setGraphic(saveGameButtonImageView);
        saveGameButton.setPadding(new Insets(-3, -3, -3, -3));


        //Pause Box for buttons:

        VBox pauseMenuBox = new VBox();
        pauseMenuBox.getChildren().addAll(resumeButton, saveGameButton, exitButton);
        pauseMenuBox.setSpacing(10f);
        pauseMenuBox.setLayoutX(105);
        pauseMenuBox.setLayoutY(250);

        //Pop up pause menu:
        Popup menuPopUp = new Popup();
        menuPopUp.getContent().addAll(pauseMenuImageView, pauseMenuBox);
        menuPopUp.setAutoHide(true);


        //All the event handlers:

        pauseButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> pauseButton.setEffect(borderGlow));
        pauseButton.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> pauseButton.setEffect(null));
        pauseButton.setOnAction(actionEvent -> {
            try {
                System.out.println("pausing game!");
                gameAllMighty.setPaused(true);
                if (!menuPopUp.isShowing())
                    menuPopUp.show(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not popup pause window");
            }
        });

        resumeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> resumeButton.setEffect(borderGlow));
        resumeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> resumeButton.setEffect(null));
        resumeButton.setOnAction(actionEvent -> {
            try {
                gameAllMighty.setPaused(false);
                menuPopUp.hide();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not resume");
            }
        });

        exitButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> exitButton.setEffect(borderGlow));
        exitButton.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> exitButton.setEffect(null));
        exitButton.setOnAction(actionEvent -> {
            try {
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not exit");
            }
        });

        saveGameButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> saveGameButton.setEffect(borderGlow));
        saveGameButton.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> saveGameButton.setEffect(null));
        saveGameButton.setOnAction(actionEvent -> {
            try {
                saveGame();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not exit");
            }
        });


        peashooterCardImageView.setPreserveRatio(true);
        wallnutCardImageView.setPreserveRatio(true);
        sunflowerCardImageView.setPreserveRatio(true);
        potatoMineCardImageView.setPreserveRatio(true);

        int wid = 80;
        peashooterCardImageView.setFitWidth(wid);
        sunflowerCardImageView.setFitWidth(wid);
        wallnutCardImageView.setFitWidth(wid);
        potatoMineCardImageView.setFitWidth(wid);


        Image barImage = new Image(new FileInputStream("res\\images\\bar.png"));
        ImageView barImageView = new ImageView(barImage);
        barImageView.setPreserveRatio(true);
        barImageView.setFitHeight(670);
        barImageView.setLayoutX(10);
        barImageView.setLayoutY(10);

        primaryStage.setMaxHeight(720);
        primaryStage.setMaxWidth(1280);

        ImageView lawnImageView = new ImageView(lawnImage);

        quickPlayPane = new Pane();

        //Plant slots VBox:
        VBox plantSlots = new VBox();
        plantSlots.getChildren().addAll(sunflowerCardImageView, peashooterCardImageView);
        if(level>1) {
            plantSlots.getChildren().add(wallnutCardImageView);
            if(level>2)
                plantSlots.getChildren().add(potatoMineCardImageView);
        }
        plantSlots.setLayoutX(20);
        plantSlots.setLayoutY(150);
        plantSlots.setSpacing(2f);

        double startDragX = 10;
        double startDragY = 60;

        peashooterCardImageView.setOnDragDetected(new draggingPlantController(peashooterCardImageView,peashooterImage));
        sunflowerCardImageView.setOnDragDetected(new draggingPlantController(sunflowerCardImageView,sunflowerImage));
        wallnutCardImageView.setOnDragDetected(new draggingPlantController(wallnutCardImageView,wallnutImage));
        potatoMineCardImageView.setOnDragDetected(new draggingPlantController(potatoMineCardImageView,potatoMineImage));


        quickPlayPane.setOnDragOver(dragEvent -> {
            peashooterCardImageView.setEffect(null);
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            dragEvent.consume();
        });

        quickPlayPane.setOnDragDropped(dragEvent -> {
            double posX;
            double posY;
            int column;
            int row;

            String plantType = dragEvent.getDragboard().getString();
            double dropX = dragEvent.getX();
            double dropY = dragEvent.getY();

            if((dropX>=300 && dropX <= 1219) && (dropY>=78 && dropY <=660)){
                if(dropX <= 397) {
                    posX = 300;
                    column = 1;
                }
                else if(dropX <=496) {
                    posX = 397;
                    column = 2;
                }
                else if(dropX <= 609) {
                    posX = 496;
                    column = 3;
                }
                else if(dropX <= 707) {
                    posX = 609;
                    column = 4;
                }
                else if(dropX <= 815) {
                    posX = 707;
                    column = 5;
                }
                else if(dropX <= 917) {
                    posX = 815;
                    column = 6;
                }
                else if(dropX <= 1020) {
                    posX = 917;
                    column = 7;
                }
                else if(dropX <= 1124) {
                    posX = 1020;
                    column = 8;
                }
                else {
                    posX = 1124;
                    column = 9;
                }
                //checking row:

                if(dropY <=199) {
                    posY = 78;
                    row = 1;
                }
                else if(dropY <= 319) {
                    posY = 199;
                    row = 2;
                }
                else if(dropY <= 440) {
                    posY = 319;
                    row = 3;
                }
                else if(dropY <= 562) {
                    posY = 440;
                    row = 4;
                }
                else {
                    posY = 562;
                    row = 5;
                }

                System.out.println("Planting coord: row: "+row+"  column: "+column);

                int rechargeTime = 0;

                try {
                    switch (plantType){
                        case "peashooter":
                            if(sunCount >= 100 && (gameAllMighty.getTimeElapsedSinceLastBought(1) > rechargeTime)){
                                sunCount-=100;
                                peaShooter newPlant = new peaShooter(row, column, posX, posY);
                                plantList.add(newPlant);
                                newPlant.addPlantToLawn(quickPlayPane);
                                peaShooterArrayList.add(newPlant);
                                gameAllMighty.resetTimeElapsedSinceLastBought(1);
                            }
                            else
                                System.out.println("Not enough sun!");
                            break;
                        case "sunflower":
                            if(sunCount >= 50 && (gameAllMighty.getTimeElapsedSinceLastBought(0) > rechargeTime)) {
                                sunCount-=50;
                                sunFlower newPlant = new sunFlower(row, column, posX, posY);
                                plantList.add(newPlant);
                                newPlant.addPlantToLawn(quickPlayPane);
                                sunFlowerArrayList.add(newPlant);
                                gameAllMighty.resetTimeElapsedSinceLastBought(0);
                            }
                            else
                                System.out.println("Not enough sun!");
                            break;
                        case "potatomine":
                            if(sunCount >= 150 && (gameAllMighty.getTimeElapsedSinceLastBought(3) > rechargeTime)) {
                                sunCount-=150;
                                potatoMine newPlant = new potatoMine(row, column, posX, posY);
                                plantList.add(newPlant);
                                newPlant.addPlantToLawn(quickPlayPane);
                                potatoMineArrayList.add(newPlant);
                                gameAllMighty.resetTimeElapsedSinceLastBought(3);
                            }
                            else
                                System.out.println("Not enough sun!");
                            break;
                        case "wallnut":
                            if(sunCount >= 50 && gameAllMighty.getTimeElapsedSinceLastBought(2) > rechargeTime) {
                                sunCount-=50;
                                wallNut newPlant = new wallNut(row, column, posX, posY);
                                plantList.add(newPlant);
                                newPlant.addPlantToLawn(quickPlayPane);
                                wallNutArrayList.add(newPlant);
                                gameAllMighty.resetTimeElapsedSinceLastBought(2);
                            }
                            else
                                System.out.println("Not enough sun!");
                            break;
                        default:
                            System.out.println("wrong or no id for plant!");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Cannot create plant!");
                }
                sunLabel.setText(Integer.toString(sunCount)); //updating the sunCount
            }
            else{
                System.out.println("Out of bounds for planting");
            }

        });


        sunLabel.setText(Integer.toString(sunCount));
        sunLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        sunLabel.setX(40);
        sunLabel.setY(132);


        quickPlayPane.getChildren().addAll(lawnImageView, barImageView, plantSlots, pauseButton,sunLabel);

        //Placing the lawnmowers:
        lawnmower lw1 = new lawnmower(1);
        lawnmowerArrayList.add(lw1);
        lw1.addLawnmower(quickPlayPane);

        lawnmower lw2 = new lawnmower(2);
        lawnmowerArrayList.add(lw2);
        lw2.addLawnmower(quickPlayPane);

        lawnmower lw3 = new lawnmower(3);
        lawnmowerArrayList.add(lw3);
        lw3.addLawnmower(quickPlayPane);

        lawnmower lw4 = new lawnmower(4);
        lawnmowerArrayList.add(lw4);
        lw4.addLawnmower(quickPlayPane);

        lawnmower lw5 = new lawnmower(5);
        lawnmowerArrayList.add(lw5);
        lw5.addLawnmower(quickPlayPane);

        quickPlayPane.setOnMouseClicked(mouseEvent -> {
            double mousePosX = mouseEvent.getX();
            double mousePosY = mouseEvent.getY();
            System.out.println("X: "+mouseEvent.getX());
            System.out.println("Y: "+mouseEvent.getY());
            collectSun(mousePosX,mousePosY);

        });

        //Spawning zombies:

        if(!isResumed)
            for(int i=0;i<level*3;i++) {
                zombiesList.add(zombie.zombieSpawner(quickPlayPane));
            }

        if(isResumed)
            gameController = resumedController;
        else
            gameController = new gameAllMighty(primaryStage,quickPlayPane,mainMenuScene,plantList,zombiesList,lawnmowerArrayList,peaShooterArrayList,sunFlowerArrayList,potatoMineArrayList,numberOfZombiesLeft,level,sunCount);


        gameController.initialize();

        Scene quickPlayScene = new Scene(quickPlayPane);


        primaryStage.setScene(quickPlayScene);
        primaryStage.show();
}

    private void saveGame() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("test.txt"));
            out.writeObject(gameController);
            System.out.println("Saved Game!");

        } finally {
            out.close();
        }
    }

    private void collectSun(double mousePosX, double mousePosY) {
        ArrayList<sun> sunList = gameAllMighty.getSunList();
        ArrayList<sun> sunToRemove = new ArrayList();
        for(sun s : sunList){
            System.out.println("Sun x: "+s.getPosX());
            System.out.println("Sun y: "+s.getPosY());
            if(mousePosX >= s.getPosX() && mousePosX <= s.getPosX()+40 && mousePosY >= s.getPosY() && mousePosY <= s.getPosY()+40){
                sunCount+=50;
                sunToRemove.add(s);
                sunLabel.setText(Integer.toString(sunCount));
                System.out.println("Sun Total: "+sunCount);
            }
        }
        //removing collected sun:
        for(sun str: sunToRemove){
            str.getPane().getChildren().removeAll();
            quickPlayPane.getChildren().remove(str.getPane());
            gameAllMighty.removeSun(str);
        }
        sunToRemove.clear();
    }
}
