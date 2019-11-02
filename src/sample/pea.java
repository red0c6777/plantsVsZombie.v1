package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class pea {
    Image image = new Image(new FileInputStream("res\\images\\pea.png"));
    ImageView imageView = new ImageView(image);
    StackPane stackPane;
    double speed = 1;
    double posX;
    double posY;

    public pea(double px,double py) throws FileNotFoundException {
        stackPane = new StackPane();
        stackPane.getChildren().add(imageView);
        this.posX = px;
        this.posY = py;
        stackPane.setLayoutX(posX);
        stackPane.setLayoutY(posY);
    }

    void addPeaToLawn(Pane primaryPane){
        primaryPane.getChildren().add(this.stackPane);
    }
    void step(){
        this.stackPane.setLayoutX(this.stackPane.getLayoutX() + speed);
    }

    public void removePea() {

    }
}
