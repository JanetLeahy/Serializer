
public class BasicArrayObject {
	private int[] anArray;

	public BasicArrayObject() {
		anArray = new int[5]; 
		for (int i=0; i<5; i++) {
			anArray[i] = i;
		}
	}
	
	public BasicArrayObject(int[] array) {
		anArray = array;
	}
	
	public void setArrayObj(int index, int value) {
		anArray[index] = value;
	}
	
	public int getArrayObj(int index) {
		return anArray[index];
	}
	
	public String toString() {
		String str = "BasicArrayObject([";
		int i=0;
		for (; i<anArray.length - 1; i++) {
			str += anArray[i] + ", ";
		}
		str += anArray[i] + "])";
		return str;
	}

}