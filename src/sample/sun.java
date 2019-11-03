package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Random;

public class sun {
    Image image = new Image(new FileInputStream("res\\images\\sun.png"));
    ImageView imageView = new ImageView(image);
    StackPane stackPane = new StackPane();
    double posX;
    double targetY;

    public sun() throws FileNotFoundException {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(40);
        stackPane.getChildren().add(imageView);
        Random rand = new Random();
        posX = rand.nextInt(1220) + 300;
        targetY = rand.nextInt(650);
    }


    public static sun sunSpawner(Pane primaryPane) throws FileNotFoundException {
        sun newSun = new sun();
        newSun.stackPane.setLayoutX(newSun.posX);
        newSun.stackPane.setLayoutY(-10);
        primaryPane.getChildren().add(newSun.stackPane);
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(5000));
        translateTransition.setNode(newSun.stackPane);
        translateTransition.setByY(newSun.targetY);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.setOnFinished(event -> {
            newSun.stackPane.setLayoutY(newSun.stackPane.getLayoutY() + newSun.targetY);
            newSun.stackPane.setTranslateX(0);
            newSun.stackPane.setTranslateY(0);
        });
        translateTransition.play();

        return newSun;
    }

    protected double getPosX(){
        return this.stackPane.getLayoutX();
    }

    protected  double getPosY(){
        return this.stackPane.getLayoutY();
    }

    protected StackPane getPane() {
        return this.stackPane;
    }

}
