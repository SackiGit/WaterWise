package se.waterwise.APIs.meteo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class RainForecastController {

    private final OpenMeteoApi openMeteoApi = new OpenMeteoApi();

    @GetMapping("/api/rain-forecast")
    public List<RainData> getRainForecast() {
        try {
            // Gothenburg coordinates.
            return openMeteoApi.getRainForecast(57.79032135129441, 11.98393584590727);

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