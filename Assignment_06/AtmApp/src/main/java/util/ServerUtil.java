package util;

import exception.ServerUnavailableException;

public class ServerUtil {
    public static void checkServerAvailability() throws ServerUnavailableException {
        if (Math.random() < 0.1) {
            throw new ServerUnavailableException("Server connection failed.");
        }
    }
}
