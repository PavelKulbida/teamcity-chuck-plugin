package chucktcplugin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ContentGeneratorTest {

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void getApproveIntro() throws Exception {

        IPhraseGenerator generator = new PhraseGenerator(
                Arrays.asList("1", "2", "3"),
                new ContentGenerator(Arrays.asList("4"))
        );

        Assert.assertEquals("1", generator.GetApproveIntro());
    }

    @Test
    public void getDisapproveIntro() throws Exception {

        IPhraseGenerator generator = new PhraseGenerator(
                Arrays.asList("1", "2", "3"),
                new ContentGenerator(Arrays.asList("4"))
        );


        Assert.assertEquals("2", generator.GetDisapproveIntro());
    }

    @Test
    public void getQuotation() throws Exception {

        IPhraseGenerator generator = new PhraseGenerator(
                Arrays.asList("1", "2", "3"),
                new ContentGenerator(Arrays.asList("4"))
        );

        Assert.assertEquals("3 4", generator.GetQuotation());
    }

    @Test
    public void getApproveIntroEmpty() throws Exception {

        IPhraseGenerator generator = new PhraseGenerator(
                new ArrayList<String>(),
                new ContentGenerator(
                        new ArrayList<String>())
        );

        Assert.assertEquals("", generator.GetApproveIntro());
    }

    @Test
    public void getDisapproveEmpty() throws Exception {

        IPhraseGenerator generator = new PhraseGenerator(
                new ArrayList<String>(),
                new ContentGenerator(
                        new ArrayList<String>())
        );


        Assert.assertEquals("", generator.GetDisapproveIntro());
    }

    @Test
    public void getQuotationEmpty() throws Exception {

        IPhraseGenerator generator = new PhraseGenerator(
                new ArrayList<String>(),
                new ContentGenerator(
                        new ArrayList<String>())
        );

        Assert.assertEquals("", generator.GetQuotation());
    }


}