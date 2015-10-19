


public class Cache {
	
	private int N;
	private int X;
	private int numberOfAccesses;
	private int numberOfAccessesBytes;

	
	public Cache (int size, int warmup){
		this.N = size;
		this.X = warmup;
		this.numberOfAccesses = 0;
		this.numberOfAccessesBytes = 0;
	}
	
	public int getX(){
		return this.X;
	}
	
	public int getN(){
		return N;
	}
	
	public void incrementAccesses(){
		this.numberOfAccesses += 1;
	}
	
	public int getAccesses(){
		return this.numberOfAccesses;
	}
	
	public void incrementAccessesBytes(int bytes){
		this.numberOfAccessesBytes += bytes;
	}
	
	public int getAccessesBytes(){
		return this.numberOfAccessesBytes;
	}
	
	

}
