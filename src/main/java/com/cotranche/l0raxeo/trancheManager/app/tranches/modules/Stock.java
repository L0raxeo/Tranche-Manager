package com.cotranche.l0raxeo.trancheManager.app.tranches.modules;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.Date;
import java.io.IOException;

public class Stock
{
    private String url;
    private String tickerSymbol;
    private double sharePrice;
    private double yoy1Return;
    private double yoy5Return;
    private double cagr5YoY;
    private double trailing1YPrice;
    private double trailing5YPrice;
    private String type;

    public Stock(String tickerSymbol) {
        this.setTickerSymbol(tickerSymbol);
        this.setUrl("https://finance.yahoo.com/quote/" + getTickerSymbol());
        try {
            WebClient client = new WebClient();
            client.getOptions().setJavaScriptEnabled(false);
            client.getOptions().setCssEnabled(false);
            client.getOptions().setUseInsecureSSL(false);
            HtmlPage summaryPage = client.getPage(this.getUrl());
            sharePrice = Double.parseDouble(String.valueOf(summaryPage.getByXPath("//*[@id=\"quote-header-info\"]/div[3]/div[1]/div[1]/fin-streamer[1]")).split("value=")[1].split("\"")[1]);
            System.out.println("[" + getTickerSymbol() + " Profile] > PPS: " + sharePrice);
            scrapeReturns(client);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stock() {}

    public String getUrl() {
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    public String getTickerSymbol() {
        return this.tickerSymbol;
    }

    public double getSharePrice() {
        return this.sharePrice;
    }

    public double getYoy1Return() {
        return this.yoy1Return;
    }

    public double getYoy5Return() {
        return this.yoy5Return;
    }

    public double getCagr5YoY() {
        return this.cagr5YoY;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setTickerSymbol(final String symbol) {
        this.tickerSymbol = symbol;
    }

    public void setSharePrice(final double price) {
        this.sharePrice = price;
    }

    public void setTrailing1YPrice(final double price)
    {
        this.trailing1YPrice = price;
    }

    public void setTrailing5YPrice(final double price)
    {
        this.trailing5YPrice = price;
    }

    private void scrapeReturns(final WebClient client) throws IOException {
        Date lastQuinquennium = new Date(System.currentTimeMillis());
        lastQuinquennium.setYear(lastQuinquennium.getYear() - 5);
        Date lastYear = new Date(System.currentTimeMillis());
        lastYear.setYear(lastYear.getYear() - 1);

        HtmlPage historicalDataPage5Y = client.getPage("https://finance.yahoo.com/quote/" + getTickerSymbol() + "/history?period1=" + lastQuinquennium.getTime() / 1000L + "&period2=" + System.currentTimeMillis() / 1000L + "&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
        Object[] historicalDataTable5Y = ((HtmlElement)historicalDataPage5Y.getByXPath("//*[@id=\"Col1-1-HistoricalDataTable-Proxy\"]/section/div[2]/table").get(0)).asNormalizedText().lines().toArray();
        HtmlPage historicalDataPage1Y = client.getPage("https://finance.yahoo.com/quote/" + getTickerSymbol() + "/history?period1=" + lastYear.getTime() / 1000L + "&period2=" + System.currentTimeMillis() / 1000L + "&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
        Object[] historicalDataTable1Y = ((HtmlElement) historicalDataPage1Y.getByXPath("//*[@id=\"Col1-1-HistoricalDataTable-Proxy\"]/section/div[2]/table").get(0)).asNormalizedText().lines().toArray();

        trailing1YPrice= Double.parseDouble(historicalDataTable1Y[historicalDataTable1Y.length - 2].toString().split("\t")[1]);
        System.out.println("[" + getTickerSymbol() + " Profile] Trailing 1 Year Price: " + trailing1YPrice);
        trailing5YPrice= Double.parseDouble(historicalDataTable5Y[historicalDataTable5Y.length - 2].toString().split("\t")[1]);
        System.out.println("[" + getTickerSymbol() + " Profile] Trailing 5 Year Price: " + trailing5YPrice);
        yoy1Return = (sharePrice - trailing1YPrice) / trailing1YPrice;
        System.out.println("[" + getTickerSymbol() + " Profile] 1 Year Return: " + yoy1Return * 100 + "%");
        yoy5Return = (sharePrice - trailing5YPrice) / trailing5YPrice;
        System.out.println("[" + getTickerSymbol() + " Profile] 5 Year Return: " + yoy5Return * 100 + "%");
        cagr5YoY = Math.pow(sharePrice / trailing5YPrice, 0.2) - 1.0;
        System.out.println("[" + getTickerSymbol() + " Profile] Compound Annual Growth Rate (5 Years): " + cagr5YoY * 100 + "%");
    }

}