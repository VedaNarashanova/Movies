package mk.ukim.finki.movies.repository.Impl;


import mk.ukim.finki.movies.bootstrap.DataHolder;
import mk.ukim.finki.movies.model.TicketOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryTicketOrderRepository {
    public void addTicket(TicketOrder ticketOrder){
        DataHolder.ticketOrders.add(ticketOrder);
    }

    public Map<String, Long> getTicketsPerMovie() {
        return DataHolder.ticketOrders.stream()
                .collect(Collectors.groupingBy(
                        TicketOrder::getMovieTitle,
                        Collectors.summingLong(TicketOrder::getNumberOfTickets)
                ));
    }



}

