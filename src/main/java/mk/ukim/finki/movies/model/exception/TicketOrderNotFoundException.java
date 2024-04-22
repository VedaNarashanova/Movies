package mk.ukim.finki.movies.model.exception;

public class TicketOrderNotFoundException extends RuntimeException{

    public TicketOrderNotFoundException() {
        super("Ticket Order does not exist");
    }
}
