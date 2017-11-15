import java.lang.reflect.Field;
import java.util.List;
import org.jdom2.Element;

public class Deserializer {

	
	public Deserializer() {
		
	}

	public Object deserialize(org.jdom2.Document document) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Object obj = null;

		List<Element> objects = document.getRootElement().getChildren();
		for (Element objectElem : objects) {

			Class classObj = Class.forName(objectElem.getAttributeValue("class"));
			obj = classObj.newInstance();

			List<Element> fields = objectElem.getChildren();
			for (Element fieldElem : fields) {
				Field field = classObj.getDeclaredField(fieldElem.getAttributeValue("name"));
				field.setAccessible(true);
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

		}
		
		return obj;
	}
}
