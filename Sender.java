import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Sender {
	public static final String FILENAME = "output.xml";

	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.out.println("Usage: <hostname> <port_number>");
			return;
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		try {
			Socket socket = new Socket(hostname, port);
			
			SAXBuilder in = new SAXBuilder();
			Document document = in.build(new File(Sender.FILENAME));
			
			XMLOutputter out = new XMLOutputter();
			out.output(document, socket.getOutputStream());
			
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error connecting to server. Exiting.");
			//e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		
	}

}
