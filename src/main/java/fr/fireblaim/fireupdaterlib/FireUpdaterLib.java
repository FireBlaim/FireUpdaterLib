package fr.fireblaim.fireupdaterlib;

import fr.fireblaim.fireupdaterlib.gui_updater.GuiUpdater;
import fr.fireblaim.fireupdaterlib.utils.Logger;
import fr.fireblaim.fireupdaterlib.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FireUpdaterLib {

    private static String link;
    private static File dest;

    private static HashMap<String, File> filesToDl = new HashMap<>();
    private static List<String> hashFiles = new ArrayList<>();

    private static List<String> ignoreDelFiles = new ArrayList<>();

    private static void indexFiles() {
        Logger.info("Indexing files...");

        String ignoreStr;
        BufferedReader ignoreFiles;

        String str;
        BufferedReader files;
        try {
            ignoreFiles = new BufferedReader(new InputStreamReader(new URL(link + "ignore.cfg").openStream()));

            while((ignoreStr = ignoreFiles.readLine()) != null) {
                ignoreDelFiles.add(ignoreStr);
            }

            files = new BufferedReader(new InputStreamReader(new URL(link + "index.php").openStream()));

            while((str = files.readLine()) != null) {
                String[] args = str.split("\\|");

                if(args.length == 2) {
                    File file = new File(dest.getPath() + "/" + args[0]);

                    hashFiles.add(args[1]);

                    if(!file.exists() || !Objects.equals(Utils.getSHA1(file), args[1])) {
                        filesToDl.put(link + "files/" + args[0], file);
                    }
                }
            }
        } catch (IOException e) {
            Logger.err("Error while indexing files... Exiting... Error: " + e);
            System.exit(1);
        }

        GuiUpdater.setFilesToDownload(filesToDl.size());

        Logger.info("Indexing finished ! There are " + filesToDl.size() + " files to download !");
    }

    private static void downloadFiles() {
        Logger.info("Downloading process started !");

        for(String dl : filesToDl.keySet()) {
            try {
                Utils.download(new URL(dl), filesToDl.get(dl));
            } catch (MalformedURLException e) {
                Logger.err("Error while downloading " + dl + " ! Error: " + e);
            }
        }

        Logger.info("Downloading process finished !");
    }

    private static void deleteUnknownFiles() {
        Logger.info("Deleting unknown files...");

        try (Stream<Path> paths = Files.walk(dest.toPath())) {
            paths.forEach(e -> {
                if(Files.isRegularFile(e)) {
                    String filePathName = e.toString().replace(dest.toString(), "").replaceFirst("\\\\", "").replaceAll("\\\\", "/");
                    if(!ignoreDelFiles.contains(filePathName)) {
                        if(!hashFiles.contains(Utils.getSHA1(e.toFile())) && !Utils.notContains(ignoreDelFiles, filePathName)) {
                            e.toFile().delete();
                            Logger.info("Deleting File: " + e.toFile().getPath());
                        }
                    }
                }
            });
        } catch (IOException e) {
            Logger.err("Error while deleting unknown files... Error: " + e);
        }
    }

    public static void startUpdate(String link, File dest) {
        System.out.println("\n" +
                "\n" +
                "  _____ _          _   _           _       _            _     _ _     \n" +
                " |  ___(_)_ __ ___| | | |_ __   __| | __ _| |_ ___ _ __| |   (_) |__  \n" +
                " | |_  | | '__/ _ \\ | | | '_ \\ / _` |/ _` | __/ _ \\ '__| |   | | '_ \\ \n" +
                " |  _| | | | |  __/ |_| | |_) | (_| | (_| | ||  __/ |  | |___| | |_) |\n" +
                " |_|   |_|_|  \\___|\\___/| .__/ \\__,_|\\__,_|\\__\\___|_|  |_____|_|_.__/ \n" +
                " __        _______ _    |_|__ ___  __  __ _____   _ _ _ _             \n" +
                " \\ \\      / / ____| |   / ___/ _ \\|  \\/  | ____| | | | | |            \n" +
                "  \\ \\ /\\ / /|  _| | |  | |  | | | | |\\/| |  _|   | | | | |            \n" +
                "   \\ V  V / | |___| |__| |__| |_| | |  | | |___  |_|_|_|_|            \n" +
                "    \\_/\\_/  |_____|_____\\____\\___/|_|  |_|_____| (_|_|_|_)            \n");

        long startTime = System.currentTimeMillis();

        FireUpdaterLib.link = link;
        FireUpdaterLib.dest = dest;

        Logger.info("Welcome to FireUpdaterLib ! Link: " + link + " , Destination Path: " + dest.getPath());

        indexFiles();

        downloadFiles();

        deleteUnknownFiles();

        long totalTime = System.currentTimeMillis() - startTime;
        int seconds = (int) (totalTime / 1000) % 60;
        int minutes = (int) ((totalTime / (1000 * 60)) % 60);
        int hours   = (int) ((totalTime / (1000 * 60 * 60)) % 24);
        String strTime = hours + " hours " + minutes + " minutes " + seconds + " seconds and " + totalTime % 1000 + " milliseconds.";
        Logger.info("Update finished, total time : " + strTime);

        Logger.info("Bye !");
    }

}
