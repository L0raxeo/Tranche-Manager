package com.cotranche.l0raxeo.trancheManager.app;
import com.cotranche.l0raxeo.trancheManager.app.tranches.StockSpreads;
import com.cotranche.l0raxeo.trancheManager.utils.CSVLoader;
import com.cotranche.l0raxeo.trancheManager.utils.FileLoader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class UniLauncher
{

    public static void init()
    {
        FileLoader.createDir(FileLoader.getProgramPath2() + "/bin/output/spreads");

        CSVLoader csvLoader = new CSVLoader();

        // your directory
        File f = new File(FileLoader.getProgramPath2() + "/bin/output/spreads/");
        for (String spreadName : Objects.requireNonNull(f.list((dir, name) -> name.endsWith("csv"))))
        {
            StockSpreads.loadSpread(String.valueOf(spreadName.split(".csv")[0]));
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        System.out.println("preparing program...");
        init();

        System.out.println("Welcome to Tranche-Manager");
        System.out.println("made by Cotranche");
        System.out.println("-----------------------------");
        System.out.println("[Hub] > Welcome to the Hub. Please enter the number corresponding to the desired action");

        Hub.menu();
    }

}
