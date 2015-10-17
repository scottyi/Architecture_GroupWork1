
/**************
* classe Data
***************/

public class Data {
	
	// attributs de Data 
	private String url;
	private int size;
	
	// constructeur de Data
	public Data(String url, int size){
		this.setUrl(url);
		this.setSize(size);
	}
	
	// accesseurs et mutateurs de Data
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString(){
		return this.url + " " + this.size;
	}
	
}
