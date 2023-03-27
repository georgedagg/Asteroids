package application;

import javafx.scene.shape.Polygon;

public class Bullet extends Character{
	
	public Bullet(int x, int y) {
		super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
		
	}
	
	public void move() {
		super.move();
		if (this.getCharacter().getTranslateX() < 0 || this.getCharacter().getTranslateX() > 0) {
			
		}
	}
}