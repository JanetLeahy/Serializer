import java.util.Scanner;

public class ObjectCreator {
	public static final int BASIC_OBJECT = 1;

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
						createObject(type, in);
						break;
					} catch (NumberFormatException e) {
						//do nothing - continue in loop
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
		System.out.println("q - back to main menu");
	}
	
	public static Object createObject(int type, Scanner in) {
		Object obj = null;
		
		if (type == BASIC_OBJECT) {
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
			System.out.println("BasicObject(" + anInt + ", " + aString + ") created");
		}
		
		
		return obj;
	}
}
