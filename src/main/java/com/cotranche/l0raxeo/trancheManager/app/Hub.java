package com.cotranche.l0raxeo.trancheManager.app;

import com.cotranche.l0raxeo.trancheManager.app.tranches.Actions;
import com.cotranche.l0raxeo.trancheManager.app.tranches.StockSpreads;

import java.util.Scanner;

public class Hub
{

    public static void menu()
    {
        System.out.println("-----------------------------");
        System.out.println("[Hub] > (0) - P/E Ratio");
        System.out.println("[Hub] > (1) - Trailing P/E Ratio");
        System.out.println("[Hub] > (2) - P/B Ratio");
        System.out.println("[Hub] > (3) - Compound Interest");
        System.out.println("[Hub] > (4) - Percent Change (Find Percent)");
        System.out.println("[Hub] > (5) - Percent Change (Find Value 1)");
        System.out.println("[Hub] > (6) - Percent Change (Find Value 2)");
        System.out.println("[Hub] > (7) - Compound Annual Growth Rate");
        System.out.println("[Hub] > (8) - Stock Stats (Yahoo Finance WebPage)");
        System.out.println("[Hub] > (9) - Stock Profiler");
        System.out.println("[Hub] > (10) - Spread Manager");
        System.out.println();
        System.out.println("Type -e/x- to exit");

        String userInput = new Scanner(System.in).next();

        try
        {
            performAction(Integer.parseInt(userInput));
        }
        catch (NumberFormatException e)
        {
            if (userInput.equals("e/x"))
            {
                System.out.println("[Program] > saving all spreads...");
                for (String spreadName : StockSpreads.getSpreadsByName())
                    StockSpreads.exportSpread(spreadName);

                System.out.println("[Program] > shutting down...");
                System.exit(0);
            }

            System.out.println("[Hub] > invalid argument");
            menu();
        }
    }

    public static void performAction(int id)
    {
        System.out.println("-----------------------------");

        switch (id)
        {
            case 0 -> Actions.zero();
            case 1 -> Actions.one();
            case 2 -> Actions.two();
            case 3 -> Actions.three();
            case 4 -> Actions.four();
            case 5 -> Actions.five();
            case 6 -> Actions.six();
            case 7 -> Actions.seven();
            case 8 -> Actions.eight();
            case 9 -> Actions.nine();
            case 10 -> Actions.ten();
        }
    }

}
