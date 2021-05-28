import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;

public class Main extends Application {

	// LevelReader class allows us to read levels from existing files at levels
	// folders
	LevelReader levelReader = new LevelReader();
	// Current Level will be shown on top pane
	int currentLevel = 1;
	// Score will be shown on top pane.
	int score = 0;
	// levelArr holds the integer values of boxes such as empty= 0,wall=
	// 1,mirror=1,wood=2
	int[][] levelArr = levelReader.readLevel(currentLevel);
	// totalNumOfDestroyedBoxes holds the values of number of destroyed boxes used.
	// It is used for debugging.
	int totalNumOfDestroyedBoxes = 0;

	static Label highScore=new Label();
	
	Stage mainStage;

	// JavaFX style scripts for all types of boxes
	String wallBoxStyle = "-fx-background-color: transparent;" + "-fx-border-color:black;\n"
			+ "    -fx-border-width: 2, 1;\n" + "    -fx-border-style: solid, segments(1, 2);\n"
			+ "    -fx-border-radius: 0, 0;\n";

	String emptyBoxStyle = "-fx-background-color: white;" + "-fx-border-color: #94928e;\n"
			+ "    -fx-border-width: 3, 1;\n" + "    -fx-border-style: solid, segments(1, 2);\n"
			+ "    -fx-border-radius: 0, 0;\n";

	String mirrorBoxStyle = "-fx-background-color: #51affc;" + "-fx-border-color: #110f66;\n"
			+ "    -fx-border-width: 3, 1;\n" + "    -fx-border-style: solid, segments(1, 2);\n"
			+ "    -fx-border-radius: 0, 0;\n";

	String woodBoxStyle = "-fx-background-color: #e874d4;" + "-fx-border-color: #6e005b;\n"
			+ "    -fx-border-width: 3, 1;\n" + "    -fx-border-style: solid, segments(1, 2);\n"
			+ "    -fx-border-radius: 0, 0;\n";

	// The center pane that will hold boxes
	BorderPane mainPane = new BorderPane();
	// In north pane there will be Score, Level and Highscore stats
	BorderPane northPane = new BorderPane();
	// In south pane there will be precise clicking stats such as how many boxes are
	// destroyed.
	BorderPane southPane = new BorderPane();
	// Boxes pane will hold the 10x10 total 100 boxes array
	GridPane boxesPane = new GridPane();

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		GridPane boxesPane = createBoxes(levelArr);
		mainStage = primaryStage;
		primaryStage.setScene(initializePanes());
		primaryStage.setResizable(false);
		primaryStage.setTitle("Java Fx Game Project");
		primaryStage.show();
	}

	// Initializing panes. Assigning height and weight values for each element.
	// Setting style and titles
	Scene initializePanes() {
				
		readHighestScore();
		
		northPane.setPrefWidth(400);
		northPane.setPrefHeight(25);

		northPane.setLeft(new Label("Level #" + currentLevel));
		northPane.setCenter(new Label("Score: " + score));
		northPane.setRight(highScore);
		northPane.setStyle("-fx-border-color:black;\n" + "    -fx-border-width: 2, 1;\n"
				+ "    -fx-border-style: solid, segments(1, 2);\n" + "    -fx-border-radius: 0, 0;\n");

		southPane.setPrefHeight(25);
		southPane.setPrefWidth(400);

		southPane.setCenter(new Label("Game Started!"));
		southPane.setStyle("-fx-border-color:black;\n" + "    -fx-border-width: 2, 1;\n"
				+ "    -fx-border-style: solid, segments(1, 2);\n" + "    -fx-border-radius: 0, 0;\n");

		boxesPane.setStyle("-fx-background-color: #525050");
		boxesPane.setHgap(5);
		boxesPane.setVgap(5);
		boxesPane.setPadding(new Insets(5, 5, 5, 5));

		mainPane.setCenter(boxesPane);
		mainPane.setTop(northPane);
		mainPane.setBottom(southPane);
		Scene scene = new Scene(mainPane, 400, 450);
		return scene;

	}

	/*
	 * Creating boxes for boxespane. After each successful click this method will be
	 * called and it'll create new boxes accoring to levelArr values. After each
	 * level is finished this method will. Also this method will be callef if the
	 * level is finished.
	 */
	GridPane createBoxes(int[][] levelArr) {

		checkIsGameFinished();
		checkIsLevelFinished();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Button b = new Button();

				switch (levelArr[i][j]) {
				case 0:
					b.setStyle(wallBoxStyle);
					break;
				case 1:
					b.setStyle(emptyBoxStyle);
					break;
				case 2:
					b.setStyle(mirrorBoxStyle);
					break;
				case 3:
					b.setStyle(woodBoxStyle);
					break;
				default:
					b.setStyle(emptyBoxStyle);
				}

				b.setDisable(false);
				b.setPrefHeight(40);
				b.setPrefWidth(40);
				b.setOnAction((e) -> boxPressed(GridPane.getRowIndex((Node) e.getSource()),
						GridPane.getColumnIndex((Node) e.getSource())));
				boxesPane.add(b, i, j);
			}
		}

		return boxesPane;
	}

	/*
	 * This method is called every time a box is clicked. It'll call a method called
	 * "checkNearbyBoxes" and it'll assign a score according to the output of this
	 * method. *
	 */
	void boxPressed(int row, int column) {

		// Calling checkNearbyBoxes method if the value of the clicked box is greater
		// than 1(Wood or Mirror).
		if (levelArr[column][row] > 1) {
			// Getting number of destroyed boxes by checkNearbyBoxes method. Assigning a new
			// score value and changing bottom text.
			switch (checkNearbyBoxes(row, column)) {

			case 1:
				score -= 3;
				southPane.setCenter(new Label("1 box destroyed -3 points!"));
				break;
			case 2:
				score -= 2;
				southPane.setCenter(new Label("2 boxes destroyed -2 points!"));
				break;
			case 3:
				score += 1;
				southPane.setCenter(new Label("3 boxes destroyed +1 points!"));
				break;
			case 4:
				score += 2;
				southPane.setCenter(new Label("4 boxes destroyed +2 points!"));
				break;
			case 5:
				score += 4;
				southPane.setCenter(new Label("5 boxes destroyed +4 points!"));
				break;

			}

			System.out.println(
					row + " " + column + " total " + totalNumOfDestroyedBoxes + " destroyed" + " total score=" + score);
			// Clearing boxes pane so we can create new boxes.
			boxesPane.getChildren().clear();
			northPane.setCenter(new Label("Score:" + score));
			// Creating new boxes accoing to the values of levelArr
			createBoxes(levelArr);

		}

	}

	/*
	 * This method will be called everytime when clicked box has value greater than 1(Wood or mirror.) 
	 * This method will check nearby positions and clicked position then it'll change levelArr values accordingly.
	 */
	int checkNearbyBoxes(int row, int column) {
		
		int numOfDestroyedBoxes = 0;
		//Starting from left of the clicked box this loop will check the position itself and right and left of the position.
		for (int i = -1; i < 2; i++) {
			if (levelArr[column + i][row] > 1) {
				levelArr[column + i][row]--;
				numOfDestroyedBoxes++;
			}
		}
		
		//Starting from bottom of the box it'll check the up and bottom of the clicked position. But of course it'll pass the 
		//clicked position because we already checked it in the first loop.
		for (int i = -1; i < 2; i++) {
			if (i == 0) {
				continue;
			} else if (levelArr[column][row + i] > 1) {
				levelArr[column][row + i]--;
				numOfDestroyedBoxes++;
			}
		}
		return numOfDestroyedBoxes;

	}

	//A method for checking if the game is finished. If game is finished it'll call a method which show the ending screen.
	void checkIsGameFinished() {
		
		//If the current level is 5(Max level) and the value of isLevelFinished method is true it'll finish the game.
		if (currentLevel == 5 && isLevelFinished() == true) {

			System.out.println("Game finished");
			showEndGameScreen();

		}

	}
	//This method will show a popup screen for user input. It'll ask for users name. Then it'll record the highscore of the user.
	void showEndGameScreen() {

		TextInputDialog dialog = new TextInputDialog("");

		dialog.setTitle("Congratulations");
		dialog.setHeaderText("Congratulations, you have finished game with Score:" + score);
		dialog.setContentText("Please enter your name:");

		ScoreWriter scoreWriter = new ScoreWriter();

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			scoreWriter.writeNewHighScore(result.get() + ": " + score);
		}
		showHighScores();

	}
	//A method for creating another popup screen to show all recorded highscores.
	void showHighScores() {

		ScoreWriter scoreWriter = new ScoreWriter();
		System.out.println(scoreWriter.getAllScores());

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("HighScores Table");
		alert.setHeaderText("All High Scores");
		alert.setContentText(scoreWriter.getAllScores());

		alert.showAndWait();

		System.exit(0);

	}
//A method for checking if game is finished. It'll check all of the boxes and change the level if the value of all boxes are smaller than2
	void checkIsLevelFinished() {

		if (isLevelFinished() == true) {

			southPane.setCenter(new Label("Congratulations level " + currentLevel + " finished. --> Next Level"));
			currentLevel++;
			levelArr = levelReader.readLevel(currentLevel);
			northPane.setLeft(new Label("Level #" + currentLevel));

		}

	}
	//A small func thath will check the values of boxes and return the value accordingly.
	boolean isLevelFinished() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				if (levelArr[i][j] > 1) {
					return false;
				}

			}
		}
		return true;

	}

	//A method for reading highest score and assigning it the the highscore label
	void readHighestScore() {
		int topscore=0;
		ScoreWriter gethigh = new ScoreWriter();
		String allscores = gethigh.getAllScores();
		for(int i =0;i<allscores.split("\n").length;i++) {
			//if(allscores.split("\n")[i].equals("")) continue;
			if(topscore<Integer.parseInt(allscores.split("\n")[i].split(" ")[1])) topscore=Integer.parseInt(allscores.split("\n")[i].split(" ")[1]);
		}
		highScore.setText("High Score: "+topscore);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}