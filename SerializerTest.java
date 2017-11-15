import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jdom2.output.XMLOutputter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SerializerTest {

	/*
	 * Try array length = 0
	 * Null elements in array, references
	 * 
	 * circular references
	 */
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBasicObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		BasicObject obj = new BasicObject(2, "Hello world");
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testRefObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		RefObject obj = new RefObject(new BasicObject(2, "Hello world"));
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testBasicArrayObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		BasicArrayObject obj = new BasicArrayObject(new int[] {1,2,3,4,5});
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testRefArrayObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		RefArrayObject obj = new RefArrayObject(new Object[] {new BasicObject(1, ""), new BasicArrayObject(new int[] {1,2,3,4,5})});
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testRefCollectionObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		RefCollectionObject obj = new RefCollectionObject();
		obj.addObj(new BasicObject(2,"XXX"));
		obj.addObj(new BasicObject(123, "OOO"));
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testEmptyArray() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		BasicArrayObject obj = new BasicArrayObject(new int[] {});
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testNullRef() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		RefObject obj = new RefObject(null);
		
		assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}
	
	@Test
	public void testCircularRef() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		RefObject obj = new RefObject();
		RefObject subObj = new RefObject(obj);
		obj.setObj(subObj);
		
		//XMLOutputter out = new XMLOutputter();
		//System.out.println(out.outputString(s.serialize(obj)));
		
		FieldInspector inspector = new FieldInspector();
		inspector.inspect(obj, true);
		//assertTrue(d.deserialize(s.serialize(obj)).toString().equals(obj.toString()));
	}

}
