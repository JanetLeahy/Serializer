
public class BasicObject {
	private int anInt;
	private String aString;
	
	public BasicObject() {
		
	}
	
	public BasicObject(int i, String str) {
		anInt = i;
		aString = str;
	}

	public int getInt() {
		return anInt;
	}
	
	public void setInt(int i) {
		anInt = i;
	}
	
	public String getString() {
		return aString;
	}

	public void setString(String str) {
		aString = str;
	}
	
	public String toString() {
		return "BasicObject(" + anInt + ", " + aString + ")";
	}

	
}
