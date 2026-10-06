package se.waterwise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);

        return "index";
    }

    @GetMapping("/watercalculator")
    public String showWaterCalculator(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        return "watercalculator";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        {
            return "AboutUs";
        }
    }
    @GetMapping("/forecast")
    public String showForecast(Model model, HttpSession session) {
        Object username = session.getAttribute("username");
        model.addAttribute("username", username);
        return "forecast";
    }
}