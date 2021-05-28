import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/*
 * ScoreWriter class allows us the write highScores after the game is finished.
 * */
public class ScoreWriter {
//Default constructor for ScoreWriter Class
	public ScoreWriter() {

		System.out.println("Initializing ScoreWriter!");

	}
//Writing a new highscore to the highscores.txt file
	void writeNewHighScore(String text) {
		try {
			File temp = new File("highScores.txt");
			FileWriter highScoresFile = new FileWriter(temp, true);
			highScoresFile.write(text + "\n");
			highScoresFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//A function for returning all existing highscores 
	String getAllScores() {

		String result = "";

		try {
			File temp = new File("highScores.txt");
			Scanner scan = new Scanner(temp);

			while (scan.hasNextLine()) {
				result += scan.nextLine() + "\n";
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

}
