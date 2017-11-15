/*
 * Assignment 3 for CPSC501, fall 2017
 * 
 * Janet Leahy, T03
 * 10104311
 * 
 */

import java.util.Scanner;

public class ObjectCreator {
	public static final int BASIC_OBJECT = 1;
	public static final int REF_OBJECT = 2;
	public static final int BASIC_ARRAY_OBJECT = 3;
	public static final int REF_ARRAY_OBJECT = 4;
	public static final int REF_COLLECTION_OBJECT = 5;

	public Object createObjectLoop(Scanner in) {
		Object obj = null;
		
		printObjectMenu();

		String typeStr = "";
		int type;
		while (!typeStr.equals("q")) {
			typeStr = in.nextLine();
			try {
				type = Integer.parseInt(typeStr);
				//passes the scanner as a parameter for the function
				// to use to obtain field values
				obj = createObject(type, in);
				break;
			} catch (NumberFormatException e) {
				//do nothing - continue in loop
			}
		}
		return obj;
	}
	
	public static void printObjectMenu() {
		System.out.println("The following objects are available: ");
		System.out.println(BASIC_OBJECT + " - BasicObject");
		System.out.println(REF_OBJECT + " - RefObject");
		System.out.println(BASIC_ARRAY_OBJECT + " - BasicArrayObject");
		System.out.println(REF_ARRAY_OBJECT + " - RefArrayObject");
		System.out.println(REF_COLLECTION_OBJECT + " - RefCollectionObject");
		//System.out.println("q - back to main menu");
	}
	
	
	//creates an object based on type constant, using the provided Scanner
	// to prompt the user for field values
	public Object createObject(int type, Scanner in) {
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
		else if (type == REF_COLLECTION_OBJECT) {
			System.out.println("Creating RefCollectionObject...");
			int num = 0;
			System.out.println("Enter number of items for the collection: ");
			try {
				num = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Number format exception - using default value 0");
			}
			RefCollectionObject refCollectionObj = new RefCollectionObject();
			
			for (int i=0; i< num; i++) {
				printObjectMenu();
				System.out.println("Enter an object type to add to the collection: ");
				try {
					int subType = Integer.parseInt(in.nextLine());
					refCollectionObj.addObj(createObject(subType, in));
				} catch (NumberFormatException e) {
					System.out.println("Number format exception - no object added");
				}
			}
			obj = refCollectionObj;
		}
		
		System.out.println(obj + " created");
		return obj;
	}
	
	

}
