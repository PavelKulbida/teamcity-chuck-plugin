package chucktcplugin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaFeturesTest {

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void stringFormat() throws Exception {

        Assert.assertEquals("/buildServerResources/quotes_RU.txt", String.format("/buildServerResources/quotes_%1$s.txt", "RU"));
    }

    @Test
    public void listSize() throws Exception {

        List<String> testList = new ArrayList<String>();

        Assert.assertEquals("0", String.valueOf(testList.size()));
    }


    @Test(expected=IndexOutOfBoundsException.class)
    public void listGet() throws Exception {

        List<String> testList = new ArrayList<String>();

        String item = testList.get(0);
    }

}