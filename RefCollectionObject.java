import java.util.ArrayList;

public class RefCollectionObject {
	private ArrayList<Object> aList;
	
	public RefCollectionObject() {
		aList = new ArrayList<Object>();
	}
	
	public RefCollectionObject(ArrayList<Object> list) {
		aList = list;
	}
	
	public void addObj(Object obj) {
		aList.add(obj);
	}
	
	public Object getObj(int index) {
		return aList.get(index);
	}
	
	public int getListLength() {
		return aList.size();
	}
	
	public String toString() {
		String str = "RefCollectionObject(<";
		int i=0;
		for (; i<aList.size() - 1; i++) {
			str += aList.get(i) + ", ";
		}
		if (aList.size() > 0) {
			str += aList.get(i) ;
		}
		return str + ">)";
	}
}
