package sample;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class selectLevelController implements EventHandler<MouseEvent> {

    int level;
    Stage primaryStage;

    public selectLevelController(Stage ps,int l){
        this.primaryStage = ps;
        this.level = l;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        gameLevel gamelevel = new gameLevel(level);
        try {
            gamelevel.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot start level game!");
        }
    }
}
