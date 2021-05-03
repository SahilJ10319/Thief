package com.cmpt276.Game;
import com.cmpt276.Map.*;
import com.cmpt276.Character.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

/**
 * Game class is a super class which implements standard game implementations
 */
public class Game implements EventHandler<KeyEvent> {
    private Thief thief;
    private int score;
    private TileMap map;
    private List<Guard> enemies;
    private boolean isopen = true;
    private KeyEvent currentEvent = null;
    private String scoreString;
    private Text scoreTxt;

    private Image ThiefStationaryLeft, MovGuardStationaryLeft, stairsUp, stairsDown;

    private ImageView Thief, stairsUpImg, stairsDownImg;
    private List<ImageView> MovGuards;

    private static Random gameRandom = null;

    /**
     * instance of a random generator
     * @return gameRandom
     */
    public static Random getGameRandom() {
        if (gameRandom == null)
            gameRandom = new Random(System.currentTimeMillis());
        return gameRandom;
    }

    /**
     * @return  score
     */
    public int getScore(){
        return score;
    }

    /**
     * loads all necessary Images initial start of game
     */
    private void loadImages(){
        ThiefStationaryLeft = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/LeftStationary1.png"));
        Thief = new ImageView(ThiefStationaryLeft);

        MovGuardStationaryLeft = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/LeftStationary1.png"));
        MovGuards = new ArrayList<ImageView>();

        stairsUp = new Image(this.getClass().getResourceAsStream("/Images/MapImages/StaircaseUp.png"));
        stairsDown = new Image(this.getClass().getResourceAsStream("/Images/MapImages/StaircaseDown.png"));
        stairsUpImg = new ImageView(stairsUp);
        stairsDownImg = new ImageView(stairsDown);
    }

    /**
     * Constructor only for testing.
     * 
     * @param gen
     */
    public Game(MapGenerator gen){
        Direction direction = Direction.Neutral;
        this.map = new TileMap(gen);
        this.thief = new Thief(new Position(0, map.getSize().getY() - 1), direction, map, this);
        this.enemies = gen.getGuards(map, thief);
    }

    /**
     * Constructs Game using parameters.
     * Creates, sets, and adds scoreText, Thief, enemies, map, and stairs to appropriate locations.
     * 
     * @param gen   Map the user will be playing.
     * @param root  Pane the game will be running on.
     */
    public Game(MapGenerator gen, Pane root) {
        loadImages();
        scoreTxt = new Text();
        scoreString = "Score: "+ (score);
        scoreTxt.setText(scoreString);
        scoreTxt.setX(100);
        scoreTxt.setY(30);
        scoreTxt.setFont(Font.font("Verdana", 18));
        root.getChildren().add(scoreTxt);
        root.getChildren().add(Thief);
        Direction direction = Direction.Neutral;
        this.map = new TileMap(gen);
        this.thief = new Thief(new Position(0, map.getSize().getY() - 1), direction, map, this);
        this.enemies = gen.getGuards(map, thief);

        for (int i = 0; i < this.enemies.size(); i++) {
            ImageView MovGuard = new ImageView(MovGuardStationaryLeft);
            this.MovGuards.add(MovGuard);
            root.getChildren().add(MovGuard);
        }

        map.drawInitial(root);
        stairsUpImg.setX(map.getSize().getX()*60+60);
        stairsUpImg.setY(60);
        stairsDownImg.setX(0);
        stairsDownImg.setY(map.getSize().getY()*60);
        root.getChildren().add(stairsDownImg);
        root.getChildren().add(stairsUpImg);

    }

    /**
     * closes the game
     */
    public void close() { //once gameover = true, set isopen to false;
        this.isopen = false;
    }

    /**
     * @return whether or not the game is open to play.
     */
    public boolean isOpen() {
        return this.isopen;
    }

    /**
     * sets game's thief to param newThief for testing
     * 
     * @param newThief  new Thief for Game's thief to be set to.
     */
    public void setThief(Thief newThief){
        this.thief = newThief;
    }

    /**
     * adds number to score and checks if score is less than 0 afterwards.
     * 
     * @param number    integer for score to added to.
     */
    public void addscore(int number) {
        score += number;
        if (score < 0){
            close();
        }
    }

    /**
     * @param number to set Score to.
     */
    public void setScore(int number) {
        score = number;
    }

    /**
     * Checks if Thief has met necessary requirements to win game.
     * 
     * @return 
     */
    public boolean wonGame() {
        Position thiefPos = thief.getPos();
        Position mapSize = map.getSize();

        if (thiefPos.getX() == mapSize.getX() - 1 && thiefPos.getY() == 0) {
            return !map.hasRemainingCoins();
        }
        return false;
    }
    /**
    * @return  map
     */

    public TileMap getMap() {
         return this.map;
    }

    /**
     * @return  thief
     */
    public Thief getThief(){
        return this.thief;
    }

    /**
     * Game function which continuously updates
     * the score, character and guard state
     */
    public void play(){
        scoreString = "Score: "+ (score);
        scoreTxt.setText(scoreString);
        this.map.update();
        this.map.updateUIPos(null);
        thief.update();
        thief.updateUIPos(Thief);
        for (int i = 0; i < this.enemies.size(); i++) {
            enemies.get(i).update();
        }
        for (int i = 0; i < this.enemies.size(); i++) {
            enemies.get(i).updateUIPos(MovGuards.get(i));
        }

        if (wonGame()) {
            close();
        }
    }

    /**
     * @param event
     * captures most recent event 
     */
    @Override
    public void handle(KeyEvent event) {
        this.currentEvent = event;
    }

    /**
     * @return most recent event
     */
    public KeyEvent getNextEvent() {
        KeyEvent tmp = this.currentEvent;
        this.currentEvent = null;
        return tmp;
    }
}
