
public class RefObject {
	private Object anObj;
	
	public RefObject() {
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
