package fr.fireblaim.fireupdaterlib.gui_updater;

public class GuiUpdater {

    private static int filesToDownload = 0;
    private static int downloadedFiles = 0;

    public static void incrementDownloadedFiles() {
        setDownloadedFiles(getDownloadedFiles() + 1);
    }

    public static int getFilesToDownload() {
        return filesToDownload;
    }

    public static void setFilesToDownload(int filesToDownload) {
        GuiUpdater.filesToDownload = filesToDownload;
    }

    public static int getDownloadedFiles() {
        return downloadedFiles;
    }

    public static void setDownloadedFiles(int downloadedFiles) {
        GuiUpdater.downloadedFiles = downloadedFiles;
    }
}
