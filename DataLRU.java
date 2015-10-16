/*****************
* classe DataLRU 
 *****************/

public class DataLRU extends Data {
	
	// attribut de DataLRU
	private int  count;
	
	//constructeur de DataLRU
	public DataLRU(String url, int size) {
		super(url, size);
		// TODO Auto-generated constructor stub
	}
	
	// accesseur et mutateur de DataLRU
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
