package Braint.util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "root")
public class TestClass {
	
	@Element(name="asd")
	public static final int asdASd = 0;

	@Element(name="string")
	public String s = "test";

}