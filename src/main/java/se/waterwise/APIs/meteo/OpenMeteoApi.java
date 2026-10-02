package se.waterwise.APIs.meteo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoApi {

    private static final String BASE_URL =
            "https://api.open-meteo.com/v1/forecast";

    private final HttpClient client;

    public OpenMeteoApi() {
        client = HttpClient.newHttpClient();
    }

    public List<RainData> getRainForecast(double latitude, double longitude)
            throws IOException, InterruptedException {

        String url =
                BASE_URL +
                        "?latitude=" + latitude +
                        "&longitude=" + longitude +
                        "&daily=rain_sum" +
                        "&forecast_days=7" +
                        "&timezone=auto";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "Open-Meteo returned HTTP "
                            + response.statusCode()
            );
        }

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root =
                mapper.readTree(response.body());

        JsonNode daily =
                root.get("daily");

        JsonNode dates =
                daily.get("time");

        JsonNode rain =
                daily.get("rain_sum");

        List<RainData> forecast =
                new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {

            String date =
                    dates.get(i).asText();

            double rainAmount =
                    rain.get(i).asDouble();

            forecast.add(
                    new RainData(date, rainAmount)
            );
        }

        return forecast;
    }

}
