import java.util.ArrayList;
import java.util.HashMap;

/*************
 * classe Main
 *************/

public class Main {

	//Nombre de requetes pour le warmup
	public static int X;
	//Taille du cache
	public static int N;
	
	public static void main(String[] args) {
		
		//On recupere les arguments X (temps de warm up) et N (taille du cache) passes en arguments
		X = Integer.parseInt(args[0]);
		N = Integer.parseInt(args[1]);

		//Check les arguments args et lance la bonne methode : LRU, LFU, Complex LRU ou Complex LFU
		//On cree le cache avec la taille voulue
		ArrayList<DataLRU> cacheLRU =  new ArrayList<DataLRU>(N);
		HashMap<String, DataLRU> mapLRU = new HashMap<String, DataLRU>(N);
		
		//Cree le cache LRU
		CacheLRU task1LRU = new CacheLRU(cacheLRU, mapLRU, N, X);
		
		IOFile.readFile(task1LRU);
		
		//Apres l'execution, cree et ecrit dans le fichier cache_lru.txt
		IOFile.writeLRU(task1LRU.getCache());
		
		//Affiche le Hitrate
		System.out.println("LRU Hit rate:" + " " + task1LRU.computeHitRateLRU() + "%");

		
	}
	
	
	

}
