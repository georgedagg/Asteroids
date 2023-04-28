package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	private static Stage stage;
	
	// Link the stylesheet
	String css = this.getClass().getResource("style.css").toExternalForm();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    
		Main.stage = primaryStage;

	    // Create a VBox for the start screen
	    VBox startScreen = new VBox(10);
	    startScreen.setPrefSize(WIDTH, HEIGHT);
	    startScreen.getStyleClass().add("start-screen");

	    // Create a Label for the title of the game
	    Label titleLabel = new Label("Asteroids");
	    titleLabel.getStyleClass().add("title-label");

	    // Create a TextField for the user to enter their name
	    Label nameInputLabel = new Label("Enter your name: ");
	    nameInputLabel.getStyleClass().add("name-input-label");
	    TextField nameInput = new TextField();
	    nameInput.getStyleClass().add("name-input");

	    // Create a Button to start the game
	    Button startButton = new Button("Start");
	    startButton.setOnAction(e -> {
	        try {
	            startGame(nameInput.getText());
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    });
	    startButton.getStyleClass().add("start-button");
	    
	    String upArrow = "\u2191";
        String leftArrow = "\u2190";
        String rightArrow = "\u2192";
        String downArrow = "\u2193";
	    
        // Controls label
	    Label controls = new Label(upArrow + "= Accelerate\n" + leftArrow + "= Turn Left\n" + rightArrow + "= Turn Right\n" + downArrow + "= Decelerate\n" + "SPACE = Shoot\n" + "H = Hyper Jumper");
	    controls.getStyleClass().add("controls");
	    
	    // Game rules label
	    Label gameRules = new Label("Shoot the asteroids and live for as long as you can.\nYou have 3 lives. When you loose a life, your ship is invincible for 3 seconds.\nEvery 10,000 points you regain a life!");
	    gameRules.getStyleClass().add("game-rules");

	    // Add the components to the VBox
	    startScreen.getChildren().addAll(titleLabel, nameInputLabel, nameInput, startButton, controls, gameRules);
	    
	    // Load the stylesheet
    	Scene startScene = new Scene(startScreen);
    	startScene.getStylesheets().add(css);
    	stage.setTitle("Asteroids");
	    stage.setScene(startScene);
	    stage.show();
	}
	
	// Start game method called by start button
	public static void startGame(String playerName) throws Exception {
	    Game game = new Game(stage, playerName);
	    game.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}