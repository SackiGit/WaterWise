package se.waterwise;

import org.springframework.boot.SpringApplication;
import se.waterwise.APIs.meteo.*;
import se.waterwise.APIs.sgu.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WaterwiseApplication {

	public static void main(String[] args) {

		SpringApplication.run(WaterwiseApplication.class, args);

//		SguApi sgu = new SguApi();
//		try {
//			GroundWaterData waterData = sgu.getLatestGroundwaterData(57.79032135129441,11.98393584590727);
//			System.out.println(waterData.getLevel());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		List<RainData> rainList = new ArrayList<>();
		OpenMeteoApi weatherApi = new OpenMeteoApi();
		try {
			rainList = weatherApi.getRainForecast(57.79032135129441,11.98393584590727);
			for(RainData data:rainList) {
				System.out.println(data.getDate()+": "+data.getRain()+ " mm");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
