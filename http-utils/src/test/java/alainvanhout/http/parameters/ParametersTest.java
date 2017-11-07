package alainvanhout.http.parameters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParametersTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Parameters parameters;

    @Before
    public void setUp() throws Exception {
        parameters = new Parameters();
    }

    // contains

    @Test
    public void contains_ifOneValue() throws Exception {
        parameters.add("key", "value");
        assertTrue(parameters.contains("key"));
    }

    @Test
    public void contains_ifMultipleValues() throws Exception {
        parameters.add("key", "value1", "value2", "value3");
        assertTrue(parameters.contains("key"));
    }

    @Test
    public void contains_ifKeyButNoValue() throws Exception {
        parameters.add("key");
        assertTrue(parameters.contains("key"));
    }

    @Test
    public void contains_ifNoKey() throws Exception {
        assertFalse(parameters.contains("key"));
    }

    // get

    @Test
    public void get_ifOneValue() throws Exception {
        parameters.add("key", "value");
        assertEquals(Arrays.asList("value"), parameters.get("key"));
    }

    @Test
    public void get_ifMultipleValues() throws Exception {
        parameters.add("key", "value1", "value2", "value3");
        assertEquals(Arrays.asList("value1", "value2", "value3"), parameters.get("key"));
    }

    @Test
    public void get_ifDuplicateValues() throws Exception {
        parameters.add("key", "value1", "value2", "value1");
        assertEquals(Arrays.asList("value1", "value2", "value1"), parameters.get("key"));
    }

    @Test
    public void get_ifKeyButNoValue() throws Exception {
        parameters.add("key");
        assertEquals(Collections.emptyList(), parameters.get("key"));
    }

    @Test
    public void get_ifNoKey() throws Exception {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Key not found: key");

        parameters.get("key");
    }

    // getOne

    @Test
    public void getOne_ifOneValue() throws Exception {
        parameters.add("key", "value");
        assertEquals("value", parameters.getOne("key"));
    }

    @Test
    public void getOne_ifMultipleValues() throws Exception {
        parameters.add("key", "value1", "value2", "value3");
        assertEquals("value1", parameters.getOne("key"));
    }

    @Test
    public void getOne_ifDuplicateValues() throws Exception {
        parameters.add("key", "value1", "value2", "value1");
        assertEquals("value1", parameters.getOne("key"));
    }

    @Test
    public void getOne_ifKeyButNoValue() throws Exception {
        parameters.add("key");

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No value found for key: key");

        parameters.getOne("key");
    }

    @Test
    public void getOne_ifNoKey() throws Exception {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Key not found: key");

        parameters.getOne("key");
    }
}