package application;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/*TO DO:
 * BULLET CLASS
 * ALIEN CLASS
 * ASTEROIDS DISAPPEAR
 *  */

public class Main extends Application {
	
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	
	@Override
	public void start(Stage stage) throws Exception{
		
		// Create a new pane for the game to run in
		Pane pane = new Pane();
		pane.setPrefSize(600, 400);
		
		// Create a hash set to track the pressed keys
		Set<KeyCode> pressedKeys = new HashSet<>();
		
		// Create a new ship using the Ship class
		Ship ship = new Ship(300,200);
		
		// Create a a number of asteroids
		List<Asteroid> asteroids = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			Random rnd = new Random();
			Asteroid asteroid = new Asteroid(rnd.nextInt(600), rnd.nextInt(400));
			asteroids.add(asteroid);
		}
		
		// Create a list for bullets
		List<Bullet> bullets = new ArrayList<>();
		
		// Adds the elements to the pane
		pane.getChildren().add(ship.getCharacter());
		asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
		
		// Create a new scene from the pane
		Scene scene = new Scene(pane);
		
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
						stop();
					}
					
				});
			}
		}.start();
		
		// Set the title and display the scene
		stage.setTitle("Asteroids");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
