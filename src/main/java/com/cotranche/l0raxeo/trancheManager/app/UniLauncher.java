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
        System.out.println("initialize project structure...");
        FileLoader.createDir(FileLoader.getProgramPath2() + "/spreads");

        System.out.println("initializing CSV Loader...");
        CSVLoader csvLoader = new CSVLoader();

        // your directory

        File f = new File(FileLoader.getProgramPath2() + "/spreads/");
        System.out.println("loading all spreadsheets in directory path: " + f.getPath());
        for (String spreadName : Objects.requireNonNull(f.list((dir, name) -> name.endsWith("csv"))))
        {
            StockSpreads.loadSpread(String.valueOf(spreadName.split(".csv")[0]));
        }
    }

    public static void main(String[] args)
    {
        System.out.println("preparing program...");
        init();

        System.out.println("""
                  _____      _                        _         \s
                 / ____|    | |                      | |        \s
                | |     ___ | |_ _ __ __ _ _ __   ___| |__   ___\s
                | |    / _ \\| __| '__/ _` | '_ \\ / __| '_ \\ / _ \\
                | |___| (_) | |_| | | (_| | | | | (__| | | |  __/
                 \\_____\\___/ \\__|_|  \\__,_|_| |_|\\___|_| |_|\\___|""".indent(1));

        System.out.println("Welcome to Tranche-Manager");
        System.out.println("-----------------------------");
        System.out.println("[Hub] > Welcome to the Hub. Please enter the number corresponding to the desired action");

        Hub.menu();
    }

}
