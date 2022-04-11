package com.cotranche.l0raxeo.trancheManager;

import com.cotranche.l0raxeo.trancheManager.app.TMLauncher;

import java.awt.*;
import java.io.Console;
import java.io.IOException;

public class Launcher
{

    public static void main(String[] args) throws IOException {
        Console console = System.console();
        if(console == null && !GraphicsEnvironment.isHeadless()){
            String filename = TMLauncher.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
            Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""});
        }else{
            TMLauncher.main(new String[0]);
            System.out.println("Program has ended, please type 'exit' to close the console");
        }
    }

}
