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
    int row;
    int column;
    double posX;
    double posY;
    String plantType;
    StackPane plantpane;
    Image plantImage;
    ImageView plantImageView;

    public plant (String pt, int r, int c, double px, double py) throws FileNotFoundException{
        this.plantType = pt;
        this.row = r;
        this.column = c;
        switch(pt){
            case "peashooter":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\peashooter.png"));
                break;
            case "sunflower":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\sunflower.png"));
                break;
            case "wallnut":
                plantImage = new Image(new FileInputStream("res\\images\\plant\\wallnut.png"));
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
        quickPlayPane.getChildren().remove(this.plantpane);
    }
}
