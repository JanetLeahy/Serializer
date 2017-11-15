/*
 * Assignment 3 for CPSC501, fall 2017
 * 
 * Janet Leahy, T03
 * 10104311
 * 
 */

import static org.junit.Assert.*;
import org.junit.Test;


public class SerializerTest {

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
	public void testEmptyConstructor() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		Serializer s = new Serializer();
		Deserializer d = new Deserializer();
		
		BasicArrayObject obj = new BasicArrayObject();
		
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
