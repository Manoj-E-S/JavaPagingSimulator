package io.github.manoj_e_s.java_page_simulator.backend_components.performance;

public class DelayHandler {
    public static void delayByMillis(int millis, String message) {
        Logger.getInstance().log(null, message);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
