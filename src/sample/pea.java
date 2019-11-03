package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class pea {
    private Image image = new Image(new FileInputStream("res\\images\\pea.png"));
    private ImageView imageView = new ImageView(image);
    private StackPane stackPane;
    private double speed = 1;
    private double sourceX;
    private double sourceY;
    private double posX;
    private double posY;

    public pea(double px,double py) throws FileNotFoundException {
        stackPane = new StackPane();
        stackPane.getChildren().add(imageView);
        this.sourceX = this.posX = px;
        this.sourceY = this.posY = py;
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

    protected double getPosX(){
        return this.stackPane.getLayoutX();
    }

    protected double getSourceX(){
        return this.sourceX;
    }

    protected void setPosX(double x){
        this.stackPane.setLayoutX(x);
    }
}