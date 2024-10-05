package io.github.manoj_e_s.java_page_simulator.backend_components.performance;

public class Logger {
    public boolean verbose = false;

    private static Logger instance;

    private Logger(boolean verbose) {
        this.verbose = verbose;
    }

    public static void configure(boolean verbose) {
        Logger.instance = new Logger(verbose);
    }

    public static Logger getInstance() {
        return Logger.instance;
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    public void log(Object o, String message) {
        if (o != null) {
            System.out.println(message + o);
            return;
        }
        System.out.println(message);
    }

    public void logVerbose(Object o, String message) {
        if(this.isVerbose()) {
            this.log(o, message);
        }
    }
}
