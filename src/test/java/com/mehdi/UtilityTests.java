package com.mehdi;

import com.mehdi.utility.ParserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used for showing simple tests only
 */
public class UtilityTests {

    private String textContent;

    @Before
    public void setUp() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("test-file");
        Path path = Paths.get(url.toURI());
        byte[] lines = Files.readAllBytes(path);
        textContent = new String(lines, StandardCharsets.UTF_8);
    }

    @Test
    public void testNumOfDotsCalculatorTest() {
        Assert.assertEquals(4, ParserUtils.numOfDotsCalculator(textContent));
    }

    @Test
    public void numOfWordsCalculatorTest() {
        Assert.assertEquals(19, ParserUtils.numOfWordsCalculator(textContent));
    }

    @Test
    public void mostUsedWordTest() {
        Assert.assertEquals("[doost] must return!","doost", ParserUtils.mostUsedWord(textContent));
    }
}
