/*****************
* classe DataLFU
******************/

public class DataLFU extends Data {
	
	// attribut de DataLFU
	private int frequency;
	
	// constructeur de DataLFU
	public DataLFU(String url, int size) {
		super(url, size);
		// TODO Auto-generated constructor stub
	}
	
	// accesseur et mutateur de DataLFU
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
