import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtils {

    private static final String REQUEST_METHOD_GET ="GET";

    public static String fetchApiResponse(String apiUrl, int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection connection = createConnection(apiUrl, connectTimeout, readTimeout);
        return readResponse(connection);
    }

    private static HttpURLConnection createConnection(String apiUrl, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(REQUEST_METHOD_GET);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        return connection;
    }

    private static String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferReader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
