package com.cotranche.l0raxeo.trancheManager.app.tranches;

import com.cotranche.l0raxeo.trancheManager.app.Hub;
import com.cotranche.l0raxeo.trancheManager.app.tranches.modules.Stock;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Actions
{

    private static final Scanner userInput = new Scanner(System.in);

    public static void zero()
    {
        double sharePrice, eps;

        System.out.println("[P/E Ratio] > enter share price");
        sharePrice = userInput.nextDouble();

        System.out.println("[P/E Ratio] > enter EPS");
        eps = userInput.nextDouble();

        System.out.println("[P/E Ratio] > P/E ratio: " + sharePrice / eps);

        Hub.menu();
    }

    public static void one()
    {
        double sharePrice, q1eps, q2eps, q3eps, q4eps;

        System.out.println("[Trailing P/E Ratio] > enter share price");
        sharePrice = userInput.nextDouble();

        System.out.println("[Trailing P/E Ratio] > enter Q1 EPS");
        q1eps = userInput.nextDouble();

        System.out.println("[Trailing P/E Ratio] > enter Q2 EPS");
        q2eps = userInput.nextDouble();

        System.out.println("[Trailing P/E Ratio] > enter Q3 EPS");
        q3eps = userInput.nextDouble();

        System.out.println("[Trailing P/E Ratio] > enter Q4 EPS");
        q4eps = userInput.nextDouble();

        System.out.println("[Trailing P/E Ratio] > Trailing P/E ratio: " + sharePrice / ((q1eps + q2eps + q3eps + q4eps) / 4));

        Hub.menu();
    }

    public static void two()
    {
        double sharePrice, bookValue;

        System.out.println("[P/B Ratio] > enter share price");
        sharePrice = userInput.nextDouble();

        System.out.println("[P/B Ratio] > enter book value");
        bookValue = userInput.nextDouble();

        System.out.println("[P/B Ratio] > P/B ratio: " + sharePrice / bookValue);

        Hub.menu();
    }

    public static void three()
    {
        double p, r, n, t;

        System.out.println("[Compound Interest] > enter principle");
        p = userInput.nextDouble();

        System.out.println("[Compound Interest] > enter rate");
        r = userInput.nextDouble();

        System.out.println("[Compound Interest] > enter compound frequency");
        n = userInput.nextDouble();

        System.out.println("[Compound Interest] > enter time in years");
        t = userInput.nextDouble();

        System.out.println("[Compound Interest] > final amount: " +
                p * Math.pow(1 + (r / n), n * t));

        Hub.menu();
    }

    public static void four()
    {
        double val1, val2;

        System.out.println("[PC (Find Percent)] > enter value 1");
        val1 = userInput.nextDouble();

        System.out.println("[PC (Find Percent)] > enter value 2");
        val2 = userInput.nextDouble();

        System.out.println("[PC (Find Percent)] > Percent Change: " +
                ((val2 - val1) / val1) * 100 + "%");

        Hub.menu();
    }

    public static void five()
    {
        double val2, percentChange;

        System.out.println("[PC (Find Value 1)] > enter value 2");
        val2 = userInput.nextDouble();

        System.out.println("[PC (Find Value 1)] > enter percent change (decimal form)");
        percentChange = userInput.nextDouble();

        System.out.println("[PC (Find Value 1)] > value 1: " +
                val2 / (percentChange + 1));

        Hub.menu();
    }

    public static void six()
    {
        double val1, percentChange;

        System.out.println("[PC (Find Value 2)] > enter value 1");
        val1 = userInput.nextDouble();

        System.out.println("[PC (Find Value 2)] > enter percent change (decimal form)");
        percentChange = userInput.nextDouble();

        System.out.println("[PC (Find Value 2)] > value 2: " +
                ((percentChange * val1) + val1));

        Hub.menu();
    }

    public static void seven()
    {
        double val1, val2, t;

        System.out.println("[CAGR] > enter initial value");
        val1 = userInput.nextDouble();

        System.out.println("[CAGR] > enter final value");
        val2 = userInput.nextDouble();

        System.out.println("[CAGR] > enter time in years");
        t = userInput.nextDouble();

        System.out.println("[CAGR] > compound annual growth rate: " +
                (Math.pow(val2 / val1, 1 / t) - 1) * 100 + "%");

        Hub.menu();
    }

    public static void eight()
    {
        String ticker;

        System.out.println("[Stock Stats] > enter ticker symbol");
        ticker = userInput.next();

        try
        {
            Desktop.getDesktop().browse(new URI("https://finance.yahoo.com/quote/" + ticker));
        }
        catch (IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }

        Hub.menu();
    }

    public static void nine()
    {
        String ticker;
        Stock stock;

        System.out.println("[Stock Profiler] > enter ticker symbol");
        ticker = userInput.next();

        System.out.println("[Stock Profiler] > would you like to automatically profile " + ticker + " Y/n");
        String autoScrape = userInput.next();
        if (autoScrape.equalsIgnoreCase("y"))
        {
            System.out.println("[Stock Profiler] > scraping yahoo finance...");
            stock = new Stock(ticker);
            System.out.println("[Stock Profiler] > successfully profiled stock");

            System.out.println("[Stock Profiler] > enter name of spread where stock is being profiled");
            System.out.println("[Stock Profiler] > existing spreads:");

            for (String spreadName : StockSpreads.getSpreadsByName())
                System.out.println("[Stock Profiler] > " + spreadName);

            StockSpreads.addProfile(userInput.next(), stock);
        }
        else if (autoScrape.equalsIgnoreCase("n"))
        {
            stock = new Stock();
            stock.setTickerSymbol(ticker);

            System.out.println("[Stock Profiler] > enter price per share");
            stock.setSharePrice(userInput.nextDouble());

            System.out.println("[Stock Profiler] > enter trailing 1 year price");
            stock.setTrailing1YPrice(userInput.nextDouble());

            System.out.println("[Stock Profiler] > enter trailing 5 year price");
            stock.setTrailing5YPrice(userInput.nextDouble());

            System.out.println("[Stock Profiler] > enter name of spread where stock is being profiled");
            System.out.println("[Stock Profiler] > existing spreads:");

            for (String spreadName : StockSpreads.getSpreadsByName())
                System.out.println("[Stock Profiler] > " + spreadName);

            StockSpreads.addProfile(userInput.next(), stock);
        }
        else
        {
            System.out.println("[Stock Profiler] > invalid argument");
            nine();
        }

        Hub.menu();
    }

    public static void ten()
    {
        System.out.println("[Spread Manager] > enter number associated with desired action");
        System.out.println("[Spread Manager] > (0) Create Spread");
        System.out.println("[Spread Manager] > (1) Load Spread");
        System.out.println("[Spread Manager] > (2) Export Spread");

        System.out.println();
        System.out.println("[Spread Manager] > (-1) reveal all loaded spreads");

        switch (userInput.nextInt()) {
            case -1 -> {
                System.out.println("Loaded Spreads:");
                for (String spreadName : StockSpreads.getSpreadsByName())
                    System.out.println(spreadName);
                ten();
            }
            case 0 -> {
                System.out.println("[Spread Manager] > enter spread name");
                StockSpreads.createSpread(userInput.next());
                System.out.println("[Spread Manager] > successfully created spread");
            }
            case 1 -> {
                System.out.println("[Spread Manager] > enter spread name");
                StockSpreads.loadSpread(userInput.next());
                System.out.println("[Spread Manager] > successfully loaded spread");
            }
            case 2 -> {
                System.out.println("[Spread Manager] > enter spread name");
                StockSpreads.exportSpread(userInput.next());
                System.out.println("[Spread Manager] > successfully exported spread");
            }
        }

        Hub.menu();
    }

}
