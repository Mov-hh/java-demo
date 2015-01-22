package mov.demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testGetCfgValueString() {
		assertEquals("", Configuration.getCfgValue("not.exists"));
		assertEquals("default", Configuration.getCfgValue("string.in.default"));
		assertEquals("userdefine", Configuration.getCfgValue("string.in.defaultAndUserdefine"));
		assertEquals("nooverride", Configuration.getCfgValue("string.in.all"));
	}

	@Test
	public void testGetCfgValueStringString() {
		assertEquals(null, Configuration.getCfgValue("not.exists", null));
		assertEquals("default", Configuration.getCfgValue("string.in.default", null));
		assertEquals("userdefine", Configuration.getCfgValue("string.in.defaultAndUserdefine", null));
		assertEquals("nooverride", Configuration.getCfgValue("string.in.all", null));
	}

	@Test
	public void testGetIntCfgValueString() {
		assertEquals(0, Configuration.getIntCfgValue("not.exists"));
		assertEquals(1000, Configuration.getIntCfgValue("int.positive"));
		assertEquals(-1000, Configuration.getIntCfgValue("int.negative"));
		assertEquals(0, Configuration.getIntCfgValue("int.overflow"));
		assertEquals(0, Configuration.getIntCfgValue("int.underflow"));
	}

	@Test
	public void testGetIntCfgValueStringInt() {
		assertEquals(-1, Configuration.getIntCfgValue("not.exists", -1));
		assertEquals(1000, Configuration.getIntCfgValue("int.positive", -1));
		assertEquals(-1000, Configuration.getIntCfgValue("int.negative", -1));
		assertEquals(-1, Configuration.getIntCfgValue("int.overflow", -1));
		assertEquals(-1, Configuration.getIntCfgValue("int.underflow", -1));
	}

	@Test
	public void testGetBooleanCfgValueString() {
		assertFalse(Configuration.getBooleanCfgValue("not.exists"));
		assertTrue(Configuration.getBooleanCfgValue("boolean.true"));
		assertFalse(Configuration.getBooleanCfgValue("boolean.false"));
	}

	@Test
	public void testGetBooleanCfgValueStringBoolean() {
		assertTrue(Configuration.getBooleanCfgValue("not.exists", true));
		assertTrue(Configuration.getBooleanCfgValue("boolean.true", false));
		assertFalse(Configuration.getBooleanCfgValue("boolean.false", true));
	}
}
