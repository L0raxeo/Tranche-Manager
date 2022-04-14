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
import java.util.Objects;

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
                        String.valueOf(stock.getYoy5Return()),
                        String.valueOf(stock.getDividendYield()),
                        String.valueOf(stock.getExpenseRatio()),
                        String.valueOf(stock.getMarketCap()),
                        String.valueOf(stock.getPeTtm()),
                        String.valueOf(stock.getEpsTtm())});

                break;
            }
        }
    }

    public static void removeProfile(String spreadName, String stockTicker)
    {
        List<String[]> profiles = spreads.get(spreadName);

        for (String[] profile : profiles)
        {
            if (Objects.equals(profile[0], stockTicker))
            {
                profiles.remove(profile);
                break;
            }
        }
    }

    public static void removeAllProfiles(String spreadName)
    {
        spreads.get(spreadName).clear();
        spreads.get(spreadName).add(new String[] {"Ticker Symbol", "PPS", "CAGR", "1 Year Return", "5 Year Return", "Div-Yield", "Expense-ratio", "Market Cap", "P/E Ratio (TTM)", "EPS (TTM)"});

    }

    public static List<String[]> getSpreadProfiles(String spreadName)
    {
        return spreads.get(spreadName);
    }

    public static List<String[]> getSpread(String spreadName)
    {
        return spreads.get(spreadName);
    }

    public static void createSpread(String spreadName)
    {
        // add expense ratio and dividend yield
        List<String[]> spreadRow = new ArrayList<>();
        spreadRow.add(new String[] {"Ticker Symbol", "PPS", "CAGR", "1 Year Return", "5 Year Return", "Div-Yield", "Expense-ratio", "Market Cap", "P/E Ratio (TTM)", "EPS (TTM)"});

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

    public static void deleteSpread(String spreadName)
    {
        FileLoader.loadFile(FileLoader.getProgramPath2() + "/bin/output/spreads/" + spreadName + ".csv").deleteOnExit();
    }

    public static List<String> getSpreadsByName()
    {
        return new ArrayList<>(spreads.keySet());
    }

}
