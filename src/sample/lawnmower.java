package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class lawnmower implements Serializable {
    transient Image image = new Image(new FileInputStream("res\\images\\lawnmower.png"));
    transient ImageView imageview = new ImageView(image);
    int row;
    double posX = 200;
    double posY;
    transient StackPane pane;
    boolean alreadyUnleashed;

    public lawnmower(int r) throws FileNotFoundException {
        this.row = r;
        alreadyUnleashed = false;
        pane = new StackPane();
        imageview.setPreserveRatio(true);
        imageview.setFitWidth(125);
        pane.getChildren().add(imageview);
        switch(r){
            case 1:
                posY = 78;
                break;
            case 2:
                posY = 199;
                break;
            case 3:
                posY = 319;
                break;
            case 4:
                posY = 440;
                break;
            case 5:
                posY = 562;
                break;
        }
        pane.setLayoutX(posX);
        pane.setLayoutY(posY);
    }

    void addLawnmower(Pane primaryPane){
        primaryPane.getChildren().add(pane);
    }

    void unleashLawnmower (Pane primaryPane){
        TranslateTransition lawnmowerMowing = new TranslateTransition();
        lawnmowerMowing.setDuration(Duration.seconds(2));
        lawnmowerMowing.setNode(pane);
        lawnmowerMowing.setByX(1100);
        lawnmowerMowing.setCycleCount(1);
        lawnmowerMowing.setAutoReverse(false);
        lawnmowerMowing.play();
    }
    boolean isAlreadyUnleashed(){
        return this.alreadyUnleashed;
    }

    int getRow(){
        return this.row;
    }
}
