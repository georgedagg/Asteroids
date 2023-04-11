package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Game {
	
    private Stage stage;
    private String playerName;
    private int score = 0;
    private int life = 3;
    private int time = 0;
    private Label scoreLabel;
    private Label lifeLabel;
    private Label timeLabel;
    private int numAsteroids;
    private Pane pane;
    private List<Asteroid> asteroids;
    private Ship ship;

    public Game(Stage stage, String playerName) {
        this.stage = stage;
        this.playerName = playerName;
        this.scoreLabel = new Label("Score: 0");
        this.lifeLabel = new Label("Life: 3");
        this.timeLabel = new Label("Time: 0");
    }
    
    public void updateScore() {
    	score += 1;
    	scoreLabel.setText("Score: " + score);
    }
    
    public void updateLife() {
    	life -= 1;
    	lifeLabel.setText("Life: " + life);
    }
    
    public void updateTime() {
    	time += 1;
    	timeLabel.setText("Time: " + time);
    }
    
    public List<Asteroid> createAsteroids(int numAsteroids, List<Asteroid> existingAsteroids) {
		for(int i=0; i<=numAsteroids; i++) {
			Asteroid newAsteroid = this.addAsteroid();
			existingAsteroids.add(newAsteroid);
		}
		return existingAsteroids;
    }
    
    public Ship createShip() {
    	this.ship = new Ship(300,200);
    	return ship;
    }
    
    public Asteroid addAsteroid() {
        Random rnd = new Random();
        int x, y;
        double shipX = ship.getCharacter().getTranslateX();
        double shipY = ship.getCharacter().getTranslateY();
        int safeDistance = 100; 

        do {
            x = rnd.nextInt(600);
            y = rnd.nextInt(400);
        } while (Math.sqrt(Math.pow(x - shipX, 2) + Math.pow(y - shipY, 2)) < safeDistance);

        Asteroid newAsteroid = new Asteroid(x, y);
        return newAsteroid;
    }
    
    public void levelUp() {
        int additionalAsteroids = (int) Math.ceil(numAsteroids * 0.5); // Increase the number of asteroids by 10%
        numAsteroids += additionalAsteroids;
        List<Asteroid> newLevelAsteroids = createAsteroids(numAsteroids, asteroids);
        newLevelAsteroids.stream().skip(newLevelAsteroids.size() - additionalAsteroids).map(Asteroid::getCharacter).forEach(pane.getChildren()::add);
    }

    
    public void start() throws Exception {
    	
    	// Track time and add 1 to the players score every second.
    	Timeline timelineScore = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
    		updateScore();
    		}));
        timelineScore.setCycleCount(Timeline.INDEFINITE);
        timelineScore.play();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
    		updateTime();
    		}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        Timeline levelUpTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> levelUp()));
        levelUpTimeline.setCycleCount(Timeline.INDEFINITE);
        levelUpTimeline.play();
        
    	// Create a new pane for the game to run in
		pane = new Pane();
		pane.setPrefSize(600, 400);
		
		scoreLabel.setLayoutX(10);
	    scoreLabel.setLayoutY(10);
	    lifeLabel.setLayoutX(10);
	    lifeLabel.setLayoutY(30);
	    timeLabel.setLayoutX(10);
	    timeLabel.setLayoutY(50);
	    pane.getChildren().addAll(scoreLabel, lifeLabel, timeLabel);
		
		// Create a new scene from the pane
		Scene scene = new Scene(pane);
		
		// Create a ship
		Ship ship = createShip();
		
		// create a list of Asteroids
		asteroids = new ArrayList<>();
		
		// Create a number of asteroid variable which we can increase for each level
		numAsteroids = 10;
		
		// Create asteroids
		createAsteroids(numAsteroids, asteroids);
		
		// Add elements to pane
		pane.getChildren().add(ship.getCharacter());
		asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
		
		// Create a list for bullets
		List<Bullet> bullets = new ArrayList<>();
		
		// Create a hash set to track the pressed keys
		Set<KeyCode> pressedKeys = new HashSet<>();
		
		// When keys are pressed, add them to the pressedKeys hash set
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				pressedKeys.add(event.getCode());
				// Print to the console to see what's happening
				System.out.println(pressedKeys);
			};
		});
		
		// When keys are released, remove them from the pressedKeys hash set
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			// handle is called to manage the event handler (key released)
			public void handle(KeyEvent event) {
				pressedKeys.remove(event.getCode());
				// Print to the console to see what's happening
				System.out.println(pressedKeys);
			};
		});
		
		// Animation Class - Runs all the animation for the game
		new AnimationTimer() {
			public void handle(long now) {
				// If the pressedKeys set contains the LEFT key
				// rotate the ship 5 degrees left continuously
				if(pressedKeys.contains(KeyCode.LEFT)) {
					ship.turnLeft();
				}
				
				// If the pressedKeys set contains the RIGHT key
				// rotate the ship 5 degrees right continuously
				if(pressedKeys.contains(KeyCode.RIGHT)) {
					ship.turnRight();
				}
				
				//If the pressedKeys set contains the UP key
				// move the ship forward
				if(pressedKeys.contains(KeyCode.UP)) {
					ship.accelerate();
				}
				
				// If the pressedKey set contains a SPACE key
				// create a bullet and add it to the pane
				if(pressedKeys.contains(KeyCode.SPACE)) {
					Bullet bullet = new Bullet((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
					bullet.getCharacter().setRotate(ship.getCharacter().getRotate());
					bullets.add(bullet);
					
					bullet.accelerate();
					bullet.setMovement(bullet.getMovement().normalize().multiply(3));
					pane.getChildren().add(bullet.getCharacter());
					
				}
				
				// Change the position of the ship based on the acceleration
				ship.move();
				asteroids.forEach(asteroid -> asteroid.move());
				bullets.forEach(projectile -> projectile.move());
				
				asteroids.forEach(asteroid ->{
					if(ship.collide(asteroid)) {
						// display a game over screen here
						System.out.print("Collision. Game Over");
						stop();
						timeline.stop();
						levelUpTimeline.stop();
					}
					
				});
			}
		}.start(); 
		
		stage.setScene(scene);
		stage.show();

    }
}

