import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
		

		String input = "";
		Scanner in = new Scanner(System.in);
		ObjectCreator creator = new ObjectCreator();
		System.out.println("Object maker started. Type h<enter> for help.");
		
		while (!input.equals("q")) {
			input = in.nextLine();
			
			if (input.equals("c")) {
				Object obj = creator.createObjectLoop(in);
				if (obj != null) {
					try {
						serializeObject(obj, in, hostname, port);
						//System.out.println("Returning to main menu...");
						System.out.println("Serialization complete");
						input = "q";
					} catch (IOException e) {
						System.out.println("Error occured serializing object.");
					}
				}
			}
			
			if (input.equals("h")) {
				printHelp();
			}
		}
		
		System.out.println("Exiting the program...");
		in.close();

	}

	

	public static void printHelp() {
		System.out.println("The following text commands are available: ");
		System.out.println("c - create an object");
		System.out.println("h - help (display commands)");
		System.out.println("q - quit");
		
	}
	
	public static void printSerializeMenu() {
		System.out.println("What to do with the XML file?");
		System.out.println("f - save to a file");
		System.out.println("d - display to console");
		System.out.println("s - send over network connection");
		System.out.println("q - exit the program");
	}
	
	
	public static void serializeObject(Object obj, Scanner in, String hostname, int port) throws FileNotFoundException, IOException {
		System.out.println("Serializing object...");
		Serializer serializer = new Serializer();
		Document document = serializer.serialize(obj);
		XMLOutputter out = new XMLOutputter();
		
		printSerializeMenu();
		
		String input = "";
		while (!input.equals("q")) {
			input = in.nextLine();
			
			if (input.equals("f")) {
				System.out.println("Saving to file...");
				out.output(document, new FileOutputStream(new File(Sender.FILENAME)));
				break;
			}
			if (input.equals("d")) {
				System.out.println("Displaying file");
				System.out.println(out.outputString(document));
				break;
			}
			if (input.equals("s")) {
				System.out.println("Sending over network...");
				sendDocument(hostname, port, document);
				break;
			}
		}
	}		

	
	public static void sendDocument(String hostname, int port, Document document) {
		try {
			Socket socket = new Socket(hostname, port);

			XMLOutputter out = new XMLOutputter();
			out.output(document, socket.getOutputStream());

			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error connecting to server. Exiting.");
			//e.printStackTrace();
		}
	}

}
