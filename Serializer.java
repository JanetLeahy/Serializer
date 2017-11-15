import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;

import org.jdom2.*;


public class Serializer {
	
	public static org.jdom2.Document serialize(Object obj) {
		LinkedList<Object> objects = new LinkedList<Object>();
		
		Document document = new Document();
		
		Element root = new Element("serialized");
		
		objects.add(obj);
		
		while (!objects.isEmpty()) {
			root.addContent(writeObject(objects.removeFirst()));
		}
		

		document.setRootElement(root);
		
		return document;
	}
	
	
	public static Element writeObject(Object obj) {
		Element elem = new Element("object");
		
		//reflectively determine object name
		Class classObj = obj.getClass();
		String name = classObj.getName();
		
		elem.setAttribute("class", name);
		elem.setAttribute("id", ""+System.identityHashCode(obj));
		
		Field[] fields = classObj.getDeclaredFields();
		for (int i=0; i<fields.length; i++) {
			elem.addContent(writeField(fields[i], obj));
		}
		
		return elem;
	}
	
	public static Element writeField(Field field, Object obj) {
		Element fieldElem = new Element("field");
		fieldElem.setAttribute("name", field.getName());
		fieldElem.setAttribute("declaringclass", obj.getClass().getName());

		
		return fieldElem;
	}
}
