package com.mehdi.service.parser;

import com.mehdi.model.Statistic;

/**
 * An interface for parsing different file extensions
 */
public interface Parser {

    Statistic getStatistic();
    String parse();
}
