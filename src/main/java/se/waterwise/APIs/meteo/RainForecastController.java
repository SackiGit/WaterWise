package se.waterwise.APIs.meteo;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.waterwise.database.WaterRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class RainForecastController {

    private final WaterRepository waterRepository;
    public RainForecastController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }
    private final OpenMeteoApi openMeteoApi = new OpenMeteoApi();
   
    @GetMapping("/api/rain-forecast")
    public List<RainData> getRainForecast(HttpSession session) {

        // Default coordinates: Gothenburg
        double lat = 57.7089;
        double lon = 11.9746;

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            String municipality =
                    (String) session.getAttribute("municipality");
            if(municipality != null && !municipality.isBlank()) {
                Map<String, Object> coords = waterRepository.getCoords(municipality);
                lat = ((Number) coords.get("Lat")).doubleValue();
                lon = ((Number) coords.get("Long")).doubleValue();
            }
        }

        try {
            // Gothenburg coordinates.
            return openMeteoApi.getRainForecast(lat, lon);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Forecast request interrupted",
                    e
            );

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Could not retrieve weather forecast",
                    e
            );
        }
    }
}