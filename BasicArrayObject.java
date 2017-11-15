
public class BasicArrayObject {
	private int[] anArray;

	public BasicArrayObject() {
		anArray = new int[10]; 
		for (int i=0; i<10; i++) {
			anArray[i] = i;
		}
	}
	
	public void setArrayObj(int index, int value) {
		anArray[index] = value;
	}
	
	public int getArrayObj(int index) {
		return anArray[index];
	}

}