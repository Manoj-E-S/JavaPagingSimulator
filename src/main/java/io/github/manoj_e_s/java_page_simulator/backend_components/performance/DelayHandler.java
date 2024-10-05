package io.github.manoj_e_s.java_page_simulator.backend_components.performance;

public class DelayHandler {
    public static void delayBySeconds(int seconds, String message) {
        Logger.getInstance().log(null, message);
        try {
            Thread.sleep(seconds); //* 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
