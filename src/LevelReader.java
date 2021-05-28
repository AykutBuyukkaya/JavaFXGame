import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Levelreader class allows us the read data from the existsing files from Levels folders.
 *  * */
public class LevelReader {
	
	private int level;
	//Precise path for each level file
	private String levelPath;
	//Template of the levelArr data in Main class. It'll be returned.
	private int[][] levelArr = new int[10][10];
	//Each wall types is put to a single string array for easy usage.
	String[] wallTypes = { "Wall" ,"Empty", "Mirror", "Wood" };

	//Default constructor for Levelreader class
	public LevelReader() {

		System.out.println("Creating a LevelReader Object");

	}
	//Initializing levelArr by making each element 0
	void initLevelArr() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				levelArr[i][j] = 0;
			}
		}

	}
//Reading data from existing files then assigning them to the levelArr. 
	int[][] readLevel(int level) {

		initLevelArr();

		try {

			File levelFile = new File("levels/level" + level + ".txt");
			Scanner scan = new Scanner(levelFile);
			String tempString;
			String[] dividedValues;
			while (scan.hasNextLine()) {
				tempString = scan.nextLine();
				dividedValues = tempString.split(",");

				for (int i = 0; i < wallTypes.length; i++) {
					if (dividedValues[0].equals(wallTypes[i])) {
						levelArr[Integer.parseInt(dividedValues[2])][Integer.parseInt(dividedValues[1])] = i;
					}
				}

			}
			return levelArr;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error! Level file cannot be found!");
			e.printStackTrace();
			return levelArr;
		}

	}

}
