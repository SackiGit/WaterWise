package se.waterwise.APIs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SguApi {

    private static final String BASE_URL =
            "https://api.sgu.se/oppnadata/" +
                    "grundvattennivaer-sgu-hype-omraden/" +
                    "ogc/features/v1";

    private final HttpClient client;

    public SguApi() {
        client = HttpClient.newHttpClient();
    }

    public int getAreaId(double latitude, double longitude)
            throws IOException, InterruptedException {

        String url =
                BASE_URL +
                        "/collections/omraden/items" +
                        "?f=application%2Fjson" +
                        "&bbox=" + longitude + "," + latitude + ","
                        + longitude + "," + latitude;

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
                    "SGU returned HTTP " + response.statusCode()
            );
        }

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.body());

        JsonNode features = root.get("features");

        if (features == null || features.isEmpty()) {
            throw new IOException(
                    "No SGU area found for coordinates: "
                            + latitude + ", " + longitude
            );
        }

        JsonNode properties =
                features.get(0).get("properties");

        return properties.get("omrade_id").asInt();
    }



    private String getGroundwaterData(int areaId)
            throws IOException, InterruptedException {



        String url =
                BASE_URL +
                        "/collections/grundvattennivaer-tidigare/items" +
                        "?f=application%2Fjson" +
                        "&filter=omrade_id=" + areaId;

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
                    "SGU returned HTTP " + response.statusCode()
            );
        }

        return response.body();
    }



    public GroundWaterData getLatestGroundwaterData(double latitude,double longitude)
            throws IOException, InterruptedException {
        int areaId = getAreaId(latitude,longitude);

        String json = getGroundwaterData(areaId);


        ObjectMapper mapper = new ObjectMapper();


        JsonNode root = mapper.readTree(json);


        JsonNode features = root.get("features");


        if (features == null || features.isEmpty()) {
            throw new IOException(
                    "No groundwater data found for area " + areaId
            );
        }


        JsonNode latest =
                features.get(features.size() - 1);


        JsonNode properties =
                latest.get("properties");


        String date =
                properties.get("datum").asText();

        int level =
                properties.get("fyllnadsgrad_sma").asInt();


        return new GroundWaterData(areaId,date, level);
    }
}