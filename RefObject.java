
public class RefObject {
	private Object anObj;
	
	public RefObject() {
		anObj = new BasicObject(0, "");
	}
	
	public RefObject(Object obj) {
		anObj = obj;
	}

	public Object getObj() {
		return anObj;
	}

	public void setObj(Object anObj) {
		this.anObj = anObj;
	}
	
	public String toString() {
		return "RefObject(" + anObj + ")";
	}
}
