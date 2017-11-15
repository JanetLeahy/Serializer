
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
	
	//note: this toString results in an infinite loop when circular
	// references occur
	public String toString() {
		return "RefObject(" + anObj + ")";
	}
}
