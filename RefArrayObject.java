
public class RefArrayObject {
	private Object anArray[];
	
	public RefArrayObject() {
		anArray = new Object[5];
		for (int i=0; i<anArray.length; i++) {
			anArray[i] = new BasicObject[i];
		}
	}

	public Object getArrayObj(int index) {
		return anArray[index];
	}

	public void setArrayObj(int index, Object anObj) {
		if (index >= 0 && index < anArray.length) {
			anArray[index] = anObj;
		}
	}
}
