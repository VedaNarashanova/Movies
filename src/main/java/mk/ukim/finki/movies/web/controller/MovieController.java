package mk.ukim.finki.movies.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.service.MovieService;
import mk.ukim.finki.movies.service.ProductionService;
import mk.ukim.finki.movies.service.TicketOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final TicketOrderService ticketOrderService;
    private final ProductionService productionService;

    public MovieController(MovieService movieService, TicketOrderService ticketOrderService, ProductionService productionService) {
        this.movieService = movieService;
        this.ticketOrderService = ticketOrderService;
        this.productionService = productionService;
    }

    @GetMapping
    public String getMoviesPage(@RequestParam(required = false) String error, Model model, HttpServletRequest request){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Movie> movies = this.movieService.listAll();

        if(request.getParameter("searchText") != null && !request.getParameter("searchText").isEmpty() &&
                request.getParameter("searchRating") != null && !request.getParameter("searchRating").isEmpty()){
            movies = this.movieService.filterByTextAndAboveRating(request.getParameter("searchText"),Double.parseDouble(request.getParameter("searchRating")));
        }
        else if(request.getParameter("searchText") != null && !request.getParameter("searchText").isEmpty()){
            movies = this.movieService.filterByText(request.getParameter("searchText"));
        }
        else if(request.getParameter("searchRating") != null && !request.getParameter("searchRating").isEmpty()){
            movies = this.movieService.filterAboveRating(Double.parseDouble(request.getParameter("searchRating")));
        }

        if(request.getParameter("rateMovieId") != null && !request.getParameter("rateMovieId").isEmpty()){
            this.movieService.rateMovie(Long.parseLong(request.getParameter("rateMovieId")),
                    Double.parseDouble(request.getParameter("rateMovieRating")));
        }

        model.addAttribute("movies", movies);
        model.addAttribute("ticketsPerMovie", ticketOrderService.getTicketsPerMovie());
        model.addAttribute("bodyContent", "listMovies");
        return "master-template";
    }

    @GetMapping("/add-form")
    public String getAddMoviePage(Model model){
        model.addAttribute("productions", this.productionService.findAll());
        model.addAttribute("bodyContent", "add-movie");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveMovie(@RequestParam String movieTitle,
                            @RequestParam String summary,
                            @RequestParam Double rating,
                            @RequestParam Long production){

        this.movieService.add(movieTitle, summary, rating, production);
        return "redirect:/movies";
    }

    @GetMapping("edit-form/{id}")
    public String getEditMovieForm(@PathVariable Long id, Model model){
        if(movieService.findById(id).isPresent()){
            model.addAttribute("movie", movieService.findById(id).get());
            model.addAttribute("productions", this.productionService.findAll());
            model.addAttribute("bodyContent", "add-movie");
            return "master-template";
        }
        return "redirect:/movies?error=MovieNotFound";
    }

    @PostMapping("/edit/{movieId}")
    public String editMovie(@PathVariable Long movieId,
                            @RequestParam String movieTitle,
                            @RequestParam String summary,
                            @RequestParam Double rating,
                            @RequestParam Long production){

        this.movieService.edit(movieId, movieTitle, summary, rating, production);
        return "redirect:/movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        this.movieService.delete(id);
        return "redirect:/movies";
    }

    @PostMapping("/order")
    public String orderMovie(@RequestParam String selectedMovie,
                             @RequestParam Integer numTickets,
                             RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("selectedMovie", selectedMovie);
        redirectAttributes.addAttribute("numTickets", numTickets);
        return "redirect:/ticket/details";
    }

}
