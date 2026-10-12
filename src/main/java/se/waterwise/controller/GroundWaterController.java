package se.waterwise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import se.waterwise.APIs.sgu.GroundWaterData;
import se.waterwise.APIs.sgu.SguApi;

import java.io.IOException;


@RestController
public class GroundWaterController {

    private final SguApi sguApi = new SguApi();

    @GetMapping("/api/groundwater/current")
    public GroundWaterData getCurrentGroundwater() {
        try {
            // Central Gothenburg.
            return sguApi.getLatestGroundwaterData(57.7089, 11.9746);

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

