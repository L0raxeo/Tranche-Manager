package com.cotranche.l0raxeo.trancheManager.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVLoader
{

    public static CSVLoader csvLoader;

    public CSVLoader()
    {
        csvLoader = this;
    }

    public String convertToCSV(String[] data)
    {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public void givenDataArray_whenConvertToCSV_thenOutputCreated(String pathName, List<String[]> dataLines) throws IOException
    {
        File csvOutputFile = new File(pathName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile))
        {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public String escapeSpecialCharacters(String data)
    {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'"))
        {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

}
