/*
 * Assignment 3 for CPSC501, fall 2017
 * 
 * Janet Leahy, T03
 * 10104311
 * 
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Receiver {

	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("Usage: java Receiver <port_number>");
			return;
		}
		
		try {
			int portNum = Integer.parseInt(args[0]);
			ServerSocket server = new ServerSocket(portNum);

			//server only handles a single connection before closing
			Socket sender = server.accept();

			SAXBuilder in = new SAXBuilder();
			Document document = in.build(sender.getInputStream());
			
			Deserializer deserializer = new Deserializer();
			Object obj = deserializer.deserialize(document);

			//System.out.println(obj);
			FieldInspector inspector = new FieldInspector();
			inspector.inspect(obj, true);
			
			sender.close();
			server.close();


		} catch (JDOMException e) {
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
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error occured with server socket. Exiting.");
			e.printStackTrace();
		}
	}

}
