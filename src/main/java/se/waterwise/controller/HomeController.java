package se.waterwise.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import se.waterwise.database.WaterRepository;
import java.util.Map;

@Controller
public class HomeController {
    private final WaterRepository waterRepository;
    public HomeController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }
    @GetMapping("/")
    public String showHomePage(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        Map<String, Object> recommendation = waterRepository.getRecommendation();
        model.addAttribute("recommendation", recommendation.get("recommendation"));
        System.out.println(recommendation.get("recommendation"));

        return "index";
    }

    @GetMapping("/watercalculator")
    public String showWaterCalculator(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        Map<String, Object> recommendation = waterRepository.getRecommendation();
        model.addAttribute("recommendation", recommendation.get("recommendation"));
        return "watercalculator";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        Map<String, Object> recommendation = waterRepository.getRecommendation();
        model.addAttribute("recommendation", recommendation.get("recommendation"));
        return "AboutUs";
    }
    @GetMapping("/forecast")
    public String showForecast(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        Map<String, Object> recommendation = waterRepository.getRecommendation();
        model.addAttribute("recommendation", recommendation.get("recommendation"));
        return "forecast";
    }
}