package mk.ukim.finki.movies.model.exception;

public class ShoppingCartNotFoundException extends RuntimeException{
    public ShoppingCartNotFoundException() {
        super("Shopping Cart does not exist");
    }
}
