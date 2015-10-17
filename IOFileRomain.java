import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/***************
 * classe IOFile
 ***************/

public class IOFile {
	
	public static void readFile (){
		
		//Declare le buffered reader utilise pour ouvrir et lire le fichier
		BufferedReader buffRead = null;
		
		try{
			
			//Lecture du fichier ligne par ligne tant qu'il n'est pas vide
			String currentLine;
			buffRead = new BufferedReader(new FileReader("./trace.txt"));
			while ((currentLine = buffRead.readLine()) != null) {
				String temp[] = lineSplit(currentLine);
				DataLRU newData = new DataLRU(temp[0], Integer.parseInt(temp[1]));
				Main.lruCaching(newData);
			}
			System.out.println("THIS IS A TEST" + Main.numberOfHitsLRU / Main.numberOfAccesses);
			
		}
		
		//Catch les erreurs eventuelles lors de l'ouverture et lecture du fichier
		catch (IOException exc1){
			exc1.printStackTrace();
			System.out.println("Erreur ouverture et lecture");
		}
		
		//En dernier lieu, ferme de buffered reader quand le fichier touche a sa fin et catch les erreurs eventuelles
		finally {
			
			try {		
				if (buffRead != null) buffRead.close();
			}
			catch (IOException exc2) {
				exc2.printStackTrace();
				System.out.println("Erreur fermeture");
			}
			
		}
	}
	
	//A partir d'une ligne du fichier, cree un tableau contenant les deux parties de la ligne (url et size)
	public static String[] lineSplit (String line){
		String[] parts = line.split(" ");
		return parts;
	}
	
	public static void writeLRU(ArrayList<DataLRU> array) {
		
		File file = new File ("cache_lru.txt");
		
		try {
			PrintWriter printWrite = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (DataLRU d : array){
				printWrite.println(d.toString());
			}
			printWrite.close();
		}
		catch (IOException e){
			e.printStackTrace();
			System.out.println("Erreur ecriture");
		}
	}

}
