package application;

import javafx.scene.shape.Polygon;

// Ship class extends the character class
public class Ship extends Character {
	
	// create a new ship using the super class
	public Ship(int x, int y) {
		super(new Polygon(-5,-5,10,0,-5,5), x, y);
		
		
	}
	
}