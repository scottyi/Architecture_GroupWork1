import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*************
 * classe Main
 *************/

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Test de la classe IOFile(ça fonctionne)
		IOFile io = new IOFile("src/trace.txt");
		String line = io.readNextLine();
		while(line!=null){
			System.out.println(line);
			line = io.readNextLine();
		}
		io.Close();
		
	}
}
