package se.waterwise.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.waterwise.database.WaterRepository;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
public class LoginController {
    private final WaterRepository waterRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public LoginController(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String municipality,
            HttpSession session){
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
        String encryptedPassword = passwordEncoder.encode(password);
        waterRepository.createUser(username,encryptedPassword,municipality);
        Map<String, Object> newUser =
                waterRepository.findUser(username);
        Integer userId = (Integer) newUser.get("userid");

        session.setAttribute("userId",userId);
        session.setAttribute("username",username);
        session.setAttribute("municipality",municipality);

        System.out.println("Account created");
        System.out.println("User ID:"+userId);
        System.out.println("Username: "+username);


        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes){

        Map<String, Object> user =
                waterRepository.findUser(username);

        //User not found
        if (user == null) {
            System.out.println("User does not exist");
            redirectAttributes.addFlashAttribute("loginMessage", "This account does not exist");
            redirectAttributes.addFlashAttribute("loginType", "error");
            return "redirect:/";
        }

        String storedPassword =
                (String) user.get("userpassword");

        if (!passwordEncoder.matches(password, storedPassword)) {
            System.out.println("Wrong password");
            redirectAttributes.addFlashAttribute("loginMessage", "Wrong password");
            redirectAttributes.addFlashAttribute("loginType", "error");
            return "redirect:/";
        }
        Integer userId = (Integer) user.get("userid");
        String municipality = (String) user.get("municipality");

        session.setAttribute("userId",userId);
        session.setAttribute("username",username);
        session.setAttribute("municipality",municipality);

        System.out.println("Login successful!");
        System.out.println("User ID: " + userId);
        System.out.println("Municipality: " + municipality);

        redirectAttributes.addFlashAttribute("loginMessage", "Logged in");
        redirectAttributes.addFlashAttribute("loginType", "success");
        return "redirect:/";
    }
@PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
}

@PostMapping("/account/municipality")
    public String changeMunicipality(
            @RequestParam String municipality,
            HttpSession session,
            RedirectAttributes redirectAttributes){
        Integer userId = (Integer) session.getAttribute("userId");


        if(userId == null){
            return "redirect:/";
        }
        waterRepository.changeMunicipality(municipality, userId);
        session.setAttribute("municipality", municipality);

    redirectAttributes.addFlashAttribute("loginMessage", "Municipality changed");
    redirectAttributes.addFlashAttribute("loginType", "success");
        return "redirect:/";
    }
    @PostMapping("/account/password")
    public  String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {


        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }
        Map<String, Object> user =
                waterRepository.findUser((String)session.getAttribute("username"));


        String storedPassword =
                (String) user.get("userpassword");

        if (!passwordEncoder.matches(currentPassword, storedPassword)) {
            System.out.println("Wrong password");
            redirectAttributes.addFlashAttribute("loginMessage", "Current password is incorrect");
            redirectAttributes.addFlashAttribute("loginType", "error");
            return "redirect:/";
        }
        if(!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            redirectAttributes.addFlashAttribute("loginMessage", "New Passwords do not match");
            redirectAttributes.addFlashAttribute("loginType", "error");
            return "redirect:/";
        }
        String encryptedPassword = passwordEncoder.encode(newPassword);
        waterRepository.updateUser(encryptedPassword,userId);

        redirectAttributes.addFlashAttribute("loginMessage", "Password changed successfully");
        redirectAttributes.addFlashAttribute("loginType", "success");
        return "redirect:/";
    }
}
