package com.github.Viduality.ConfigMaster;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ConfigMaster {

    public static void replaceFileData(Plugin plugin, String FilewithDir, String replace, String with) {
        loadYAML(plugin, FilewithDir);
        String inputStr = null;


        if (replace.contains("#")) {
            List<String> replacementlist = Arrays.asList(replace.split("#"));
            List<String> newStringList = Arrays.asList(with.split("#"));



            try {
                BufferedReader file = new BufferedReader(new FileReader(plugin.getDataFolder() + FilewithDir));
                String line;
                StringBuffer inputBuffer = new StringBuffer();

                while ((line = file.readLine()) != null) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
                inputStr = inputBuffer.toString();

                file.close();
            } catch (Exception e) {
                System.out.println("Problem reading file.");
                e.printStackTrace();
            }



            for (int i = 0; i < replacementlist.size(); i++) {
                String replacement = replacementlist.get(i);
                String newString = newStringList.get(i);

                if (inputStr.contains(replace)) {
                    String OldString = plugin.getConfig().getString(replacement);
                    String replace1 = replacement + ":";
                    String with1 = replacement + ": " + newString;
                    String OldLine = replacement + ": " + OldString;
                    String NewLine = replacement + ": " + newString;
                    if (OldString == null) {
                        inputStr = inputStr.replace(replace1, with1);
                    } else {
                        inputStr = inputStr.replace(OldLine, NewLine);
                    }
                } else System.out.println("Keine Zeile in der Config gefunden!");



            }
        }


        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(plugin.getDataFolder() + FilewithDir));
            String line;
            StringBuffer inputBuffer = new StringBuffer();

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            inputStr = inputBuffer.toString();

            file.close();

            /// Whoever reads the following lines... Please, don't blame me for it, i know this is absolut botched xD

            if (inputStr.contains(replace)) {
                String OldString = plugin.getConfig().getString(replace);
                String replace1 = replace + ":";
                String with1 = replace + ": " + with;
                String OldLine = replace + ": " + OldString;
                String NewLine = replace + ": " + with;
                if (OldString == null) {
                    inputStr = inputStr.replace(replace1, with1);
                } else {
                    inputStr = inputStr.replace(OldLine, NewLine);
                }
            } else System.out.println("Keine Zeile in der Config gefunden!");


            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(plugin.getDataFolder() + FilewithDir);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
            e.printStackTrace();
        }
    }







    public static void loadYAML(Plugin plugin, String filewithPath) {
        try {
            plugin.getConfig().load(plugin.getDataFolder() + filewithPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
