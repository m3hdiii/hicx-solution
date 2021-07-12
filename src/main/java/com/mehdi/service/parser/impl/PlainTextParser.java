package com.mehdi.service.parser.impl;

import com.mehdi.model.Statistic;
import com.mehdi.service.parser.Parser;
import com.mehdi.utility.ParserUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Just a simple PlainTextParser implementation of {@link Parser} interface
 * For new file formats we need to create their own implementations and develop the {@link com.mehdi.service.ParserFactory} class
 */
public class PlainTextParser implements Parser {

    private File file;

    public PlainTextParser(File file) {
        this.file = file;
    }

    public Statistic getStatistic() {
        String content = parse();
        int numOfDots= ParserUtils.numOfDotsCalculator(content);
        int numOfWords= ParserUtils.numOfWordsCalculator(content);
        String mostUsedWord = ParserUtils.mostUsedWord(content);
        return new Statistic(numOfDots, numOfWords, mostUsedWord);
    }

    public String parse() {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
