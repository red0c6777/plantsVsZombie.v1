package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

public class plant {
    private int row;
    private int column;
    private double posX;
    private double posY;
    double health;
    private String plantType;
    private StackPane plantpane;
    private Image plantImage;
    private ImageView plantImageView;

    public plant (String pt, int r, int c, double px, double py) throws FileNotFoundException{
        this.plantType = pt;
        this.row = r;
        this.column = c;
        switch(pt){
            case "peashooter":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\peashooter.png"));
                health = 100;
                break;
            case "sunflower":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\sunflower.png"));
                health = 100;
                break;
            case "wallnut":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\wallnut.png"));
                health = 500;
                break;
            case "potatomine":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\potatoMine.png"));
                health = 50;
                break;
        }
        this.plantImageView = new ImageView(plantImage);
        this.posX = px;
        this.posY = py;
        plantpane = new StackPane(plantImageView);
        plantpane.setLayoutX(posX+10);
        plantpane.setLayoutY(posY+30);

    }

    void addPlantToLawn(Pane quickPlayPane){
        quickPlayPane.getChildren().add(plantpane);
    }

    void removePlantFromLawn(Pane quickPlayPane){
        plantpane.getChildren().remove(plantImageView);
        quickPlayPane.getChildren().remove(this.plantpane);
    }

    protected double getPosX(){
        return this.posX;
    }

    protected double getPosY(){
        return this.posY;
    }

    public int getColumn() {
        return column;
    }

    public int getRow(){
        return row;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    void dead(Pane primaryPane){
        plantpane.getChildren().remove(plantImageView);
        primaryPane.getChildren().remove(plantImage);
    }
}

class peaShooter extends plant{

    public peaShooter(int r, int c, double px, double py) throws FileNotFoundException {
        super("peashooter", r, c, px, py);
    }
}

class sunFlower extends plant{

    public sunFlower(int r, int c, double px, double py) throws FileNotFoundException {
        super("sunflower", r, c, px, py);
    }
}

class wallNut extends plant{

    public wallNut(int r, int c, double px, double py) throws FileNotFoundException {
        super("wallnut", r, c, px, py);
    }
}

class potatoMine extends plant{

    public potatoMine(int r, int c, double px, double py) throws FileNotFoundException {
        super("potatomine", r, c, px, py);
    }
}


