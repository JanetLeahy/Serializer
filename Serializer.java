import org.jdom2.*;


public class Serializer {

	public static org.jdom2.Document serialize(Object obj) {
		Document document = new Document();
		
		Element root = new Element("serialized");
		document.setRootElement(root);
		
		return document;
	}
}
