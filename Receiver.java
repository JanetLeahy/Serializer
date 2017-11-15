import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Receiver {

	public static void main(String[] args) {
		SAXBuilder in = new SAXBuilder();
		try {
			Document document = in.build(new File(ObjectCreator.FILENAME));
			
			Deserializer deserializer = new Deserializer();
			Object obj = deserializer.deserialize(document);
			
			//Inspector inspector = new Inspector();
			//inspector.inspect(obj, true);
			System.out.println(obj);
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
