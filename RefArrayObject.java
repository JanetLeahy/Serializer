
public class RefArrayObject {
	private Object anArray[];
	
	public RefArrayObject() {
		anArray = new Object[5];
		for (int i=0; i<anArray.length; i++) {
			anArray[i] = new BasicObject[i];
		}
	}
	
	public RefArrayObject(Object[] array) {
		anArray = array;
	}

	public Object getArrayObj(int index) {
		return anArray[index];
	}

	public void setArrayObj(int index, Object anObj) {
		if (index >= 0 && index < anArray.length) {
			anArray[index] = anObj;
		}
	}
	
	public String toString() {
		String str = "RefArrayObject([";
		int i=0;
		for (; i<anArray.length - 1; i++) {
			str += anArray[i] + ", ";
		}
		if (anArray.length > 0) {
			str += anArray[i] ;
		}
		return str + "])";
	}
}
