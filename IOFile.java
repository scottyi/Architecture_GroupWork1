import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/***************
 * classe IOFile
 ***************/

public class IOFile {
	
	// attribut de IOFile
	private BufferedReader reader;
	private String line; // line répresente une ligne courante du fichier
	
	//constructeur de IOFile
	public IOFile(String in){
		try{
			reader = new BufferedReader(new FileReader(in));
		}catch(FileNotFoundException e){
			System.err.println("Unable to open file : " + in);	
			e.printStackTrace();
		}
	}
	
	/* Cette méthode lit une ligne du fichier
	 * 
	 */
	public String readNextLine(){
		String s=null;
		// Lecture d'une ligne du fichier
		try{
			s=reader.readLine();
		}catch (IOException e){
			System.err.println("Unable to read next line in input file");
			e.printStackTrace();
		} finally{
		// Si le fichier est terminé ç-à-d que reader.readLine()==null
			if(s==null){
				line = null;
			}
			else{
				line = s;
			}
		}
		return line;
	}
	
	/* Cette méthode ferme le flux (stream) et libère toutes les ressources
	 * du système qui lui ont été associées.
	 */
	public void Close(){
		try{
			reader.close();
		}catch(IOException e){
			System.err.println("Termination failed");
			e.printStackTrace();
		}
		
	}
}
