package se.waterwise.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import se.waterwise.database.WaterRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

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
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.with(firstDayOfYear());
        LocalDate lastDay = today.with(lastDayOfYear());
        String municipality = (String) session.getAttribute("municipality");
        if (municipality == null){
            municipality="goteborg";
            session.setAttribute("municipality",municipality);
        }
        List<Map<String, Object>> munCon = waterRepository.getMunConMonth(municipality, firstDay, lastDay);
        model.addAttribute("munCon",munCon);
        System.out.println(model.getAttribute("munCon"));
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