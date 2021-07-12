package com.mehdi.service;

import com.mehdi.service.parser.Parser;
import com.mehdi.service.parser.impl.PlainTextParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Create an instance for the {@link Parser} interface based on the input mime-type
 */
public class ParserFactory {

    public Parser getParser(File file) {

        if (isPlainText(file)) {
            return new PlainTextParser(file);
        }

        if (isPDF(file)) {
            //PDF Implementation here
        }

        //... Other implementations

        throw new IllegalArgumentException(file.getAbsolutePath());
    }

    /**
     * If ever in the future we needed the pdf file
     * @param file
     * @return
     */
    private boolean isPDF(File file) {
       /* String mimeType = getMimeType(file);
        return mimeType == null ? false : mimeType.equals("application/pdf");*/
        return false;
    }

    private boolean isPlainText(File file) {
        String mimeType = getMimeType(file);
        return mimeType == null ? false : mimeType.equals("text/plain");
    }


    /**
     * It finds the file mime-type, even if the file has no specific/clear extensions
     * @param file
     * @return
     */
    private String getMimeType(File file) {
        Path path = file.toPath();
        try {
            String mimeType = Files.probeContentType(path);
            return mimeType;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
