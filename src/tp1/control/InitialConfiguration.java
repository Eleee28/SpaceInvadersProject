package tp1.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InitialConfiguration {
		 	
	private ArrayList<String> descriptions;
	
	public ArrayList<String> getDescriptions() {
		return descriptions;
	}

	public static final InitialConfiguration NONE = new InitialConfiguration();
	
	/*
	 * Reads the list of aliens from the specified file
	 * */
	
	public static InitialConfiguration readFromFile(String filename) throws FileNotFoundException, IOException {
		InitialConfiguration conf = new InitialConfiguration();
		
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(filename));
			String line;
			conf.descriptions = new ArrayList<String>();
			
			while ((line = buffer.readLine()) != null) {
				conf.descriptions.add(line);	
			}
			
			buffer.close();

		}
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found: \"" + filename + "\"");
		}
		catch (IOException e) {
			throw new IOException("Error in the input data");
		}

		return conf;
	}
	
}
