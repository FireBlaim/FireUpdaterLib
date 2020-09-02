package fr.fireblaim.fireupdaterlib.utils;

import fr.fireblaim.fireupdaterlib.gui_updater.GuiUpdater;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;

public class Utils {

    public static void download(URL urlToFile, File fileDest) {
        fileDest.getParentFile().mkdirs();

        Logger.info("Downloading File : " + urlToFile.toString() + " TO " + fileDest.toString());

        try {
            HttpURLConnection connection = (HttpURLConnection) urlToFile.openConnection();

            connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");

            DataInputStream dis = new DataInputStream(connection.getInputStream());

            byte[] fileData = new byte[connection.getContentLength()];

            int x;
            for (x = 0; x < fileData.length; x++)  {
                fileData[x] = dis.readByte();
            }

            dis.close();

            FileOutputStream fos = new FileOutputStream(fileDest);
            fos.write(fileData);

            fos.close();

            GuiUpdater.incrementDownloadedFiles();
        } catch (IOException e) {
            Logger.warn("File " + urlToFile.toString() + " wasn't downloaded, error: " + e);
        }
    }

    public static String getSHA1(File file) {
        DigestInputStream stream = null;
        try {
            stream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("SHA-1"));
            byte[] buffer = new byte[65536];

            int read = stream.read(buffer);
            while(read >= 1)
                read = stream.read(buffer);
        } catch(Exception ignored) {
            return null;
        }
        finally {
            if(stream != null)
                try {
                    stream.close();
                }
            catch(IOException ignored) {}
        }

        return String.format("%1$032x", new BigInteger(1, stream.getMessageDigest().digest()));
    }

    public static boolean notContains(List<String> list, String str) {
        boolean contains = false;
        for (String s : list)
            if(str.contains(s)) {
                contains = true;
                break;
            }
        return contains;
    }
}
