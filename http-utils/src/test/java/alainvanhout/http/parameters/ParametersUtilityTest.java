package alainvanhout.http.parameters;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ParametersUtilityTest {

    @Test
    public void toQueryString_forComplexCase() throws Exception {
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
    public void toQueryString_forEmptyParametersObject() throws Exception {
        Parameters parameters = new Parameters();
        assertEquals("", ParametersUtility.toQueryString(parameters));
    }

    @Test
    public void fromQueryStringForComplexCase() throws Exception {
        final Parameters result = ParametersUtility.fromQueryString("key1=value1&key1=value2&key1=value3&key2=value4&key3&key4=&key5=valueDupl&key5=valueDupl&key6=value6");

        final Parameters expected = new Parameters();
        expected.add("key1", "value1", "value2", "value3");
        expected.add("key2", "value4");
        expected.add("key3");
        expected.add("key4", "");
        expected.add("key5", "valueDupl", "valueDupl");
        expected.add("key6", "value6");

        // we do not care about the specific order, only the contents
        final HashMap<String, List<String>> unsortedExpected = new HashMap<>(expected.getMap());
        final HashMap<String, List<String>> unsortedResult = new HashMap<>(result.getMap());
        assertEquals(unsortedExpected, unsortedResult);
    }

    @Test
    public void fromQueryString_forEmptyString() throws Exception {
        final Parameters parameters = ParametersUtility.fromQueryString("");
        assertEquals(0, parameters.size());
    }

}