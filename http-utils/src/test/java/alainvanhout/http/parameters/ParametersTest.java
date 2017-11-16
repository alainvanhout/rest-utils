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
    public final ExpectedException expectedException = ExpectedException.none();

    private Parameters parameters;

    @Before
    public void setUp() {
        parameters = new Parameters();
    }

    // contains

    @Test
    public void contains_ifOneValue()  {
        parameters.add("key", "value");
        assertTrue(parameters.contains("key"));
    }

    @Test
    public void contains_ifMultipleValues() {
        parameters.add("key", "value1", "value2", "value3");
        assertTrue(parameters.contains("key"));
    }

    @Test
    public void contains_ifKeyButNoValue() {
        parameters.add("key");
        assertTrue(parameters.contains("key"));
    }

    @Test
    public void contains_ifNoKey() {
        assertFalse(parameters.contains("key"));
    }

    // get

    @Test
    public void get_ifOneValue() {
        parameters.add("key", "value");
        assertEquals(Collections.singletonList("value"), parameters.get("key"));
    }

    @Test
    public void get_ifMultipleValues() {
        parameters.add("key", "value1", "value2", "value3");
        assertEquals(Arrays.asList("value1", "value2", "value3"), parameters.get("key"));
    }

    @Test
    public void get_ifDuplicateValues() {
        parameters.add("key", "value1", "value2", "value1");
        assertEquals(Arrays.asList("value1", "value2", "value1"), parameters.get("key"));
    }

    @Test
    public void get_ifKeyButNoValue() {
        parameters.add("key");
        assertEquals(Collections.emptyList(), parameters.get("key"));
    }

    @Test
    public void get_ifNoKey() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Key not found: key");

        parameters.get("key");
    }

    // getOne

    @Test
    public void getOne_ifOneValue() {
        parameters.add("key", "value");
        assertEquals("value", parameters.getOne("key"));
    }

    @Test
    public void getOne_ifMultipleValues() {
        parameters.add("key", "value1", "value2", "value3");
        assertEquals("value1", parameters.getOne("key"));
    }

    @Test
    public void getOne_ifDuplicateValues() {
        parameters.add("key", "value1", "value2", "value1");
        assertEquals("value1", parameters.getOne("key"));
    }

    @Test
    public void getOne_ifKeyButNoValue() {
        parameters.add("key");

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No value found for key: key");

        parameters.getOne("key");
    }

    @Test
    public void getOne_ifNoKey() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Key not found: key");

        parameters.getOne("key");
    }
}