import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

public class ObjectCreator {
	public static final String FILENAME = "output.xml";
	public static final int BASIC_OBJECT = 1;
	public static final int REF_OBJECT = 2;
	public static final int BASIC_ARRAY_OBJECT = 3;
	public static final int REF_ARRAY_OBJECT = 4;

	public static void main(String[] args) {
		String input = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Object creator started. Type h<enter> for help.");
		
		while (!input.equals("q")) {
			input = in.nextLine();
			
			if (input.equals("c")) {
				printObjectMenu();

				String typeStr = "";
				int type;
				while (!typeStr.equals("q")) {
					typeStr = in.nextLine();
					try {
						type = Integer.parseInt(typeStr);
						//passes the scanner as a parameter for the function
						// to use to obtain field values
						Object obj = createObject(type, in);
						serializeObject(obj, in);
						break;
					} catch (NumberFormatException e) {
						//do nothing - continue in loop
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Returning to main menu...");
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
	
	public static void printObjectMenu() {
		System.out.println("The following objects are available: ");
		System.out.println(BASIC_OBJECT + " - BasicObject");
		System.out.println(REF_OBJECT + " - RefObject");
		System.out.println(BASIC_ARRAY_OBJECT + " - BasicArrayObject");
		System.out.println(REF_ARRAY_OBJECT + " - RefArrayObject");
		System.out.println("q - back to main menu");
	}
	
	public static void printSerializeMenu() {
		System.out.println("What to do with the XML file?");
		System.out.println("f - save to a file");
		System.out.println("d - display to console");
		System.out.println("s - send over network connection");
		System.out.println("q - return to main menu");
	}
	
	public static Object createObject(int type, Scanner in) {
		Object obj = null;
		
		if (type == BASIC_OBJECT) {
			System.out.println("Creating BasicObject...");
			//set fields
			int anInt = 0;
			String aString = "";
			System.out.println("Enter value for int anInt: ");
			try {
				anInt = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Number format exception - using default value 0.");
			}
			System.out.println("Enter value for String aString: ");
			aString = in.nextLine();
			
			obj = new BasicObject(anInt, aString);
		}
		else if (type == REF_OBJECT) {
			System.out.println("Creating RefObject...");
			printObjectMenu();
			System.out.println("Enter a type for Object anObj: ");
			Object subObj = null;
			try {
				int subType = Integer.parseInt(in.nextLine());
				subObj = createObject(subType, in);
			} catch (NumberFormatException e) {
				System.out.println("Number format exception - using default value null");
			}
			
			obj = new RefObject(subObj);
		}
		else if (type == BASIC_ARRAY_OBJECT) {
			System.out.println("Creating BasicArrayObject...");
			int len = 0;
			System.out.println("Enter a length for anArray: ");
			try {
				len = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Number format exception - using default length 0");
			}
			int[] array = new int[len];
			for (int i=0; i< len; i++) {
				System.out.println("Enter an integer value for anArray[" + i + "]: ");
				try {
					array[i] = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Number format exception - using default value 0");
					array[i] = 0;
				}
			}
			obj = new BasicArrayObject(array);
		}
		else if (type == REF_ARRAY_OBJECT) {
			System.out.println("Creating RefArrayObject...");
			int len = 0;
			System.out.println("Enter a length for anArray: ");
			try {
				len = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Number format exception - using default length 0");
			}
			Object[] array = new Object[len];
			for (int i=0; i< len; i++) {
				printObjectMenu();
				System.out.println("Enter a type for anArray[" + i + "]: ");
				try {
					int subType = Integer.parseInt(in.nextLine());
					array[i] = createObject(subType, in);
				} catch (NumberFormatException e) {
					System.out.println("Number format exception - using default value null");
					array[i] = null;
				}
			}
			obj = new RefArrayObject(array);
			
		}
		

		System.out.println(obj + "created");
		return obj;
	}
	
	
	public static void serializeObject(Object obj, Scanner in) throws FileNotFoundException, IOException {
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
				out.output(document, new FileOutputStream(new File(FILENAME)));
				break;
			}
			if (input.equals("d")) {
				System.out.println("Displaying file");
				System.out.println(out.outputString(document));
				break;
			}
			if (input.equals("s")) {
				System.out.println("Sending over network");
				break;
			}
		}
	}
}
