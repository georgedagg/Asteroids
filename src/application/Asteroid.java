package application;

import java.util.Random;

// Asteroid class extends the Character class
public class Asteroid extends Character{
	
	private double rotate;
	
	// Create a new asteroid from the super class
	public Asteroid(int x, int y) {
		
		// Use the Shape class to create an asteroid with a random size
		super(new Shape().newShape(), x, y);
		
		Random rnd = new Random();
		
		// Set the initial rotation of the asteroid
		// this makes sure asteroids have different rotation speeds.
		super.getCharacter().setRotate(rnd.nextInt(360));
		
		// Set the number of accelerations for the asteroid
		int speed = 1 + rnd.nextInt(10);
		
		// Implement the accelerations
		for(int i =0; i < speed;i ++) {
			accelerate();
		}
		
		// Sets the rotation speed
		this.rotate = 0.5 - rnd.nextDouble();
	}
	
	public void move() {
		super.move();
		super.getCharacter().setRotate(super.getCharacter().getRotate() + rotate);
	}

}