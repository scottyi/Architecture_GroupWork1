import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*************
 * classe Main
 *************/

public class Main {
	
	//Creation de l'arraylist representant le cache
	public static ArrayList<DataLRU> cache; //=> cool car dynamique (rapport)
	public static Map<String, DataLRU> mapCache;
	//Creation du compteur retenant le nombre de hit en LRU
	public static int numberOfHitsLRU;
	//Creation du compteur retenant le nombre de hit en LFU
	public static int numberOfHitsLFU;
	//Creation du compteur retenant le nombre d'acces
	public static int numberOfAccesses;
	//Creation du compteur pour le warmup
	public static int XLRU;
	//Nombre de requetes pour le warmup
	public static int X;
	//Taille du cache
	public static int N;
	
	public static int TEST;
	
	public static void main(String[] args) {
		
		//On recupere les arguments X (temps de warm up) et N (taille du cache) passes en arguments
		X = Integer.parseInt(args[0]);
		N = Integer.parseInt(args[1]);
		
		//On initialise les variables
		numberOfHitsLRU = 0;
		numberOfHitsLFU = 0;
		numberOfAccesses = 0;
		XLRU = 0;
		TEST = 0;

		//Check les arguments args et lance la bonne methode : LRU, LFU, Complex LRU ou Complex LFU
		//On cree le cache avec la taille voulue
		cache =  new ArrayList<DataLRU>(N);
		mapCache = new HashMap<String, DataLRU>(N);
		
		IOFile.readFile();
		
		//Apres l'execution, cree et ecrit dans le fichier cache_lru.txt
		IOFile.writeLRU(cache);
		
		//Affiche le Hitrate
		printHitRateLRU();
		System.out.println(cache.size());
		System.out.println("********************************************************************************************************");
		System.out.println(TEST);
		System.out.println("********************************************************************************************************");

		
	}
	
	public static void printHitRateLRU(){
	    double temp = numberOfHitsLRU / numberOfAccesses*100.0;
		System.out.println("LRU Hit rate:" + " " + temp + "%");
	}
	
	public static void printHitRateLFU(){
		double temp = numberOfHitsLFU / numberOfAccesses*100.0;
		System.out.println("LFU Hit rate:" + " " + temp + "%");
	}
	
	public static void lruCaching (DataLRU newData) {
		System.out.println(XLRU);
		System.out.println(numberOfHitsLRU);
		System.out.println(numberOfAccesses);

		if (mapCache.containsKey(newData.getUrl()) == true){ // Cas ou le cache contient la donnee
			
			System.out.println("Data is in the cache and accessible, counted as a hit");
			if (XLRU < X){
				XLRU++;
			}
			else{
				numberOfHitsLRU++;
				numberOfAccesses++;
			}
			
		}
		else{
			if ((cache.size() < N) == true /*&& (newData.getSize() != cache.get(cache.indexOf(newData)).getSize())*/){ //Cas ou le cache ne continent pas la donnee
																											 		 //mais a de la place libre
				System.out.println("Data is not in the cache, adding new data to the cache");		
				cache.add(0, newData); //Donnee la plus recente = ajoutee en debut de liste
				mapCache.put(newData.getUrl(), newData); //Add the data to the hashmap
				if (XLRU < X){
					XLRU++;
				}
				else{
					System.out.println("Counted as a miss");
					numberOfAccesses++;
				}
				
			}
			else { // Cas ou le cache ne contient pas la donnee et n'a plus de place libre
				TEST++;
				System.out.println("Data is not in the cache, and cache is full");
				System.out.println("Removing oldest data and adding new data to the cache, counted as a miss");
				mapCache.remove((cache.get(cache.size()-1)).getUrl()); //Enleve le oldest element de la hashmap
				cache.remove(cache.size()-1); //Oldest = le dernier dans l'arraylist
				cache.add(0, newData);//Donnee la plus recente = ajoutee en debut de liste
				mapCache.put(newData.getUrl(), newData); //Add the new data to the hashmap
				if (XLRU < X){
					XLRU++;
				}
				else{
					numberOfAccesses++;
				}
			}
		}
	}
	

}
