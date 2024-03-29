package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    private Pane mainMenuPane;
    private Scene mainMenuScene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setResizable(false);
        //Making Pane and VBox
        mainMenuPane = new Pane();
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
        Image startGameButtonImage = new Image(new FileInputStream("res\\images\\button\\startGameButton.png"),120,50,false,false);
        Image leaderboardButtonImage = new Image(new FileInputStream("res\\images\\button\\leaderboardButton.png"),140,50,false,false);
        Image selectLevelImage = new Image(new FileInputStream("res\\images\\button\\selectLevelButton.png"),120,50,false,false);
        ImageView loadingImageImageView = new ImageView(loadingImage);

        //Creating the buttons
        Button newGameButton = new Button();
        Button loadGameButton = new Button();
        Button exitButton = new Button();
        Button startGameButton = new Button();
        Button leaderboardButton = new Button();
        Button selectLevelButton = new Button();

        newGameButton.setGraphic(new ImageView(newGameButtonImage));
        loadGameButton.setGraphic(new ImageView(loadGameButtonImage));
        exitButton.setGraphic(new ImageView(exitButtonImage));
        startGameButton.setGraphic(new ImageView(startGameButtonImage));
        leaderboardButton.setGraphic(new ImageView(leaderboardButtonImage));
        selectLevelButton.setGraphic(new ImageView(selectLevelImage));


        newGameButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        loadGameButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        exitButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        startGameButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        leaderboardButton.setPadding(new Insets(-1f,-1f,-1f,-1f));
        selectLevelButton.setPadding(new Insets(-1f,-1f,-1f,-1f));

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

        startGameButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> startGameButton.setEffect(borderGlow));
        startGameButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> startGameButton.setEffect(null));
        startGameButton.setOnAction(actionEvent -> {
            try {
                //startGame(primaryStage);
                gameLevel gamelevel = new gameLevel(3,mainMenuScene);
                gamelevel.setPlayerName("Guest");
                gamelevel.start(primaryStage);
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

        selectLevelButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(event) -> selectLevelButton.setEffect(borderGlow));
        selectLevelButton.addEventHandler(MouseEvent.MOUSE_EXITED,(event) -> selectLevelButton.setEffect(null));
        selectLevelButton.setOnAction(actionEvent -> {
            try {
                selectLevel(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not select level!");
            }
        });

        menuBox.getChildren().addAll(startGameButton,newGameButton,loadGameButton,selectLevelButton,exitButton);
        menuBox.setSpacing(5f);

        mainMenuPane.getChildren().addAll(loadingImageImageView,menuBox,leaderboardButton);

        menuBox.setLayoutX(580);
        menuBox.setLayoutY(350);

        leaderboardButton.setLayoutX(200);
        leaderboardButton.setLayoutY(200);


        mainMenuScene = new Scene(mainMenuPane);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }


    void newGame(Stage primaryStage) throws Exception{
        Image bgi = new Image(new FileInputStream("res\\images\\selectLevelBackground.jpg"),1280,720,false,false);
        ImageView bgiv = new ImageView(bgi);

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
                gameLevel gamelevel = new gameLevel(1,mainMenuScene);
                String playerName = nameTF.getText();
                System.out.println(playerName);
                gamelevel.setPlayerName(playerName);
                gamelevel.start(primaryStage);
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Couldn't start the level!");
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
        newGamePane.getChildren().addAll(bgiv,menuBox);
        Scene newGameScene = new Scene(newGamePane);

        primaryStage.setScene(newGameScene);
        primaryStage.show();
    }

    void loadGame(Stage primaryStage) throws Exception{
        /*Image underConstructionImage = new Image(new FileInputStream("res\\images\\underConstruction.jpg"),1280,720,false,false);
        ImageView underConstructionImageView = new ImageView(underConstructionImage);

        Pane loadGamePane = new Pane();
        loadGamePane.getChildren().add(underConstructionImageView);
        Scene loadGameScene = new Scene(loadGamePane);

        primaryStage.setScene(loadGameScene);
        primaryStage.show();
         */

        Image bgi = new Image(new FileInputStream("res\\images\\selectLevelBackground.jpg"),1280,720,false,false);
        ImageView bgiv = new ImageView(bgi);

        Image submitButtonImage = new Image(new FileInputStream("res\\images\\button\\submitButton.png"),120,50,false,false);
        Image cancelButtonImage = new Image(new FileInputStream("res\\images\\button\\cancelButton.png"),120,50,false,false);

        TextField nameTF = new TextField();
        nameTF.setText("Enter name to load the game");

        Button submitButton = new Button();
        Button cancelButton = new Button();
        submitButton.setGraphic(new ImageView(submitButtonImage));
        cancelButton.setGraphic(new ImageView(cancelButtonImage));


        submitButton.setOnAction(actionEvent -> {
            try {
                gameLevel gamelevel = new gameLevel(1,mainMenuScene);
                String playerName = nameTF.getText();
                System.out.println(playerName);
                gamelevel.setPlayerName(playerName);
                gamelevel.start(primaryStage);
                gameAllMighty loadedGame;
                ObjectInputStream in = null;
                try{
                    in = new ObjectInputStream(new FileInputStream(playerName+".txt"));
                    loadedGame = (gameAllMighty) in.readObject();
                    //System.out.println("Loaded game seconds= " + loadedGame.getSecondsPassed());
                    gameLevel loadedGamelevel = new gameLevel(loadedGame.getLevel(),mainMenuScene);
                    loadedGamelevel.resume(primaryStage,loadedGame,true);
                }finally {
                    in.close();
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Couldn't load the level!");
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
        newGamePane.getChildren().addAll(bgiv,menuBox);
        Scene newGameScene = new Scene(newGamePane);

        primaryStage.setScene(newGameScene);
        primaryStage.show();

    }

    void selectLevel(Stage primaryStage) throws FileNotFoundException {
        Pane selectLevelPane = new Pane();
        Image bg = new Image(new FileInputStream("res\\images\\selectLevelBackground.jpg"),1280,720,false,false);
        Image selectLevelFlagImage = new Image(new FileInputStream("res\\images\\selectLevelFlag.png"));
        ImageView selectLevelFlagImageView = new ImageView(selectLevelFlagImage);
        selectLevelFlagImageView.setPreserveRatio(true);
        selectLevelFlagImageView.setFitWidth(700);
        selectLevelFlagImageView.setLayoutX(290);
        selectLevelFlagImageView.setLayoutY(100);

        HBox levelButtons = new HBox();
        Button l1 = new Button();
        l1.setId("1");
        l1.setGraphic(new ImageView(new Image(new FileInputStream("res\\images\\button\\l1Button.png"),120,50,false,false)));
        l1.setPadding(new Insets(-2f));

        Button l2 = new Button();
        l2.setId("2");
        l2.setGraphic(new ImageView(new Image(new FileInputStream("res\\images\\button\\l2Button.png"),120,50,false,false)));
        l2.setPadding(new Insets(-2f));

        Button l3 = new Button();
        l3.setId("3");
        l3.setGraphic(new ImageView(new Image(new FileInputStream("res\\images\\button\\l3Button.png"),120,50,false,false)));
        l3.setPadding(new Insets(-2f));

        Button l4 = new Button();
        l4.setId("4");
        l4.setGraphic(new ImageView(new Image(new FileInputStream("res\\images\\button\\l4Button.png"),120,50,false,false)));
        l4.setPadding(new Insets(-2f));

        Button l5 = new Button();
        l5.setId("5");
        l5.setGraphic(new ImageView(new Image(new FileInputStream("res\\images\\button\\l5Button.png"),120,50,false,false)));
        l5.setPadding(new Insets(-2f));

        levelButtons.getChildren().addAll(l1,l2,l3,l4,l5);
        levelButtons.setSpacing(10f);

        levelButtons.setLayoutY(350);
        levelButtons.setLayoutX(320);

        l1.setOnMouseClicked(new selectLevelController(primaryStage ,1,mainMenuScene));
        l2.setOnMouseClicked(new selectLevelController(primaryStage,2,mainMenuScene));
        l3.setOnMouseClicked(new selectLevelController(primaryStage,3,mainMenuScene));
        l4.setOnMouseClicked(new selectLevelController(primaryStage,4,mainMenuScene));
        l5.setOnMouseClicked(new selectLevelController(primaryStage,5,mainMenuScene));

        selectLevelPane.getChildren().addAll(new ImageView(bg),selectLevelFlagImageView,levelButtons);
        Scene selectLevelScene = new Scene(selectLevelPane);
        primaryStage.setScene(selectLevelScene);
    }

    void peaShooterSelected(double startPosX,double startPosY){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
