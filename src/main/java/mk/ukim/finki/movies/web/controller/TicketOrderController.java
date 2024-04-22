package mk.ukim.finki.movies.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.movies.model.TicketOrder;
import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.service.AuthService;
import mk.ukim.finki.movies.service.MovieService;
import mk.ukim.finki.movies.service.ShoppingCartService;
import mk.ukim.finki.movies.service.TicketOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;
    private final MovieService movieService;
    private final AuthService authService;
    private final ShoppingCartService shoppingCartService;

    public TicketOrderController(TicketOrderService ticketOrderService, MovieService movieService, AuthService authService, ShoppingCartService shoppingCartService) {
        this.ticketOrderService = ticketOrderService;
        this.movieService = movieService;
        this.authService = authService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getOrdersPage(HttpServletRequest request, Model model){
        List<TicketOrder> orders = ticketOrderService.listAll();
        model.addAttribute("movies", movieService.listAll());
        model.addAttribute("users", authService.listAll());

        if(request.getParameter("filterByMovie") != null && !request.getParameter("filterByMovie").isEmpty()){
            orders = ticketOrderService.filterByMovie(request.getParameter("filterByMovie"));
        }

        if(request.getParameter("filterByUser") != null && !request.getParameter("filterByUser").isEmpty()){
            orders = ticketOrderService.filterByUser(request.getParameter("filterByUser"));
        }

        if(request.getParameter("from") != null && !request.getParameter("from").isEmpty()
                && request.getParameter("to") != null && !request.getParameter("to").isEmpty()){
            LocalDateTime from = LocalDateTime.parse(request.getParameter("from"));
            LocalDateTime to = LocalDateTime.parse(request.getParameter("to"));
            orders = shoppingCartService.findOrdersBetween(from, to);
        }

        model.addAttribute("statusOfTicket", this.ticketOrderService.getStatusOfTickets());
        model.addAttribute("userIdOfTicket", this.ticketOrderService.getUserNamePerMovie());
        model.addAttribute("datesOfOrders", this.shoppingCartService.getTimeOfTicketOrders());
        model.addAttribute("orders", orders);
        model.addAttribute("bodyContent", "listOrders");
        return "master-template";
    }

    @GetMapping("/details")
    public String getOrderPage(@RequestParam String selectedMovie,
                               @RequestParam Integer numTickets,
                               HttpServletRequest request,
                               Model model){

        User user = (User)request.getSession().getAttribute("user");

        model.addAttribute("selectedMovie", selectedMovie);
        model.addAttribute("name", user.getUserFullName().getName());
        model.addAttribute("numTickets", numTickets);

        model.addAttribute("bodyContent", "orderConfirmation");
        return "master-template";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam String selectedMovie,
                               @RequestParam Integer numTickets){
        ticketOrderService.placeOrder(selectedMovie, numTickets);
        return "redirect:/movies";
    }

    @PostMapping("/duplicate")
    public String duplicateOrder(@RequestParam String selectedMovie,
                                 @RequestParam Integer numTickets){
        ticketOrderService.placeOrder(selectedMovie, numTickets);
        return "redirect:/movies";
    }
}
