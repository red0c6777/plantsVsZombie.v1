package sample;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

import java.util.EventListener;

public class draggingPlantController implements EventHandler<MouseEvent> {
    private ImageView plantImageView;
    private Image plantImage;

    public draggingPlantController(ImageView imageView, Image image){
        this.plantImageView = imageView;
        this.plantImage = image;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        Dragboard drag = this.plantImageView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        String id = this.plantImageView.getId();
        content.putImage(this.plantImage);
        if(id == "peashooter" || id == "wallnut" || id == "sunflower" || id == "potatomine")
            content.putString(id);
        else
            System.out.println("ERROR: not a valid id");
        drag.setContent(content);
    }
}
