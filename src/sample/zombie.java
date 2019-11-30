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
import java.io.Serializable;
import java.util.Random;

public class zombie implements Serializable {
    transient Image image = new Image(new FileInputStream("res//images//zombie//zombieFlying.gif"),114,144,false,false);
    transient ImageView imageview = new ImageView(image);
    double posX;
    double posY;
    double resumedPosX;
    double resumePosY;
    int row;
    int health;
    double speed;
    int type;
    transient StackPane zombiePane;
    int initialPosX = 1300;
    int initialPosY;

    public zombie() throws FileNotFoundException {
        zombiePane = new StackPane();
        posX = this.zombiePane.getLayoutX();
        posY = this.zombiePane.getLayoutY();
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
        newZombie.posX = newZombie.initialPosX;
        newZombie.zombiePane.setLayoutY(newZombie.initialPosY);
        newZombie.posY = newZombie.initialPosY;

        primaryPane.getChildren().add(newZombie.zombiePane);


        return newZombie;

    }

    void dead(Pane primaryPane){
        zombiePane.getChildren().remove(imageview);
        primaryPane.getChildren().remove(zombiePane);
    }

    void step(){
        this.zombiePane.setLayoutX(this.zombiePane.getLayoutX() - speed);
        posX = posX - speed;
    }

    protected double getPosX(){
        return this.posX;
    }

    protected double getPosY(){
        return this.posY;
    }

    protected double getPos(){
        return  this.zombiePane.getLayoutY();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageview() {
        return imageview;
    }

    public void setImageview(ImageView imageview) {
        this.imageview = imageview;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public StackPane getZombiePane() {
        return zombiePane;
    }

    public void setZombiePane(StackPane zombiePane) {
        this.zombiePane = zombiePane;
    }

    public int getInitialPosX() {
        return initialPosX;
    }

    public void setInitialPosX(int initialPosX) {
        this.initialPosX = initialPosX;
    }

    public int getInitialPosY() {
        return initialPosY;
    }

    public void setInitialPosY(int initialPosY) {
        this.initialPosY = initialPosY;
    }

    public int getRow() {
        return this.row;
    }

    protected void damage(double d) {
        this.health -= d;
    }


}
