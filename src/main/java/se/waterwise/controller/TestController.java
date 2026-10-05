package se.waterwise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.waterwise.database.WaterRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

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
    public String logs(Model model, HttpSession session,
                       @RequestParam(required = false) LocalDate startDate,
                       @RequestParam(required = false) LocalDate endDate) {

        Integer userId = (Integer) session.getAttribute("userId");

        if(userId == null){
            return "redirect:/";
        }
        Object username = session.getAttribute("username");
        model.addAttribute("username",username);

        List<Map<String, Object>> data =
                waterRepository.getDailyConsumption(userId);

        Map<String, Object> total =
                waterRepository.getTotalConsumption(userId);

        model.addAttribute("total", total);
        model.addAttribute("consumption", data);
        if(startDate != null && endDate != null) {
            List<Map<String, Object>> activityConsumption =
                    waterRepository.getConsumptionByActivityByDate(userId, startDate, endDate);
            System.out.println(activityConsumption);
            model.addAttribute("activityConsumption", activityConsumption);
        }
        return "logs";
    }


    @PostMapping("/logs/add")
    public String addLog(
            @RequestParam String activity,
            @RequestParam int duration,
            @RequestParam LocalDate date,
            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if(userId == null){
            return "redirect:/";
        }
        waterRepository.addConsumption(userId, activity, duration, date);

        return "redirect:/logs";
    }
}