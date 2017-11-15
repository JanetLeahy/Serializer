
public class RefObject {
	private Object anObj;
	
	public RefObject() {
		anObj = new BasicObject(1, "");
	}

	public Object getObj() {
		return anObj;
	}

	public void setObj(Object anObj) {
		this.anObj = anObj;
	}
}
