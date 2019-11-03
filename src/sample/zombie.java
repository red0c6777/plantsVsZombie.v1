package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class zombie {
    Image image = new Image(new FileInputStream("res//images//zombie//zombieFlying.gif"),114,144,false,false);
    ImageView imageview = new ImageView(image);
    int posX;
    int posY;
    int row;
    int health;
    double speed;
    int type;
    StackPane zombiePane;
    int initialPosX = 1300;
    int initialPosY;

    public zombie() throws FileNotFoundException {
        zombiePane = new StackPane();
        zombiePane.getChildren().add(imageview);
        health = 100;
        speed = 0.1;
    }

    public static zombie zombieSpawner(Pane primaryPane) throws FileNotFoundException {
        zombie newZombie = new zombie();
        Random rand = new Random();
        int randomRow = rand.nextInt(5)+1;
        switch(randomRow){
            case 1:
                newZombie.initialPosY = 78;
                newZombie.row = 1;
                break;
            case 2:
                newZombie.initialPosY = 199;
                newZombie.row = 2;
                break;
            case 3:
                newZombie.initialPosY = 319;
                newZombie.row = 3;
                break;
            case 4:
                newZombie.initialPosY = 440;
                newZombie.row = 4;
                break;
            case 5:
                newZombie.initialPosY = 562;
                newZombie.row = 5;
                break;
        }
        newZombie.initialPosX = (1280 + rand.nextInt(10000));

        newZombie.zombiePane.setLayoutX(newZombie.initialPosX);
        newZombie.zombiePane.setLayoutY(newZombie.initialPosY);

        primaryPane.getChildren().add(newZombie.zombiePane);


        return newZombie;

    }

    void removeZombie(Pane primaryPane){
        zombiePane.getChildren().remove(imageview);
        primaryPane.getChildren().remove(zombiePane);
    }

    void step(){
        this.zombiePane.setLayoutX(this.zombiePane.getLayoutX() - speed);
    }

    protected double getPosX(){
        return this.zombiePane.getLayoutX();
    }

    protected double getPos(){
        return  this.zombiePane.getLayoutY();
    }

    public int getRow() {
        return this.row;
    }
}
