

/*****************
* classe DataLRU 
 *****************/

public class DataLRU extends Data {
	
	//Attribut de DataLRU representant le nombre de requetes effectuees depuis le dernier acces a la donnee. Plus la valeur de recency est elevee, plus cela fait 
	//longtemps qu'elle n'aplus ete accedee.
	private int recency = 0;
	
	//Constructeur de DataLRU
	public DataLRU(String url, int size) {
		super(url, size);
	}
	
	//Accesseur du compteur "recency"
	public int getCount() {
		return recency;
	}

	//Modificateur du compteur "recency"
	public void setCount(int count) {
		this.recency = count;
	}

}
