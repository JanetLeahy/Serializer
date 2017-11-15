import java.util.ArrayList;

public class RefCollectionObject {
	private ArrayList<Object> aList;
	
	public RefCollectionObject() {
		aList = new ArrayList<Object>();
		for (int i=0; i<5; i++) {
			aList.add(new BasicObject(i, ""));
		}
	}
	
	public Object getObj(int index) {
		return aList.get(index);
	}
	
	public void setObj(int index, Object obj) {
		aList.set(index, obj);
	}
}
