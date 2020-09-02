package fr.fireblaim.fireupdaterlib.utils;

import net.wytrem.logging.LoggerFactory;

public class Logger {

    //Based on WyLog created by wytrem

    private static final net.wytrem.logging.Logger logger = LoggerFactory.getLogger("FireUpdaterLib");

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void err(String msg) {
        logger.error(msg);
    }

    public static void warn(String msg) {
        logger.warning(msg);
    }

    public static net.wytrem.logging.Logger getLogger() {
        return Logger.logger;
    }

}
