package mk.ukim.finki.movies.model.exception;

public class TicketOrderAlreadyInShoppingCartException extends RuntimeException{
    public TicketOrderAlreadyInShoppingCartException() {
        super("Ticket Order Already in Shopping Cart");
    }

}
