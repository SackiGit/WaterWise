package se.waterwise;

import se.waterwise.APIs.GroundWaterData;
import se.waterwise.APIs.SguApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterwiseApplication {

	public static void main(String[] args) {

		//SpringApplication.run(WaterwiseApplication.class, args);
		SguApi sgu = new SguApi();
		try {
			GroundWaterData waterData = sgu.getLatestGroundwaterData(57.79032135129441,11.98393584590727);
			System.out.println(waterData.getLevel());
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
