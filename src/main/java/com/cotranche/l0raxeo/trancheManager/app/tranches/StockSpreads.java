package com.cotranche.l0raxeo.trancheManager.app.tranches;

import com.cotranche.l0raxeo.trancheManager.app.tranches.modules.Stock;
import com.cotranche.l0raxeo.trancheManager.utils.CSVLoader;
import com.cotranche.l0raxeo.trancheManager.utils.FileLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockSpreads
{

    // Lists of spreadsheets: lists (containing spreads) of lists (containing profiles) of an array of strings (profile data)
    // saves all spreads in CSV file
    private static final HashMap<String, List<String[]>> spreads = new HashMap<>();

    /**
     * @param spreadName in which to index profile
     * @param stock being profiled into specified spread
     */
    public static void addProfile(String spreadName, Stock stock)
    {
        for (String spread : spreads.keySet())
        {
            if (spread.equals(spreadName))
            {
                List<String[]> spreadRows = spreads.get(spread);
                spreadRows.add(new String[] {stock.getTickerSymbol(),
                        String.valueOf(stock.getSharePrice()),
                        String.valueOf(stock.getCagr5YoY()),
                        String.valueOf(stock.getYoy1Return()),
                        String.valueOf(stock.getYoy5Return())});

                break;
            }
        }
    }

    public static void createSpread(String spreadName)
    {
        // add expense ratio and dividend yield
        List<String[]> spreadRow = new ArrayList<>();
        spreadRow.add(new String[] {"Ticker Symbol", "PPS", "CAGR", "1 Year Return", "5 Year Return"});

        spreads.put(spreadName, spreadRow);
    }

    public static void loadSpread(String spreadName)
    {
        List<String[]> spreadRow = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FileLoader.getProgramPath2() + "/bin/output/spreads/" + spreadName + ".csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                spreadRow.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        spreads.put(spreadName, spreadRow);
    }

    public static void exportSpread(String spreadName)
    {
        List<String[]> queuedSpread = spreads.get(spreadName);
        try {
            CSVLoader.csvLoader.givenDataArray_whenConvertToCSV_thenOutputCreated(FileLoader.getProgramPath2() + "/bin/output/spreads/" + spreadName + ".csv", queuedSpread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSpreadsByName()
    {
        return new ArrayList<>(spreads.keySet());
    }

}
