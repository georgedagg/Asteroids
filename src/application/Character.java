package application;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character {

    private Polygon character;
    private Point2D movement;

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    public Polygon getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }
    
    public Point2D getMovement(){
    	return this.movement;
    }
    
    public void setMovement(Point2D x) {
    	this.movement = x;
    }
    
    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
        
        // If ship moves off the left of the screen, adjust horizontal position to max width
        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + Main.WIDTH);
        }
        
        // If ship moves of the right of the screen, adjust horizontal position to 0
        if (this.character.getTranslateX() > Main.WIDTH) {
            this.character.setTranslateX(this.character.getTranslateX() % Main.WIDTH);
        }
        
        // If ship moves off the top of the screen, adjust vertical position to max height
        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + Main.HEIGHT);
        }
        
        // If ship moves off the bottom of the screen, adjust vertical position to 0
        if (this.character.getTranslateY() > Main.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % Main.HEIGHT);
        }

    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
    
    public boolean collide(Character other) {
    	Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
    	return collisionArea.getBoundsInLocal().getWidth() != -1;
  
    }
}
