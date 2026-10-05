package se.waterwise.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.waterwise.database.WaterRepository;

import java.util.Map;

@Controller
public class LoginController {
    private final WaterRepository waterRepository;

    public LoginController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            HttpSession session) {
        Map<String, Object> user =
            waterRepository.findUser(username);
        if (user != null) {
            System.out.println("User already exists");
            return "redirect:/";
        }

        if(!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return "redirect:/";
        }
        waterRepository.createUser(username,password);
        Map<String, Object> newUser =
                waterRepository.findUser(username);
        Integer userId = (Integer) newUser.get("userid");

        session.setAttribute("userId",userId);
        session.setAttribute("username",username);

        System.out.println("Account created");
        System.out.println("User ID:"+userId);
        System.out.println("Username: "+username);

        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        Map<String, Object> user =
                waterRepository.findUser(username);

        if (user == null) {
            System.out.println("User does not exist");
            return "redirect:/";
        }

        String storedPassword =
                (String) user.get("userpassword");

        if (!storedPassword.equals(password)) {
            System.out.println("Wrong password");
            return "redirect:/";
        }
        Integer userId = (Integer) user.get("userid");

        session.setAttribute("userId",userId);
        session.setAttribute("username",username);

        System.out.println("Login successful!");
        System.out.println("User ID: " + userId);

        return "redirect:/";
    }
@PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
}
}
