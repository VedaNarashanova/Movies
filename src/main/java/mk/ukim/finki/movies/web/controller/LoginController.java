package mk.ukim.finki.movies.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.model.exception.InvalidArgumentsException;
import mk.ukim.finki.movies.model.exception.UserNotFoundException;
import mk.ukim.finki.movies.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(HttpServletRequest request){
        try{
            User user = authService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/movies";
        }catch (InvalidArgumentsException | UserNotFoundException exception){
            return "redirect:/login?error=" + exception.getMessage();
        }
    }
}
