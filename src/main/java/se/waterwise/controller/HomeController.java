package se.waterwise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/watercalculator")
    public String showWaterCalculator() {
        return "watercalculator";
    }

    @GetMapping("/about-us")
    public String showAboutUs() {
        return "AboutUs";
    }
}
