package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		this.stage = primaryStage;
		
		// Create a VBox for the start screen
	    VBox startScreen = new VBox(10);
	    startScreen.setPrefSize(WIDTH, HEIGHT);

	    // Create a Label for the title of the game
	    Label titleLabel = new Label("Asteroids");
	    titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

	    // Create a TextField for the user to enter their name
	    TextField nameInput = new TextField();
	    nameInput.setPromptText("Enter your name");

	    // Create a Button to start the game
	    Button startButton = new Button("Start");
	    startButton.setOnAction(e -> {
	        try {
	            startGame(nameInput.getText());
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    });
	    
	    // Add the components to the VBox
	    startScreen.getChildren().addAll(titleLabel, nameInput, startButton);
	    
	    // Create and set the start screen scene
	    Scene startScene = new Scene(startScreen);
	    stage.setTitle("Asteroids");
	    stage.setScene(startScene);
	    stage.show();
		
	}
	
	private void startGame(String playerName) throws Exception {
	    Game game = new Game(stage, playerName);
	    game.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
