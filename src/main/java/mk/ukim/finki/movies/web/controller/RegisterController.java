package mk.ukim.finki.movies.web.controller;

import mk.ukim.finki.movies.model.exception.InvalidArgumentsException;
import mk.ukim.finki.movies.model.exception.PasswordsDoNotMatchException;
import mk.ukim.finki.movies.service.AuthService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateOfBirth){
        try{
            this.authService.register(username, password, repeatPassword, name, surname, dateOfBirth);
            return "redirect:/login";
        }catch (InvalidArgumentsException | PasswordsDoNotMatchException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}

