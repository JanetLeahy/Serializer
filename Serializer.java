/*
 * Assignment 3 for CPSC501, fall 2017
 * 
 * Janet Leahy, T03
 * 10104311
 * 
 */


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.jdom2.*;


public class Serializer {
	ArrayList<Object> objects;
	
	public Serializer() {
		objects = new ArrayList<Object>();
	}
	
	public org.jdom2.Document serialize(Object obj) {
		objects.clear();
		int written = 0;
		Document document = new Document();
		
		Element root = new Element("serialized");
		
		objects.add(obj);
		
		while (written < objects.size()) {
			root.addContent(writeObject(objects.get(written)));
			written++;
		}

		document.setRootElement(root);
		
		return document;
	}
	
	
	public Element writeObject(Object obj) {
		Element elem = new Element("object");
		
		//reflectively determine object name
		Class classObj = obj.getClass();
		String name = classObj.getName();
		
		elem.setAttribute("class", name);
		elem.setAttribute("id", ""+System.identityHashCode(obj));
		
		if (classObj.isArray()) {
			int len = Array.getLength(obj);
			elem.setAttribute("length", ""+len);

			for (int i=0; i<len; i++) {
				Object arrayItem = Array.get(obj, i);
				
				if (arrayItem == null) {
					Element valueElem = new Element("value");
					valueElem.addContent("");
					elem.addContent(valueElem);
				}
				else if (arrayItem.getClass().equals(Integer.class) || arrayItem.getClass().equals(Double.class) ||
						arrayItem.getClass().equals(Long.class) || arrayItem.getClass().equals(Short.class) ||
						arrayItem.getClass().equals(Float.class) || arrayItem.getClass().equals(Byte.class) ||
						arrayItem.getClass().equals(Character.class) || arrayItem.getClass().equals(Boolean.class) ||
						arrayItem.getClass().equals(String.class)
						) {
					Element valueElem = new Element("value");
					valueElem.addContent(""+arrayItem);
					elem.addContent(valueElem);
				}
				else {
					Element refElem = new Element("reference");
					refElem.addContent(""+System.identityHashCode(arrayItem));
					elem.addContent(refElem);
					if (!objects.contains(arrayItem)) {
						//if clause deals with circular references
						objects.add(arrayItem);
					}
				}
			}
			
		}
		else {
			Field[] fields = classObj.getDeclaredFields();
			for (int i=0; i<fields.length; i++) {
				elem.addContent(writeField(fields[i], obj));
			}
		}
		
		return elem;
	}
	
	public Element writeField(Field field, Object obj) {
		Element fieldElem = new Element("field");
		fieldElem.setAttribute("name", field.getName());
		fieldElem.setAttribute("declaringclass", obj.getClass().getName());

		Object value = null;
		try {
			field.setAccessible(true);
			value = field.get(obj);
			String content = "";

			if (value == null) {
				Element valueElem = new Element("value");
				valueElem.addContent(content);
				fieldElem.addContent(valueElem);
			}
			else if (field.getType().isPrimitive() || field.getType() == String.class) {
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
			else {
				//field contains reference to an object
				content += System.identityHashCode(value);
				Element refElem = new Element("reference");
				refElem.addContent(content);
				fieldElem.addContent(refElem);
				if (!objects.contains(value)) {
					//if clause deals with circular references
					objects.add(value);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return fieldElem;
	}
	
}
