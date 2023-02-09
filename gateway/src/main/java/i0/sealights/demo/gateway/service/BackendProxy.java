package i0.sealights.demo.gateway.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BackendProxy {

    public ResponseEntity<String> callUrl(final String endpointUrl, Map<String, String[]> queryParameters) {
        try {
            System.out.println("Another change in the GATEWAY");
            final String parameterString = convertQueryParameters(queryParameters);
            final URL url = new URL(endpointUrl + parameterString);
            final HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");

            int status = httpConnection.getResponseCode();
            final String receivedContent = fetchBackendResponse(httpConnection);

            return ResponseEntity.status(status).body(receivedContent);

        } catch (ProtocolException e) {
            throw new BackendExecutionException("Bad, bad protocol: " + endpointUrl, e);
        } catch (MalformedURLException e) {
            throw new BackendExecutionException("Invalid URL was specified: " + endpointUrl, e);
        } catch (IOException e) {
            throw new BackendExecutionException("IO problem: " + endpointUrl, e);
        }

    }

    private String fetchBackendResponse(HttpURLConnection httpConnection) {
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content.toString();
        } catch (Exception e) {
            throw new BackendExecutionException("Could not read the response ", e);
        }
    }

    private String convertQueryParameters(Map<String, String[]> queryParameters) throws IOException {
        if (queryParameters.isEmpty()) {
            return "";
        }

        return "?" + queryParameters.entrySet().stream()
            .map(entrySet -> entrySet.getKey() + "=" + firstElement(entrySet.getValue()))
            .collect(Collectors.joining("&"));

    }

    private String firstElement(String[] stringsArray) {
        return  (stringsArray != null && stringsArray.length > 0 && stringsArray[0] != null) ? stringsArray[0] : "";
    }
}
