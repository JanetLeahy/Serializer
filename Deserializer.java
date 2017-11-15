/*
 * Assignment 3 for CPSC501, fall 2017
 * 
 * Janet Leahy, T03
 * 10104311
 * 
 */

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
		// the main HashMap, using id as the key (so they can be used to fill
		// in any references)
		for (Element objectElem : objectElems) {
			Class classObj = Class.forName(objectElem.getAttributeValue("class"));
			Object obj = null;
			if (classObj.isArray()) {
				int len = Integer.parseInt(objectElem.getAttributeValue("length"));
				obj = Array.newInstance(classObj.getComponentType(), len);
			} 
			else {
				obj = classObj.newInstance();
			}
			String id = objectElem.getAttributeValue("id");
			
			objects.put(id, obj);
		}


		// parses through the list a second time and fills in all of the fields
		for (Element objectElem : objectElems) {
			Class classObj = Class.forName(objectElem.getAttributeValue("class"));
			Object obj = objects.get(objectElem.getAttributeValue("id"));

			//if it has a length attribute, it is an array
			if (classObj.isArray()) {
				List<Element> elems = objectElem.getChildren();
				Class valueType = classObj.getComponentType();
				int i = 0;
				
				for (Element elem : elems) {
					String value = elem.getText();

					if (valueType == Integer.TYPE) {
						Array.set(obj, i, Integer.parseInt(value));
					} else if (valueType == Double.TYPE) {
						Array.set(obj, i, Double.parseDouble(value));
					} else if (valueType == Short.TYPE) {
						Array.set(obj, i, Short.parseShort(value));
					} else if (valueType == Long.TYPE) {
						Array.set(obj, i, Long.parseLong(value));
					} else if (valueType == Float.TYPE) {
						Array.set(obj, i, Float.parseFloat(value));
					} else if (valueType == Boolean.TYPE) {
						Array.set(obj, i, Boolean.parseBoolean(value));
					} else if (valueType == Character.TYPE) {
						Array.set(obj, i, value.charAt(0));
					} else if (valueType == Byte.TYPE) {
						Array.set(obj, i, Byte.parseByte(value));
					} else if (valueType == String.class) {
						Array.set(obj, i, value);
					}
					else { // object is an array of object references
						Array.set(obj, i, objects.get(value));
					}
					//increment the array index
					i++;
				}
				
			}
			//otherwise, read and fill in any fields
			else {
				List<Element> fields = objectElem.getChildren();
				for (Element fieldElem : fields) {
					Field field = classObj.getDeclaredField(fieldElem.getAttributeValue("name"));
					
					if (Modifier.isFinal(field.getModifiers())) {
						//avoids trying to set constant values
						continue;
					}
					
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

		}
		
		//return the first object in the XML document
		return objects.get(objectElems.get(0).getAttributeValue("id"));
	}
}
