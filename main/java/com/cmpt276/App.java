package com.cmpt276;

import com.cmpt276.Game.Game;
import com.cmpt276.Map.LevelLoader;
import com.cmpt276.Map.MapGenerator;
import com.cmpt276.Map.RandomMazeGenerator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Wrapper main class used to make creation of 
 * executable jar file 
 */
class GUIStarter{
    public static void main(String[] args ) {
        App.main(args);
    }
}
public class App extends Application{

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    /**
     * is used by the timeline to run every tick
     */
    private static class LoopEventHandler implements EventHandler<ActionEvent> {
        private Timeline timeline = null;
        private Game game;
        private Stage stage;

        /**
         * Constructor
         *
         * @param game the game instance
         * @param stage the screen the game draws to
         */
        public LoopEventHandler(Game game, Stage stage) {
            this.game = game;
            this.stage = stage;
        }
        /**
         * Function called every tick to run all the game code
         *
         * @param event NOT USED
         */
        @Override
        public void handle(ActionEvent event) {
            game.play();
            if (!game.isOpen()) {
                timeline.stop();
                int a = game.getScore();
                String s;
                Pane root = new Pane();
                Text text = new Text();
                Image quitImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Sadge.png"));
                ImageView quitImgView = new ImageView(quitImg);
                Button quit = new Button("Quit", quitImgView);
                quit.setContentDisplay(ContentDisplay.RIGHT);
                quit.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent e){
                        stage.close();
                    }
                });

                if (a < 0)
                {
                    s = "GAME OVER";
                    Image lockedDoorImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/LockedGate.png"));
                    ImageView lockedDoorImgView = new ImageView(lockedDoorImg);
                    Image thiefImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/GameOver.png"));
                    ImageView thiefImgView = new ImageView(thiefImg);
                    lockedDoorImgView.setX(150);
                    lockedDoorImgView.setY(256);
                    thiefImgView.setX(150);
                    thiefImgView.setY(256);
                    quit.setLayoutX(750);
                    quit.setLayoutY(400);

                    stage.setTitle("GAME OVER");
                    root.getChildren().add(thiefImgView);
                    root.getChildren().add(lockedDoorImgView);
                }
                else{
                    s = "ESCAPED";
                    Image escapedImg;
                    if (a == 0){
                        escapedImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/GameOver.png"));
                    }
                    else{
                        escapedImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Anna.png"));
                    }
                    ImageView escapedImgView = new ImageView(escapedImg);
                    Text scoreTxt = new Text();
                    String scoreString = "Score: "+ (game.getScore());
                    scoreTxt.setText(scoreString);
                    scoreTxt.setFont(Font.font("Verdana", 100));
                    scoreTxt.setX(100);
                    scoreTxt.setY(400);
                    escapedImgView.setX(600);
                    escapedImgView.setY(200);
                    quit.setLayoutX(150);
                    quit.setLayoutY(500);

                    stage.setTitle("ESCAPED");
                    root.getChildren().add(escapedImgView);
                    root.getChildren().add(scoreTxt);
                }

                text.setText(s);
                text.setX(400);
                text.setY(200);
                text.setFont(Font.font("Impact", 100));
                root.getChildren().add(quit);
                root.getChildren().add(text);
                Scene scene = new Scene(root, WIDTH, HEIGHT);

                stage.setScene(scene);
                stage.show();
            }
        }
        public void setTimeline(Timeline timeline) {
            this.timeline = timeline;
        }
    }

    /**
     * Takes in a filename and loads that level then starts the gane
     *
     * @param stage the stage to draw the game to
     * @param filename the path to the map
     * @param title the title of the window to create
     */
    private void loadLevel(Stage stage, String filename, String title) {
        Pane root = new Pane();
        MapGenerator gen = new LevelLoader(filename);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Game game = new Game(gen, root);
        scene.setOnKeyPressed(game);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        StartGame(stage, game);
    }

    /**
     * main class
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Image level1Img, level2Img, level3Img, level4Img, randomImg, quitImg;
    private ImageView LV1ImgView, LV2ImgView, LV3ImgView, LV4ImgView, randomLVImgView, quitImgView;

    /**
     * loads all necessary Images for LevelSelect screen
     */
    private void loadImages(){
        level1Img = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Level1.png"));
        level2Img = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Level2.png"));
        level3Img = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Level3.png"));
        level4Img = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Level4.png"));
        quitImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Sadge.png"));
        randomImg = new Image(this.getClass().getResourceAsStream("/Images/LevelSelectImages/Random.png"));
        LV1ImgView = new ImageView(level1Img);
        LV2ImgView = new ImageView(level2Img);
        LV3ImgView = new ImageView(level3Img);
        LV4ImgView = new ImageView(level4Img);
        randomLVImgView = new ImageView(randomImg);
        quitImgView = new ImageView(quitImg);
    }


    /**
     * Creates LevelSelect screen with text and buttons to lead to the levels or exit of the Game.
     *
     * @param stage stage that is shown to the player
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        Pane levelSelectPane = new Pane();
        Scene levelSelectScene = new Scene(levelSelectPane, WIDTH, HEIGHT);

        loadImages();

        Text levelSelectText = new Text();
        levelSelectText.setText("LEVEL SELECT");
        levelSelectText.setX(350);
        levelSelectText.setY(200);
        levelSelectText.setFont(Font.font("Impact", 100));

        Text ReadMeText = new Text();
        ReadMeText.setTextAlignment(TextAlignment.CENTER);
        ReadMeText.setText("Read the \nREADME.md \nfor how to play.");
        ReadMeText.setX(675);
        ReadMeText.setY(700);
        ReadMeText.setFont(Font.font("Verdana", 20));

        Button LV1Button = new Button("Tutorial One", LV1ImgView);
        Button LV2Button = new Button("Tutorial Two", LV2ImgView);
        Button LV3Button = new Button("Tutorial Three", LV3ImgView);
        Button LV4Button = new Button("Tutorial Four", LV4ImgView);
        Button quit = new Button("Quit", quitImgView);
        Button randomLVButton = new Button("Random Maps", randomLVImgView);
        LV1Button.setContentDisplay(ContentDisplay.BOTTOM);
        LV2Button.setContentDisplay(ContentDisplay.BOTTOM);
        LV3Button.setContentDisplay(ContentDisplay.BOTTOM);
        LV4Button.setContentDisplay(ContentDisplay.BOTTOM);
        quit.setContentDisplay(ContentDisplay.RIGHT);

        /**
         * Sets LV1Button to load Level1 Map.
         */
        LV1Button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                loadLevel(stage, "classes/maps/map1.txt", "Tutorial One");
            }
        });

        /**
         * Sets LV2Button to load Level2 Map.
         */
        LV2Button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                loadLevel(stage, "classes/maps/map2.txt", "Tutorial Two");
            }
        });

        /**
         * Sets LV3Button to load Level3 Map.
         */
        LV3Button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                loadLevel(stage, "classes/maps/map3.txt", "Tutorial Three");
            }
        });

        /**
         * Sets LV4Button to load Level4 Map.
         */
        LV4Button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                loadLevel(stage, "classes/maps/map4.txt", "Tutorial Four");
            }
        });

        /**
         * Sets quit Button to close Stage.
         */
        quit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                stage.close();
            }
        });

        /**
         * sets randomLVButton to load randomMap.
         */
        randomLVButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Pane root = new Pane();
                MapGenerator gen = new RandomMazeGenerator(new RandomMazeGenerator.Options(18, 12));

                Scene scene = new Scene(root, WIDTH, HEIGHT);
                Game game = new Game(gen, root);
                scene.setOnKeyPressed(game);

                stage.setTitle("Random Map");
                stage.setScene(scene);
                stage.show();

                StartGame(stage, game);
            }
        });

        LV1Button.setLayoutX(15);
        LV2Button.setLayoutX(315);
        LV3Button.setLayoutX(615);
        LV4Button.setLayoutX(915);
        randomLVButton.setLayoutX(0);
        quit.setLayoutX(915);

        LV1Button.setLayoutY(300);
        LV2Button.setLayoutY(300);
        LV3Button.setLayoutY(300);
        LV4Button.setLayoutY(300);
        randomLVButton.setLayoutY(600);
        quit.setLayoutY(600);

        levelSelectPane.getChildren().add(LV1Button);
        levelSelectPane.getChildren().add(LV2Button);
        levelSelectPane.getChildren().add(LV3Button);
        levelSelectPane.getChildren().add(LV4Button);
        levelSelectPane.getChildren().add(randomLVButton);
        levelSelectPane.getChildren().add(levelSelectText);
        levelSelectPane.getChildren().add(ReadMeText);
        levelSelectPane.getChildren().add(quit);


        stage.setTitle("Level Select");
        stage.setScene(levelSelectScene);
        stage.show();
    }


    /**
     * Starts the timeline for the game ticks
     *
     * @param stage the stage to draw to
     * @param game the game to start
     */
    private void StartGame(Stage stage, Game game) {

        LoopEventHandler handler = new LoopEventHandler(game, stage);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), handler), new KeyFrame(Duration.seconds(0.1)));

        handler.setTimeline(timeline);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
