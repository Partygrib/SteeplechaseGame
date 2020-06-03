package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Let;
import model.Runner;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    File file = new File("back.png");
    String localUrl = file.toURI().toURL().toString();
    Image imageBack = new Image(localUrl);
    Pane appField = new Pane();
    Pane gameField = new Pane();
    Runner runner;
    {
        try {
            runner = new Runner();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Let> lets = new ArrayList<>();
    public static int score = 0;
    private static final Label scoreLabel = new Label("Score: " + score);
    private static final Label begin = new Label("Чтобы начать нажмите любую клавишу");
    private static final Label gameOver = new Label("GAME OVER!"
            + System.lineSeparator() + "Score: " + score);
    private static int oldScore = 0;
    private static double m = 0.0;
    private static int check = 0;

    public Main() throws MalformedURLException {
    }

    public Parent createScene() throws MalformedURLException {
        int k = 0;
        ImageView background = new ImageView(imageBack);
        background.setFitHeight(900);
        background.setFitWidth(1600);
        gameField.setPrefSize(1600, 900);
        for (int i = 0; i < 150; i++) {
            int a = new Random().nextInt(3);
            Let let = new Let(a);
            if (i % 10 == 0) k = k + 100;
            let.setTranslateX(i * (900 + k) + 1200 + let.getWidth());
            if (a == 1) let.setTranslateY(606 - let.getHeight());
            else let.setTranslateY(646);
            lets.add(let);
            gameField.getChildren().addAll(let);
        }
        scoreLabel.setFont(new Font("Arial", 45));
        scoreLabel.setTextFill(Color.DEEPPINK);
        gameField.getChildren().addAll(runner);
        begin.setTranslateY(700);
        begin.setTranslateX(350);
        begin.setFont(new Font("Arial", 55));
        begin.setTextFill(Color.LIME);
        gameOver.setVisible(false);
        gameOver.setTranslateY(300);
        gameOver.setTranslateX(600);
        gameOver.setFont(new Font("Arial", 65));
        gameOver.setTextFill(Color.LIME);
        appField.getChildren().addAll(background,gameField,scoreLabel,begin,gameOver);
        return appField;
    }

    public void update() {
        if (check == 0) {
            gameOver.setVisible(false);
            if (runner.p.getY() < 5) {
                runner.p = runner.p.add(0, 1);
            }
            boolean g1 = runner.moveX(runner.p.getX(), m);
            boolean g2 = runner.moveY(runner.p.getY());
            if (g1 || g2) {
                gameOver.setVisible(true);
                gameOver.setText("GAME OVER!" + System.lineSeparator()
                        + "Score: " + score);
                score = 0;
                oldScore = 0;
                m = 0.0;
                check = 1;
            }
            if (score >= oldScore + 5) {
                oldScore = score;
                m = m + 0.2;
            }
            scoreLabel.setText("Score: " + score);
            runner.translateXProperty().addListener((obs, old, newValue) -> {
                if (runner.getTranslateY() < 565) runner.animation.setOffsetX(908);
                else runner.animation.setOffsetX(0);
                runner.animation.play();
                int a = newValue.intValue();
                if (a > 200) gameField.setLayoutX(-(a - 200));
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        Scene scene = new Scene(createScene());
        scene.setOnKeyPressed(event -> {
            begin.setVisible(false);
            runner.jump();
            check = 0;
        });
        scene.setOnMouseClicked(event -> {
            begin.setVisible(false);
            runner.jump();
            check = 0;
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Steeplechase");
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
