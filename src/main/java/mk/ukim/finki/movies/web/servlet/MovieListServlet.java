package mk.ukim.finki.movies.web.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.service.MovieService;
import mk.ukim.finki.movies.service.TicketOrderService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;
import java.util.SimpleTimeZone;


//req (request) is the incoming data from a client (e.g., a web browser) to a web server
//resp (response) is the outgoing data from the web server back to the client.
// These objects are used to communicate and process information in web applications.
@WebServlet(name = "MovieServlet", urlPatterns = "/movieListServlet")
public class MovieListServlet extends HttpServlet {
    private final MovieService movieService;
    private final TicketOrderService ticketOrderService;
    private final SpringTemplateEngine springTemplateEngine;

    public MovieListServlet(MovieService movieService, TicketOrderService ticketOrderService, SpringTemplateEngine springTemplateEngine) {
        this.movieService = movieService;
        this.ticketOrderService = ticketOrderService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        List<Movie> movies = movieService.listAll();
//        if(req.getParameter("text") != null && !req.getParameter("text").isEmpty()){
//            movies = movieService.searchMovies(req.getParameter("text"));
//        }
//        if(req.getParameter("rating") != null && !req.getParameter("rating").isEmpty()){
//            movies = movieService.filterAbove(movies, Float.parseFloat(req.getParameter("rating")));
//        }

        if(req.getParameter("rateMovieId") != null && !req.getParameter("rateMovieTitle").isEmpty()){
            movieService.rateMovie(Long.parseLong(req.getParameter("rateMovieId")),
                    Double.parseDouble(req.getParameter("rateMovieRating")));
        }

        context.setVariable("ticketsPerMovie", ticketOrderService.getTicketsPerMovie());
        context.setVariable("movies", movies);
        springTemplateEngine.process("listMovies.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().setAttribute("movie", req.getParameter("selectedMovie"));
        getServletContext().setAttribute("name", req.getParameter("name"));
        getServletContext().setAttribute("tickets", req.getParameter("numTickets"));
        resp.sendRedirect("/ticketOrderServlet");
    }
}
