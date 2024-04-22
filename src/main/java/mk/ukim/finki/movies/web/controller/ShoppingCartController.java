package mk.ukim.finki.movies.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.movies.model.ShoppingCart;
import mk.ukim.finki.movies.model.TicketOrder;
import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.service.ShoppingCartService;
import mk.ukim.finki.movies.service.TicketOrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final TicketOrderService ticketOrderService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, TicketOrderService ticketOrderService) {
        this.shoppingCartService = shoppingCartService;
        this.ticketOrderService = ticketOrderService;
    }



    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest request, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        User user = (User) request.getSession().getAttribute("user");
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getId());
        model.addAttribute("orders", this.shoppingCartService.listAllTicketOrdersInShoppingCart(shoppingCart.getId()));
        model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";
    }

    @GetMapping("/history")
    public String getShoppingCartsHistory(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        List<ShoppingCart> shoppingCarts = this.shoppingCartService.listAllShoppingCartsOfUser(user.getId());

        if(request.getParameter("from") != null && !request.getParameter("from").isEmpty() &&
                request.getParameter("to") != null && !request.getParameter("to").isEmpty()){
            LocalDateTime from = LocalDateTime.parse(request.getParameter("from"));
            LocalDateTime to = LocalDateTime.parse(request.getParameter("to"));
            shoppingCarts = this.shoppingCartService.listAllShoppingCartsOfUserBetween(user.getId(), from, to);
        }

        model.addAttribute("carts", shoppingCarts);
        model.addAttribute("bodyContent", "shopping-carts-history");
        return "master-template";
    }

    @PostMapping("/add-product")
    public String addTicketOrderToShoppingCart(@RequestParam String selectedMovie,
                                               @RequestParam Integer numTickets,
                                               HttpServletRequest request){
        try{
            TicketOrder order = ticketOrderService.placeOrder(selectedMovie, numTickets);
            User user = (User) request.getSession().getAttribute("user");
            ShoppingCart shoppingCart = this.shoppingCartService.addTicketOrderToShoppingCart(user.getId(), order.getId());
            return "redirect:/shopping-cart";
        }catch (RuntimeException exception){
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        this.shoppingCartService.removeFromCart(user.getId(), id);
        return "redirect:/shopping-cart";
    }

    @PostMapping("/confirm")
    public String confirmOrder(HttpServletRequest request,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateOfCreation){
        User user = (User) request.getSession().getAttribute("user");
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getId());
        this.shoppingCartService.finishShoppingCart(shoppingCart.getId(), dateOfCreation);
        return "redirect:/movies";
    }

}
