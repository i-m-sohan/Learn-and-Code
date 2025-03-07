import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class ApiUtils {
    public static String fetchApiResponse(String apiUrl, int connectTimeout, int readTimeout) throws IOException {
        java.net.URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        StringBuilder response = new StringBuilder();
        String responseLines;
        while ((responseLines = bufferReader.readLine()) != null) {
            response.append(responseLines);
        }
        return response.toString();
    }
}