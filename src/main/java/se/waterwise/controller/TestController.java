package se.waterwise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.waterwise.database.WaterRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private final WaterRepository waterRepository;

    public TestController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    @GetMapping("/logs")
    public String logs(Model model) {

        List<Map<String, Object>> data =
                waterRepository.getDailyConsumption(1);

        Map<String, Object> total =
                waterRepository.getTotalConsumption(1);

        model.addAttribute("total", total);
        model.addAttribute("consumption", data);

        return "logs";
    }

    @PostMapping("/logs/add")
    public String addLog(
            @RequestParam String activity,
            @RequestParam int duration,
            @RequestParam LocalDate date) {

        waterRepository.addConsumption(1, activity, duration, date);

        return "redirect:/logs";
    }
}