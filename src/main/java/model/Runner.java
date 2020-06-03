package model;

import animation.SpriteAnimation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.Main;

import java.io.File;
import java.net.MalformedURLException;

public class Runner extends Pane {
    File file = new File("runner.png");
    String localUrl = file.toURI().toURL().toString();
    Image image = new Image(localUrl);
    ImageView imageView1 = new ImageView(image);
    public Point2D p;
    int count = 5;
    int offsetX = 0;
    int width = 227;
    int height = 493;
    int v = 0;
    public SpriteAnimation animation;
    public Runner() throws MalformedURLException {
        p = new Point2D(0,0);
        setTranslateX(100);
        setTranslateY(500);
        imageView1.setFitHeight(123);
        imageView1.setFitWidth(57);
        imageView1.setViewport(new Rectangle2D(offsetX,0,width,height));
        animation = new SpriteAnimation(this.imageView1, Duration.millis(500),count,offsetX,width,height);
        getChildren().addAll(this.imageView1);
    }

    public boolean moveX(double x, double m) {
        for (int i = 0; i < Math.abs(x); i++) {
            setTranslateX(getTranslateX() + 1 + m);
            for (int j = v; j < Main.lets.size(); j++) {
                if (this.getBoundsInParent().intersects(Main.lets.get(j).getBoundsInParent())) {
                    setTranslateX(100);
                    setTranslateY(500);
                    v = 0;
                    return true;
                }
                if(getTranslateX() >= Main.lets.get(j).getTranslateX() + 70) {
                    Main.score++;
                    v = v + 1;
                }
            }
        }
        return false;
    }

    public boolean moveY(double y) {
        for (int i = 0; i < Math.abs(y); i++) {
            for (Let let : Main.lets) {
                if (this.getBoundsInParent().intersects(let.getBoundsInParent())) {
                    setTranslateX(100);
                    setTranslateY(500);
                    v = 0;
                    return true;
                }
            }
        }
        if (getTranslateY() < 0) {
            setTranslateY(0);
        }
        if (getTranslateY() > 560) {
            setTranslateY(560);
        }
        setTranslateY(getTranslateY() + y);
        return false;
    }

    public void jump() {
        if (getTranslateY() == 565) {
            p = new Point2D(6, -20);
        }
    }
}
