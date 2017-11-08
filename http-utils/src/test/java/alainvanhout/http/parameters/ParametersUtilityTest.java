package alainvanhout.http.parameters;

import org.junit.Test;

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
        assertEquals("key1=value1&key1=value2&key1=value3&key2=value4&key5=valueDupl&key5=valueDupl&key6=value6&key3&key4=", ParametersUtility.toQueryString(parameters));
    }

    @Test
    public void toQueryString_forEmptyParametersObject() throws Exception {
        Parameters parameters = new Parameters();
        assertEquals("", ParametersUtility.toQueryString(parameters));
    }

}