package mk.ukim.finki.movies.service;

import mk.ukim.finki.movies.model.TicketOrder;

import java.util.List;
import java.util.Map;

public interface TicketOrderService{
    TicketOrder placeOrder(String movieTitle, int numberOfTickets);
    Map<String, Long> getTicketsPerMovie();
    Map<Long, String> getUserNamePerMovie();
    Map<Long, String> getStatusOfTickets();
    List<TicketOrder> listAll();
    List<TicketOrder> filterByMovie(String title);
    List<TicketOrder> filterByUser(String name);


}
