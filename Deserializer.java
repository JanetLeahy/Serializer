import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Element;

public class Deserializer {
	HashMap<String, Object> objects;
	
	public Deserializer() {
		objects = new HashMap<String, Object>();
	}

	public Object deserialize(org.jdom2.Document document) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		
		List<Element> objectElems = document.getRootElement().getChildren();
		
		//starts by creating empty versions of all objects and adding them to
		// the main HashMap, using id as the key
		for (Element objectElem : objectElems) {
			Class classObj = Class.forName(objectElem.getAttributeValue("class"));
			Object obj = classObj.newInstance();
			String id = objectElem.getAttributeValue("id");
			
			objects.put(id, obj);
		}
		
		
		// parses through the list a second time and fills in all of the fields
		for (Element objectElem : objectElems) {
			Class classObj = Class.forName(objectElem.getAttributeValue("class"));
			Object obj = objects.get(objectElem.getAttributeValue("id"));

			List<Element> fields = objectElem.getChildren();
			for (Element fieldElem : fields) {
				Field field = classObj.getDeclaredField(fieldElem.getAttributeValue("name"));
				field.setAccessible(true);

				if (fieldElem.getChild("value") != null) {
					Class valueType = field.getType();
					String value = fieldElem.getChild("value").getText();

					if (valueType == Integer.TYPE) {
						field.set(obj, Integer.parseInt(value));
					} else if (valueType == Double.TYPE) {
						field.set(obj, Double.parseDouble(value));
					} else if (valueType == Short.TYPE) {
						field.set(obj, Short.parseShort(value));
					} else if (valueType == Long.TYPE) {
						field.set(obj, Long.parseLong(value));
					} else if (valueType == Float.TYPE) {
						field.set(obj, Float.parseFloat(value));
					} else if (valueType == Boolean.TYPE) {
						field.set(obj, Boolean.parseBoolean(value));
					} else if (valueType == Character.TYPE) {
						field.set(obj, value.charAt(0));
					} else if (valueType == Byte.TYPE) {
						field.set(obj, Byte.parseByte(value));
					} else if (valueType == String.class) {
						field.set(obj, value);
					}
				}
				else if (fieldElem.getChild("reference") != null) {
					String refNum = fieldElem.getChild("reference").getText();
					field.set(obj, objects.get(refNum));
				}

			}

		}
		
		//return the first object in the XML document
		return objects.get(objectElems.get(0).getAttributeValue("id"));
	}
}
