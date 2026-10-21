package se.waterwise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import se.waterwise.APIs.sgu.GroundWaterData;
import se.waterwise.APIs.sgu.SguApi;
import se.waterwise.database.WaterRepository;

import java.io.IOException;


import java.util.Map;
import jakarta.servlet.http.HttpSession;


@RestController
public class GroundWaterController {
    private final WaterRepository waterRepository;
    public GroundWaterController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }
    private final SguApi sguApi = new SguApi();

    @GetMapping("/api/groundwater/current")
    public GroundWaterData getCurrentGroundwater(HttpSession session) {

        // Default coordinates: Gothenburg
        double lat = 57.7089;
        double lon = 11.9746;

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            String municipality =
                    (String) session.getAttribute("municipality");

            if (municipality != null && !municipality.isBlank()) {
                Map<String, Object> coords =
                        waterRepository.getCoords(municipality);

                lat = ((Number) coords.get("Lat")).doubleValue();
                lon = ((Number) coords.get("Long")).doubleValue();
            }
        }

        try {

            return sguApi.getLatestGroundwaterData(lat,lon);

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Groundwater request interrupted.",
                    exception
            );

        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Could not retrieve groundwater data.",
                    exception
            );
        }
    }
}

