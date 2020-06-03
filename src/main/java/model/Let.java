package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.MalformedURLException;

public class Let extends Pane {
    public int mod;
    public Let (int mod) throws MalformedURLException {
        this.mod = mod;
        int width = 0;
        int height = 0;
        String name = "";
        switch (mod) {
            case 0:
                width = 80;
                height = 40;
                name = "b0.png";
                break;
            case 1:
                width = 40;
                height = 80;
                name = "b1.png";
                break;
            case 2:
                width = 40;
                height = 40;
                name = "b2.png";
                break;
        }
        File file = new File(name);
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        getChildren().addAll(imageView);
    }
}
