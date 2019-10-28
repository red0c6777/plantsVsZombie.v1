package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.Stack;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setResizable(false);
        //Making Pane and VBox
        Pane mainMenuPane = new Pane();
        VBox menuBox = new VBox();

        //Creating glow effect for button glowing when cursor is hovered on them
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);

        //Loading all the images to be used
        Image loadingImage = new Image(new FileInputStream("res\\images\\loadinghd.jpg"),1280,720,false,false);
        Image newGameButtonImage = new Image(new FileInputStream("res\\images\\button\\newGameButton.png"),120,50,false,false);
        Image loadGameButtonImage = new Image(new FileInputStream("res\\images\\button\\loadGameButton.png"),120,50,false,false);
        Image exitButtonImage = new Image(new FileInputStream("res\\images\\button\\exitButton.png"),120,50,false,false);
        Image quickPlayButtonImage = new Image(new FileInputStream("res\\images\\button\\quickPlayButton.png"),120,50,false,false);
        Image leaderboardButtonImage = new Image(new FileInputStream("res\\images\\button\\leaderboardButton.png"),140,50,false,false);
        ImageView loadingImageImageView = new ImageView(loadingImage);

        //Creating the buttons
        Button newGameButton = new Button();
        Button loadGameButton = new Button();
        Button exitButton = new Button();
        Button quickPlayButton = new Button();
        Button leaderboardButton = new Button();

        newGameButton.setGraphic(new ImageView(newGameButtonImage));
        loadGameButton.setGraphic(new ImageView(loadGameButtonImage));
        exitButton.setGraphic(new ImageView(exitButtonImage));
        quickPlayButton.setGraphic(new ImageView(quickPlayButtonImage));
        leaderboardButton.setGraphic(new ImageView(leaderboardButtonImage));


        newGameButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        loadGameButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        exitButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        quickPlayButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        leaderboardButton.setPadding(new Insets(-1f,-1f,-1f,-1f));

        newGameButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> newGameButton.setEffect(borderGlow));
        newGameButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> newGameButton.setEffect(null));
        newGameButton.setOnAction(actionEvent -> {
            try {
                newGame(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not start new game!");
            }
        });

        loadGameButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> loadGameButton.setEffect(borderGlow));
        loadGameButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> loadGameButton.setEffect(null));
        loadGameButton.setOnAction(actionEvent -> {
            try {
                loadGame(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not load game!");
            }
        });

        exitButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> exitButton.setEffect(borderGlow));
        exitButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> exitButton.setEffect(null));
        exitButton.setOnAction(actionEvent -> System.exit(0));

        quickPlayButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> quickPlayButton.setEffect(borderGlow));
        quickPlayButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> quickPlayButton.setEffect(null));
        quickPlayButton.setOnAction(actionEvent -> {
            try {
                quickPlay(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not start quick play!");
            }
        });

        leaderboardButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> leaderboardButton.setEffect(borderGlow));
        leaderboardButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> leaderboardButton.setEffect(null));
        leaderboardButton.setOnAction(actionEvent -> {
            try {
                loadGame(
                        primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not start quick play!");
            }
        });


        menuBox.getChildren().addAll(quickPlayButton,newGameButton,loadGameButton,exitButton);
        menuBox.setSpacing(5f);

        mainMenuPane.getChildren().addAll(loadingImageImageView,menuBox,leaderboardButton);

        menuBox.setLayoutX(580);
        menuBox.setLayoutY(350);

        leaderboardButton.setLayoutX(200);
        leaderboardButton.setLayoutY(200);


        Scene mainMenuScene = new Scene(mainMenuPane);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }


    void newGame(Stage primaryStage) throws Exception{
        Image underConstructionImage = new Image(new FileInputStream("res\\images\\underConstruction.jpg"),1280,720,false,false);
        ImageView underConstructionImageView = new ImageView(underConstructionImage);

        Image submitButtonImage = new Image(new FileInputStream("res\\images\\button\\submitButton.png"),120,50,false,false);
        Image cancelButtonImage = new Image(new FileInputStream("res\\images\\button\\cancelButton.png"),120,50,false,false);

        TextField nameTF = new TextField();
        nameTF.setText("Enter name");

        Button submitButton = new Button();
        Button cancelButton = new Button();
        submitButton.setGraphic(new ImageView(submitButtonImage));
        cancelButton.setGraphic(new ImageView(cancelButtonImage));


        submitButton.setOnAction(actionEvent -> {
            try {
                startLevel(primaryStage,1);
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Could'nt start the level!");
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            try {
                start(primaryStage);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Can not start the game!");
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(submitButton,cancelButton);


        VBox menuBox = new VBox();
        menuBox.getChildren().addAll(nameTF,buttonBox);

        menuBox.setLayoutX(500);
        menuBox.setLayoutY(350);

        Pane newGamePane = new Pane();
        newGamePane.getChildren().addAll(underConstructionImageView,menuBox);
        Scene newGameScene = new Scene(newGamePane);

        primaryStage.setScene(newGameScene);
        primaryStage.show();
    }

    void loadGame(Stage primaryStage) throws Exception{
        Image underConstructionImage = new Image(new FileInputStream("res\\images\\underConstruction.jpg"),1280,720,false,false);
        ImageView underConstructionImageView = new ImageView(underConstructionImage);

        Pane loadGamePane = new Pane();
        loadGamePane.getChildren().add(underConstructionImageView);
        Scene loadGameScene = new Scene(loadGamePane);

        primaryStage.setScene(loadGameScene);
        primaryStage.show();
    }

    void quickPlay(Stage primaryStage) throws Exception{
        /*Image lawnImage = new Image(new FileInputStream("res\\images\\lawn\\lawnHD_crop.jpg"),1280,720,false,false);
        ImageView lawnImageView = new ImageView(lawnImage);

        Pane quickPlayPane = new Pane();
        quickPlayPane.getChildren().add(lawnImageView);
        Scene quickPlayScene = new Scene(quickPlayPane);

        primaryStage.setScene(quickPlayScene);
        primaryStage.show();
        */
        startLevel(primaryStage,1);
    }

    void startLevel(Stage primaryStage,int level)throws Exception{
        System.out.println("Starting the level!");

        int sunCount = 0;

        Text sunCountL = new Text();
        sunCountL.setText(Integer.toString(sunCount));

        Image lawnImage = new Image(new FileInputStream("res\\images\\lawn\\lawnHD_crop.jpg"),1280,720,false,false);
        Image zombieFlyingimage = new Image(new FileInputStream("res\\images\\zombie\\zombieFlying.gif"),114,144,false,false);

        //Images of plants
        Image sunflowerImage = new Image(new FileInputStream("res\\images\\plant\\sunflower.png"));
        Image peashooterImage = new Image(new FileInputStream("res\\images\\plant\\peashooter.png"));
        Image wallnutImage = new Image(new FileInputStream("res\\images\\plant\\wallnut.png"));
        Image peashooterCard = new Image(new FileInputStream("res\\images\\plant\\peashooterCard.png"));
        Image sunflowerCard = new Image(new FileInputStream("res\\images\\plant\\sunflowerCard.png"));
        Image wallnutCard = new Image(new FileInputStream("res\\images\\plant\\wallnutCard.png"));


        ImageView peashooterCardImageView = new ImageView(peashooterCard);
        ImageView sunflowerCardImageView = new ImageView(sunflowerCard);
        ImageView wallnutCardImageView = new ImageView(wallnutCard);

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

        TranslateTransition zombieMovingTransition = new TranslateTransition();
        zombieMovingTransition.setDuration(Duration.seconds(40));
        zombieMovingTransition.setNode(zombieFlyingImageView);
        zombieMovingTransition.setByX(-1040);
        zombieMovingTransition.setCycleCount(1);
        zombieMovingTransition.setAutoReverse(false);
        zombieMovingTransition.play();

        VBox plantSlots = new VBox();
        plantSlots.getChildren().addAll(sunflowerCardImageView,peashooterCardImageView, wallnutCardImageView);

        plantSlots.setLayoutX(20);
        plantSlots.setLayoutY(150);
        plantSlots.setSpacing(2f);

        sunCountL.setX(100);
        sunCountL.setFont(Font.font(40));

        quickPlayPane.getChildren().addAll(lawnImageView,barImageView,zombieFlyingImageView,plantSlots,sunCountL);


        peashooterCardImageView.setOnMouseDragged(mouseEvent -> {

        });

        Scene quickPlayScene = new Scene(quickPlayPane);
        primaryStage.setScene(quickPlayScene);
        primaryStage.show();
    }
    void pauseMenu(){
        System.out.println("paused");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
