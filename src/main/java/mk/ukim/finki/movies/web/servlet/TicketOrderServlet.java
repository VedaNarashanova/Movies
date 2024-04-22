package mk.ukim.finki.movies.web.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.service.TicketOrderService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TicketOrderServlet", urlPatterns = "/ticketOrderServlet")
public class TicketOrderServlet extends HttpServlet {
    private final TicketOrderService ticketOrderService;
    private final SpringTemplateEngine springTemplateEngine;

    public TicketOrderServlet(TicketOrderService ticketOrderService, SpringTemplateEngine springTemplateEngine) {
        this.ticketOrderService = ticketOrderService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        context.setVariable("movie", getServletContext().getAttribute("movie"));
        context.setVariable("name", getServletContext().getAttribute("name"));
        context.setVariable("tickets", getServletContext().getAttribute("tickets"));
        context.setVariable("ip", req.getRemoteAddr());

        springTemplateEngine.process("orderConfirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String movie = (String)getServletContext().getAttribute("movie");
        String name = (String)getServletContext().getAttribute("name");
        String tickets = (String)getServletContext().getAttribute("tickets");
        String ip = req.getRemoteAddr();

        //ticketOrderService.placeOrder(movie, name, ip, Integer.parseInt(tickets));
        resp.sendRedirect("/movieListServlet");
    }
}

