package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;

public class gameLevel extends Application {

    int level;

    void setLevel(int l){
        level = l;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        System.out.println("Starting the level!");

        //Creating glow effect for button glowing when cursor is hovered on them
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

        //Sun counter

        int sunCount = 0;
        Text sunCountL = new Text();
        sunCountL.setText(Integer.toString(sunCount));

        //Button images and image Views

        Image pauseButtonImage = new Image(new FileInputStream("res\\images\\button\\pauseButton.png"));
        ImageView pauseButtonImageView = new ImageView(pauseButtonImage);
        pauseButtonImageView.setPreserveRatio(true);
        pauseButtonImageView.setFitWidth(100);
        Image resumeButtonImage = new Image(new FileInputStream("res\\images\\button\\resumeButton.png"));
        ImageView resumeButtonImageView = new ImageView(resumeButtonImage);
        Image exitButtonImage = new Image(new FileInputStream("res\\images\\button\\exitButtonPauseMenu.png"));
        ImageView exitButtonImageView = new ImageView(exitButtonImage);
        resumeButtonImageView.setPreserveRatio((true));
        resumeButtonImageView.setFitWidth(150);
        exitButtonImageView.setPreserveRatio(true);
        exitButtonImageView.setFitWidth(150);

        Image lawnImage = new Image(new FileInputStream("res\\images\\lawn\\lawnHD_crop.jpg"), 1280, 720, false, false);
        Image zombieFlyingimage = new Image(new FileInputStream("res\\images\\zombie\\zombieFlying.gif"), 114, 144, false, false);

        //Images and image views of plants
        Image sunflowerImage = new Image(new FileInputStream("res\\images\\plant\\sunflower.png"));
        Image peashooterImage = new Image(new FileInputStream("res\\images\\plant\\peashooter.png"));
        Image wallnutImage = new Image(new FileInputStream("res\\images\\plant\\wallnut.png"));
        Image peashooterCard = new Image(new FileInputStream("res\\images\\plant\\peashooterCard.png"));
        Image sunflowerCard = new Image(new FileInputStream("res\\images\\plant\\sunflowerCard.png"));
        Image wallnutCard = new Image(new FileInputStream("res\\images\\plant\\wallnutCard.png"));
        Image pauseMenuImage = new Image(new FileInputStream("res\\images\\button\\pauseMenuPicture.png"));
        ImageView peashooterCardImageView = new ImageView(peashooterCard);
        ImageView sunflowerCardImageView = new ImageView(sunflowerCard);
        ImageView wallnutCardImageView = new ImageView(wallnutCard);
        ImageView pauseMenuImageView = new ImageView(pauseMenuImage);


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


        //Pause Box for buttons:

        VBox pauseMenuBox = new VBox();
        pauseMenuBox.getChildren().addAll(resumeButton, exitButton);
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
                menuPopUp.hide();
                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not exit");
            }
        });


        peashooterCardImageView.setPreserveRatio(true);
        wallnutCardImageView.setPreserveRatio(true);
        sunflowerCardImageView.setPreserveRatio(true);

        int wid = 80;
        peashooterCardImageView.setFitWidth(wid);
        sunflowerCardImageView.setFitWidth(wid);
        wallnutCardImageView.setFitWidth(wid);


        Image barImage = new Image(new FileInputStream("res\\images\\bar.png"));
        ImageView barImageView = new ImageView(barImage);
        barImageView.setPreserveRatio(true);
        barImageView.setFitHeight(670);
        barImageView.setLayoutX(10);
        barImageView.setLayoutY(10);

        primaryStage.setMaxHeight(720);
        primaryStage.setMaxWidth(1280);

        ImageView lawnImageView = new ImageView(lawnImage);
        ImageView zombieFlyingImageView = new ImageView(zombieFlyingimage);
        Pane quickPlayPane = new Pane();

        zombieFlyingImageView.setX(1300);
        zombieFlyingImageView.setY(40);

        //Zombie walking/flying animation:
        TranslateTransition zombieMovingTransition = new TranslateTransition();
        zombieMovingTransition.setDuration(Duration.seconds(40));
        zombieMovingTransition.setNode(zombieFlyingImageView);
        zombieMovingTransition.setByX(-1040);
        zombieMovingTransition.setCycleCount(1);
        zombieMovingTransition.setAutoReverse(false);
        zombieMovingTransition.play();

        //Plant slots VBox:
        VBox plantSlots = new VBox();
        plantSlots.getChildren().addAll(sunflowerCardImageView, peashooterCardImageView, wallnutCardImageView);
        plantSlots.setLayoutX(20);
        plantSlots.setLayoutY(150);
        plantSlots.setSpacing(2f);

        double startDragX = 10;
        double startDragY = 60;


        peashooterCardImageView.setOnMousePressed(mouseEvent -> {

            System.out.println("clicked plant");
            peashooterCardImageView.setEffect(plantGlow);
            //peaShooterSelected(peashooterCardImageView.getX(),peashooterCardImageView.getY());
        });

        sunCountL.setX(100);
        sunCountL.setFont(Font.font(40));

        quickPlayPane.getChildren().addAll(lawnImageView, barImageView, zombieFlyingImageView, plantSlots, sunCountL, pauseButton);


        peashooterCardImageView.setOnMouseDragged(mouseEvent -> {

        });

        Scene quickPlayScene = new Scene(quickPlayPane);
        primaryStage.setScene(quickPlayScene);
        primaryStage.show();

}}
