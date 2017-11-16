package alainvanhout.http.parameters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParametersUtilityTest {

    @Test
    public void toQueryString_forComplexCase() {
        Parameters parameters = new Parameters();
        parameters.add("key1", "value1", "value2", "value3");
        parameters.add("key2", "value4");
        parameters.add("key3");
        parameters.add("key4", "");
        parameters.add("key5", "valueDupl", "valueDupl");
        parameters.add("key6", "value6");
        assertEquals("key1=value1&key1=value2&key1=value3&key2=value4&key3&key4=&key5=valueDupl&key5=valueDupl&key6=value6", ParametersUtility.toQueryString(parameters));
    }

    @Test
    public void toQueryString_forEmptyParametersObject() {
        Parameters parameters = new Parameters();
        assertEquals("", ParametersUtility.toQueryString(parameters));
    }

    @Test
    public void fromQueryStringForComplexCase() {
        final Parameters result = ParametersUtility.fromQueryString("key1=value1&key1=value2&key1=value3&key2=value4&key3&key4=&key5=valueDupl&key5=valueDupl&key6=value6");

        final Parameters expected = new Parameters();
        expected.add("key1", "value1", "value2", "value3");
        expected.add("key2", "value4");
        expected.add("key3");
        expected.add("key4", "");
        expected.add("key5", "valueDupl", "valueDupl");
        expected.add("key6", "value6");

        assertEquals(expected, result);
    }

    @Test
    public void fromQueryString_forEmptyString() {
        final Parameters parameters = ParametersUtility.fromQueryString("");
        assertEquals(0, parameters.size());
    }
}