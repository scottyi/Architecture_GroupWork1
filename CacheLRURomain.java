import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CacheLRU extends Cache{
	
	private HashMap<String, DataLRU> map; //Bon pour facilement stocker et retrouver les valeurs
	private ArrayList<DataLRU> cache; //Bon car dynamique
	private int XLRU;
	private int numberOfHits;
	private double hitRateLRU;
	
	public CacheLRU(ArrayList<DataLRU> c, HashMap<String, DataLRU> mapLRU, int size, int warmup){
		super(size, warmup);
		this.map = mapLRU;
		this.cache = c;
		this.XLRU = 0;
		this.numberOfHits = 0;
		this.hitRateLRU = 0.0;
	}
	
	public ArrayList<DataLRU> getCache(){
		return this.cache;
	}

	public void setCache(ArrayList<DataLRU> newC) {
		this.cache = newC;
	}
	
	public Map<String, DataLRU> getMap(){
		return this.map;
	}

	public void setMap(HashMap<String, DataLRU> newM) {
		this.map = newM;
	}
	
	public void incrementXLRU(){
		this.XLRU += 1;
	}
	
	public int getXLRU(){
		return this.XLRU;
	}
	
	public void incrementHits(){
		this.numberOfHits += 1;
		System.out.println("THIS IS THE NUMBER OF HITS" + " " + this.numberOfHits);
	}
	
	public int getHits(){
		return this.numberOfHits;
	}
	
	public double computeHitRateLRU(){
	    this.hitRateLRU = (double) ((double) this.getHits() / (double)this.getAccesses())*100.0;
		return this.hitRateLRU;
	}
	
	public void lruCaching (DataLRU newData) {
		System.out.println(this.getXLRU());
		System.out.println(this.getHits());
		System.out.println(this.getAccesses());

		if (this.getMap().containsKey(newData.getUrl()) == true){ // Cas ou le cache contient la donnee
			
			System.out.println("Data is in the cache and accessible, counted as a hit");
			if (this.getXLRU() < this.getX()){
				XLRU++;
			}
			else{
				this.incrementHits();
				this.incrementAccesses();
			}
			
		}
		else{
			if ((this.getCache().size() < this.getN()) == true /*&& (newData.getSize() != cache.get(cache.indexOf(newData)).getSize())*/){ //Cas ou le cache ne continent pas la donnee
																											 		 //mais a de la place libre
				System.out.println("Data is not in the cache, adding new data to the cache");		
				this.getCache().add(0, newData); //Donnee la plus recente = ajoutee en debut de liste
				this.getMap().put(newData.getUrl(), newData); //Add the data to the hashmap
				if (this.getXLRU() < this.getX()){
					this.incrementXLRU();
				}
				else{
					System.out.println("Counted as a miss");
					this.incrementAccesses();
				}
				
			}
			else { // Cas ou le cache ne contient pas la donnee et n'a plus de place libre
				System.out.println("Data is not in the cache, and cache is full");
				System.out.println("Removing oldest data and adding new data to the cache, counted as a miss");
				this.getMap().remove((this.getCache().get(this.getCache().size()-1)).getUrl()); //Enleve le oldest element de la hashmap
				this.getCache().remove(this.getCache().size()-1); //Oldest = le dernier dans l'arraylist
				this.getCache().add(0, newData);//Donnee la plus recente = ajoutee en debut de liste
				this.getMap().put(newData.getUrl(), newData); //Add the new data to the hashmap
				if (this.getXLRU() < this.getX()){
					this.incrementXLRU();
				}
				else{
					this.incrementAccesses();
				}
			}
		}
	}


}
