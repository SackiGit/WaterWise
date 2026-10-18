package se.waterwise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import se.waterwise.database.WaterRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TestController {

    private final WaterRepository waterRepository;

    public TestController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    @GetMapping("/logs")
    public String logs(Model model, HttpSession session,
                       @RequestParam(required = false) LocalDate startDate,
                       @RequestParam(required = false) LocalDate endDate,
                       @RequestParam(defaultValue = "7")String period) {

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
        double totalLitres;
        if(total.get("totallitres")==null){
            totalLitres = 0.0;
        } else{
            totalLitres= ((Number) total.get("totallitres")).doubleValue();
        }


        int daysPosted = data.size();

        double averageDailyConsumption;
        if(daysPosted > 0){
            averageDailyConsumption = totalLitres/daysPosted;
        } else{
            averageDailyConsumption=0.0;
        }

        model.addAttribute("total", total);
        model.addAttribute("consumption", data);
        model.addAttribute("daysPosted", daysPosted);
        model.addAttribute("averageDailyConsumption", averageDailyConsumption);

        LocalDate today = LocalDate.now();
        LocalDate selectedStartDate;
        LocalDate selectedEndDate;

        if(startDate != null && endDate != null) {
          selectedStartDate = startDate;
          selectedEndDate = endDate;
          period = "custom";
        } else{
            selectedEndDate = today;
            if(period.equals("1")){
                selectedStartDate = today;
            } else if(period.equals("30")){
                selectedStartDate=today.minusDays(29);
            }else{
                period="7";
                selectedStartDate=today.minusDays(6);
            }
            }


        List<Map<String, Object>> activityConsumption =
                waterRepository.getConsumptionByActivityByDate(userId, selectedStartDate, selectedEndDate);
        model.addAttribute("activityConsumption", activityConsumption);
        model.addAttribute("period",period);
        model.addAttribute("startDate",selectedStartDate);
        model.addAttribute("endDate",selectedEndDate);
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
    @PostMapping("/logs/addCSV")
    public String addCSVlog(
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {

        Integer userId = (Integer) session.getAttribute("userId");


        if(userId == null){
            return "redirect:/";
        }
        if(file.isEmpty() || file.getSize() > 1_000_000){
            return "redirect:/logs";
        }
        try{
            byte[] bytes = file.getBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);

            String[] rows = content.split("\\R");
            rows = Arrays.copyOfRange(rows,1,rows.length);

            for (String row : rows){
                String[] values = row.split(",");
                LocalDate date = LocalDate.parse(values[0].trim());
                int duration = Integer.parseInt(values[1].trim());

                waterRepository.addCsv(userId,duration,date);
            }
        }catch (IOException e){return "redirect:/logs";}

        return "redirect:/logs";
    }
}