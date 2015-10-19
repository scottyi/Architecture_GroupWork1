import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CacheLRU extends Cache{
	
	private HashMap<String, DataLRU> map; //Bon pour facilement stocker et retrouver les valeurs
	private ArrayList<DataLRU> cache; //Bon car dynamique
	private int XLRU;
	private int currentSize;
	private int numberOfHits;
	private int numberOfHitsByte;
	private double hitRateLRU;
	private double hitByteRateLRU;
	
	public CacheLRU(ArrayList<DataLRU> c, HashMap<String, DataLRU> mapLRU, int size, int warmup){
		super(size, warmup);
		this.map = mapLRU;
		this.cache = c;
		this.XLRU = 0;
		this.numberOfHits = 0;
		this.hitRateLRU = 0.0;
		this.currentSize = 0;
		this.numberOfHitsByte = 0;
		this.hitByteRateLRU = 0;
	}
	
	public ArrayList<DataLRU> getCache(){
		return this.cache;
	}
	
	public int getCurrentSize(){
		return this.currentSize;
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
	
	public void incrementHitBytes(int bytes){
		this.numberOfHitsByte += bytes;
	}
	
	public int getHitBytes(){
		return this.numberOfHitsByte;
	}
	
	public void incrementHits(){
		this.numberOfHits += 1;
		System.out.println("THIS IS THE NUMBER OF HITS" + " " + this.numberOfHits);
	}
	
	public void incrementCurrentSize(int bytes){
		this.currentSize += bytes;
	}
	
	public void decrementCurrentSize(int bytes){
		this.currentSize -= bytes;
	}
	
	public int indexOfData (DataLRU d){ //Pas grave car IndexOf est de complexite O(N)
		int r = -1;
		for (int i=0; i<this.getCache().size(); i++){
			if (this.getCache().get(i).getUrl().equals(d.getUrl())) r=i;
		}
		return r;
	}
	
	public boolean containsData (DataLRU d) { //Pas grave car Contains est de complexite O(N)
		boolean r = false;
		for (int i=0; i<this.getCache().size(); i++){
			if (this.getCache().get(i).getUrl().equals(d.getUrl())) r=true;
		}
		return r;
	}
	
	public int getHits(){
		return this.numberOfHits;
	}
	
	public boolean isTooBig(int bytes){
		return (this.getN() < bytes);
	}
	
	public double computeHitRateLRU(){
	    this.hitRateLRU = (double) ((double) this.getHits() / (double)this.getAccesses())*100.0;
		return this.hitRateLRU;
	}
	
	public double computeHitByteRateLRU(){
	    this.hitByteRateLRU = (double) ((double) this.getHitBytes() / (double)this.getAccessesBytes())*100.0;
		return this.hitByteRateLRU;
	}
	
	public void lruCaching (DataLRU newData) {
		System.out.println(this.getXLRU());
		System.out.println(this.getHits());
		System.out.println(this.getAccesses());

		if (this.getMap().containsKey(newData.getUrl()) == true){ // Cas ou le cache contient la donnee
			
			System.out.println("Data is in the cache and accessible, counted as a hit");
			if (this.getXLRU() < this.getX()){
				this.incrementXLRU();
			}
			else{
				this.incrementHits();
				this.incrementAccesses();
			}
			
		}
		else{
			if ((this.getCache().size() < this.getN()) == true ){ //Cas ou le cache ne continent pas la donnee
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
	
	public void lruCachingComplexe (DataLRU newData) {

		System.out.println(this.getXLRU());
		System.out.println(this.getHits());
		System.out.println(this.getAccesses());

		if (this.getMap().containsKey(newData.getUrl()) == true){ // Cas ou le cache contient la donnee
			int index  = this.indexOfData(newData);
			if (newData.getSize() == this.getCache().get(index).getSize()) { // Cas ou les donnees ont la meme taille
				System.out.println("Data is in the cache and accessible, counted as a hit");
				if (this.getXLRU() < this.getX()){
					this.incrementXLRU();
				}
				else{
					this.incrementHits();				
					this.incrementHitBytes(newData.getSize());
					this.incrementAccesses();
					this.incrementAccessesBytes(newData.getSize());
				}
			}
			else{ //Cas ou les tailles sont differentes 
				System.out.println("Data is in the cache, but the sizes are different ! Data replaced and counted as a miss");
				DataLRU old = this.getCache().get(this.indexOfData(newData));
				//First case, there is room for the new version of the data
				if (this.isTooBig(newData.getSize())==false && ((this.getCurrentSize()-old.getSize())+newData.getSize() < this.getN())){
					this.getMap().remove(old.getUrl()); //Enleve l'ancien de la map
					int temp = this.indexOfData(newData);
					this.decrementCurrentSize(this.getCache().get(temp).getSize()); //Remove the old number of bytes from the cache size
					this.getCache().remove(temp); //Old value in the cache
					this.getCache().add(0, newData);//Donnee la plus recente = ajoutee en debut de liste
					this.getMap().put(newData.getUrl(), newData); //Add the new data to the hashmap
					this.incrementCurrentSize(newData.getSize()); //Add the new number of bytes to the cache size
				}
				//Second case, there is no room for the new version of the data
				else if(this.isTooBig(newData.getSize())==false && ((this.getCurrentSize()-old.getSize())+newData.getSize() > this.getN())){
					while((this.getCurrentSize()-old.getSize())+newData.getSize() > this.getN()){
				
						this.getMap().remove((this.getCache().get(this.getCache().size()-1)).getUrl()); //Enleve le oldest element de la hashmap
						this.decrementCurrentSize(this.getCache().get(this.getCache().size()-1).getSize()); //Remove number of bytes from the cache size
						this.getCache().remove(this.getCache().size()-1); //Oldest = le dernier dans l'arraylist
						
					}
					this.getMap().remove(old.getUrl()); //Enleve l'ancien de la map
					int temp = this.indexOfData(newData);
					if (temp != -1){ //Si il a pas deja ete enleve
						this.decrementCurrentSize(this.getCache().get(temp).getSize()); //Remove the old number of bytes from the cache size
						this.getCache().remove(temp); //Old value in the cache
					}
					this.getCache().add(0, newData);//Donnee la plus recente = ajoutee en debut de liste
					this.getMap().put(newData.getUrl(), newData); //Add the new data to the hashmap
					this.incrementCurrentSize(newData.getSize()); //Add the number of bytes to the cache size
				}
				//Third case, data simply too big for the cache
				else{
					System.out.println("Data too big for the cache, not stored and counted as a miss");
				}

				if (this.getXLRU() < this.getX()){
					this.incrementXLRU();
				}
				else{
					this.incrementAccesses();
					this.incrementAccessesBytes(newData.getSize());
				}
			}
			
		}
		else{
			if ((this.getCurrentSize() < this.getN()) == true && (this.getCurrentSize()+newData.getSize() < this.getN())){ //Cas ou le cache ne continent pas 
																														   //la donnee mais a de la place libre
				if (this.isTooBig(newData.getSize())==false ){																						 		
					System.out.println("Data is not in the cache, adding new data to the cache");		
					this.getCache().add(0, newData); //Donnee la plus recente = ajoutee en debut de liste
					this.getMap().put(newData.getUrl(), newData); //Add the data to the hashmap
					this.incrementCurrentSize(newData.getSize()); //Add the new number of bytes to the cache size
				}
				else{
					System.out.println("Data too big for the cache, not stored and counted as a miss");
					
				}
				if (this.getXLRU() < this.getX()){
					this.incrementXLRU();
				}
				else{
					System.out.println("Counted as a miss");
					this.incrementAccesses();
					this.incrementAccessesBytes(newData.getSize());
				}
				
			}
			else { // Cas ou le cache ne contient pas la donnee et n'a plus de place libre
				System.out.println("Data is not in the cache, and cache is full");
				System.out.println("Removing oldest data until enough space and adding new data to the cache, counted as a miss");
				if (this.isTooBig(newData.getSize())==false ){		
					while(this.getCurrentSize() + newData.getSize() > this.getN()){
						this.getMap().remove((this.getCache().get(this.getCache().size()-1)).getUrl()); //Enleve le oldest element de la hashmap
						this.decrementCurrentSize(this.getCache().get(this.getCache().size()-1).getSize()); //Remove number of bytes from the cache size
						this.getCache().remove(this.getCache().size()-1); //Oldest = le dernier dans l'arraylist
					}
					this.getCache().add(0, newData);//Donnee la plus recente = ajoutee en debut de liste
					this.getMap().put(newData.getUrl(), newData); //Add the new data to the hashmap
					this.incrementCurrentSize(newData.getSize()); //Add the number of bytes to the cache size
				}
				else{
					System.out.println("Counted as a miss");
				}
				
				if (this.getXLRU() < this.getX()){
					this.incrementXLRU();
				}
				else{
					this.incrementAccesses();
					this.incrementAccessesBytes(newData.getSize());
				}
			}
		}
	}


}
