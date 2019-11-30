package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class selectLevelController implements EventHandler<MouseEvent> {

    int level;
    Stage primaryStage;
    Scene mainMenuScene;

    public selectLevelController(Stage ps,int l,Scene mms){
        this.primaryStage = ps;
        this.level = l;
        this.mainMenuScene = mms;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        gameLevel gamelevel = new gameLevel(level,mainMenuScene);
        try {
            gamelevel.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot start level game!");
        }
    }
}