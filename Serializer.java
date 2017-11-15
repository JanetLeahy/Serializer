import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;

import org.jdom2.*;


public class Serializer {
	static LinkedList<Object> objects;
	
	public static org.jdom2.Document serialize(Object obj) {
		objects = new LinkedList<Object>();
		
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

		Object value = null;
		try {
			field.setAccessible(true);
			value = field.get(obj);
			String content = "";

			if (value != null && (field.getType().isPrimitive() || field.getType() == String.class)) {
				if (value.getClass() == Integer.class) {
					content += field.getInt(obj);
				} else if (value.getClass() == Double.class) {
					content += field.getDouble(obj);
				} else if (value.getClass() == Short.class) {
					content += field.getShort(obj);
				} else if (value.getClass() == Long.class) {
					content += field.getLong(obj);
				} else if (value.getClass() == Float.class) {
					content += field.getFloat(obj);
				} else if (value.getClass() == Character.class) {
					content += field.getChar(obj);
				} else if (value.getClass() == Boolean.class) {
					content += field.getBoolean(obj);
				} else if (value.getClass() == Byte.class) {
					content += field.getByte(obj);
				} else if (value.getClass() == String.class) {
					content += value;
				}
				
				Element valueElem = new Element("value");
				valueElem.addContent(content);
				fieldElem.addContent(valueElem);

			}
			else if (value != null){
				//field contains reference to an object
				content += System.identityHashCode(value);
				Element refElem = new Element("reference");
				refElem.addContent(content);
				fieldElem.addContent(refElem);
				objects.add(value);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return fieldElem;
	}
}
